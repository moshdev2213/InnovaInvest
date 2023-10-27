package com.example.innova.FormData

import com.example.charmrides.Validation.ValidationResult

class AddProjForm(
    private var projTitle:String,
    private var skills:String,
    private var budget:String,
    private var description:String
) {

    fun validateBudget(): ValidationResult {

        return if(budget.isEmpty()){
            ValidationResult.Empty("Please Enter Budget")
        } else{
            ValidationResult.Valid
        }
    }
    fun validateProjTitle(): ValidationResult {
        return when {
            projTitle.isEmpty() -> ValidationResult.Empty("Please Enter Title")
            else -> ValidationResult.Valid
        }
    }
    fun validateProjSkills(): ValidationResult {
        return when {
            skills.isEmpty() -> ValidationResult.Empty("Please Enter Skills")
            else -> ValidationResult.Valid
        }
    }
    fun validateProjDes(): ValidationResult {
        return when {
            description.isEmpty() -> ValidationResult.Empty("Please Enter Description")
            else -> ValidationResult.Valid
        }
    }
}