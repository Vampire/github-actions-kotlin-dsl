package it.krzeminski.githubactions.domain.triggers

data class Push(
    val branches: List<String>? = null,
    val tags: List<String>? = null,
    val branchesIgnore: List<String>? = null,
    val tagsIgnore: List<String>? = null,
) : Trigger()