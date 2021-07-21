package com.example.favdish.model.entities

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)