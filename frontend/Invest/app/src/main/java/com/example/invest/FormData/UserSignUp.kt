package com.example.charmrides.FormData

import com.example.charmrides.Validation.ValidationResult


class UserSignUp(
    private var tel:String,
    private var email:String,
    private var password:String,
    private var rePass:String,
    private var name:String
) {
    fun validateEmail(): ValidationResult {
        val regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}" // Regex to match email addresses

        return if(email.isEmpty()){
            ValidationResult.Empty("Please Enter Email")
        }else if(email.equals(password)){
            ValidationResult.Invalid("Email Shouldn't be password")
        }else if(!email.matches(regex.toRegex())){
            ValidationResult.Invalid("Please Enter Valid Email")
        }else{
            ValidationResult.Valid
        }
    }

    fun validatePassword():ValidationResult{
        // Regex to validate password with all uppercase ,lowercase,special characters,and minimum 8 chars
        //val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}\$"

        // Regex to validate password with alphaNumerics,and minimum 8 chars
        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"


        return if(password.isEmpty()){
            ValidationResult.Empty("Please Enter Password")
        } else if (!password.matches(passwordPattern.toRegex())) {
            ValidationResult.Invalid("Invalid ex: Aa@asda22") // Password is invalid
        }else if(password!=rePass){
            ValidationResult.Invalid("Passwords MisMatch")
        } else{
            ValidationResult.Valid
        }

    }
    fun validateRePass():ValidationResult{
        return if(rePass.isEmpty()){
            ValidationResult.Empty("Please Enter Password")
        }else if(password!=rePass){
            ValidationResult.Invalid("Passwords MisMatch")
        } else{
            ValidationResult.Valid
        }

    }

    fun validateTelephone():ValidationResult{
        // Regex to validate password with alphaNumerics,and minimum 8 chars
        val telPattern = "^\\d{10}$"
        return if(tel.isEmpty()){
            ValidationResult.Empty("Please Enter Telephone")
        } else if (!tel.matches(telPattern.toRegex())) {
            ValidationResult.Invalid("Invalid ex: 0765654332") // Password is invalid
        }else{
            ValidationResult.Valid
        }
    }
    fun validateName(): ValidationResult {
        // Regex to validate the name - allows letters, spaces, and may be more complex depending on your requirements
        val namePattern = "^[A-Za-z ]+$"

        return when {
            name.isEmpty() -> ValidationResult.Empty("Please Enter a Name")
            !name.matches(namePattern.toRegex()) -> ValidationResult.Invalid("Invalid Name")
            else -> ValidationResult.Valid
        }
    }

}