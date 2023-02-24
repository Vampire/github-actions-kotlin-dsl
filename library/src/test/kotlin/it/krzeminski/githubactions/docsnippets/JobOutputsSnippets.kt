package it.krzeminski.githubactions.docsnippets

import io.kotest.core.spec.style.FunSpec
import it.krzeminski.githubactions.domain.JobOutputs
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.actions.Action
import it.krzeminski.githubactions.domain.triggers.Push
import it.krzeminski.githubactions.dsl.expressions.expr
import it.krzeminski.githubactions.dsl.workflow
import java.util.LinkedHashMap

class JobOutputsSnippets : FunSpec({
    test("jobOutputs") {
        workflow(
            name = "Test workflow",
            on = listOf(Push()),
        ) {
/* ktlint-disable indent */
// --8<-- [start:defineJobOutputs1]
val myJob = job(
    id = "my_job",
    runsOn = RunnerType.UbuntuLatest,
    outputs = object : JobOutputs() {
        var myOutput by output()
        var anotherOutput by output()
    },
// --8<-- [end:defineJobOutputs1]
/*
// --8<-- [start:defineJobOutputs2]
) { ... }
// --8<-- [end:defineJobOutputs2]
*/
) {
    class DocTest : Action<DocTest.Outputs>("doc", "test", "v0") {
        override fun toYamlArguments(): LinkedHashMap<String, String> = linkedMapOf()
        override fun buildOutputObject(stepId: String): Outputs = Outputs(stepId)
        inner class Outputs(stepId: String) : Action.Outputs(stepId) {
            val someStepOutput: String = ""
        }
    }
    val someStep = uses(DocTest())

// --8<-- [start:setJobOutputs]
jobOutputs.myOutput = someStep.outputs.someStepOutput
jobOutputs.anotherOutput = someStep.outputs["custom-output"]
// --8<-- [end:setJobOutputs]
}

// --8<-- [start:useJobOutputs]
job(
    id = "use_output",
    runsOn = RunnerType.UbuntuLatest,
    needs = listOf(myJob),
) {
    run(
        name = "Use outputs",
        command = """
            echo ${expr { myJob.outputs.myOutput }}
            echo ${expr { myJob.outputs.anotherOutput }}
        """.trimIndent(),
    )
}
// --8<-- [end:useJobOutputs]
/* ktlint-enable indent */
        }
    }
},)