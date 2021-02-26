package com.github.netherald.sqlWrapper

import com.github.netherald.sqlWrapper.models.Guild
import com.github.netherald.sqlWrapper.models.User
import com.mysql.cj.xdevapi.JsonArray
import org.json.simple.parser.JSONParser
import java.lang.IllegalStateException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList

class SqlWrapper {

    var sqlConnection: Connection
    var logger: Logger

    constructor(username: String, password: String, logger: Logger, ip: String = "localhost", port: Int = 3306) {
        this.logger = logger
        logger.info("Loading driver...")
        try {
            Class.forName("com.mysql.jdbc.Driver")
            logger.info("Connecting to SQL...")
            sqlConnection = DriverManager.getConnection(ip, username, password);
            logger.info("Connected to ${ip}:${port}")
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("Driver not found!")
        } catch (e: SQLException) {
            logger.info("Check the exception!")
            e.printStackTrace()
            logger.info("==============================================");
            throw UnknownError("Unknown Error!")
        }
    }

    fun closeConnection() {
        try {
            if (sqlConnection != null || !sqlConnection.isClosed) {
                sqlConnection.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getGuild(id: Int) : Guild {
        if (sqlConnection.isClosed)
            throw IllegalStateException("SQL Connection is Closed!")
        val statement = sqlConnection.prepareStatement("SELECT FROM guilds WHERE id=?")
        statement.setInt(0, id)

        val result = statement.executeQuery()

        while (result.next()) {
            val list = JSONParser().parse(result.getString("users")) as JsonArray
            val listParsed = ArrayList<User>()
            for (str in list) {
                listParsed.add(User(str.toFormattedString(), null))
            }
            return Guild(result.getString("name"), result.getInt("id"), listParsed, result.getString("description"))
        }
        throw IllegalAccessException("No guild found!")
    }

    fun getUser(uuid: UUID) : User {
        if (sqlConnection.isClosed)
            throw IllegalStateException("SQL Connection is Closed!")
        val statement = sqlConnection.prepareStatement("SELECT FROM users WHERE uuid=?")
        statement.setString(0, uuid.toString())

        val result = statement.executeQuery()

        while (result.next()) {
            return User(uuid.toString(), getGuild(result.getInt("guild")))
        }
        throw IllegalAccessException("No user found!")
    }
}