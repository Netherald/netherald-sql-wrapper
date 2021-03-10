package com.github.netherald.sqlWrapper.models

data class User(val uuid: String, val guild: Guild?, val friends: ArrayList<User>?)
