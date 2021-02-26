package com.github.netherald.sqlWrapper.models

data class Guild(val name: String, val id: Int, val users: ArrayList<User>, val description: String)
