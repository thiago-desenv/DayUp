package com.example.dayup.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.net.HttpURLConnection
import java.net.URL

data class RepoInfo(
    val name: String,
    val pushedAt: ZonedDateTime
)

class GitHubRepository {
    suspend fun getUserRepos(username: String): List<RepoInfo> = withContext(Dispatchers.IO) {
        val url = URL("https://api.github.com/users/$username/repos")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        val repos = mutableListOf<RepoInfo>()

        try {
            val response = connection.inputStream.bufferedReader().readText()
            val jsonArray = JSONArray(response)

            for (i in 0 until jsonArray.length()) {
                val repo = jsonArray.getJSONObject(i)
                val name = repo.getString("name")
                val pushedAtStr = repo.getString("pushed_at")
                val pushedAt = Instant.parse(pushedAtStr).atZone(ZoneId.systemDefault())
                repos.add(RepoInfo(name, pushedAt))
            }
        } catch(e: Exception) {
            Log.e("GitHubRepository", "Erro ao buscar repositórios", e)
        }
        finally {
            connection.disconnect()
        }

        repos
    }
}