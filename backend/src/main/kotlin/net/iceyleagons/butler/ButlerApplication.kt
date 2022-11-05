package net.iceyleagons.butler

import com.google.api.client.json.gson.GsonFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.Locale

@SpringBootApplication
class ButlerApplication

fun main(args: Array<String>) {
	runApplication<ButlerApplication>(*args)
}

internal fun Request.execute(): String {
	return client.newCall(this).execute().use { it.body.string() }
}

internal val json by lazy {
	Json {
		ignoreUnknownKeys = true
		prettyPrint = false
	}
}

internal val gsonFactory: GsonFactory by lazy {
	GsonFactory.getDefaultInstance()
}

internal val client by lazy {
	OkHttpClient.Builder()
		.addInterceptor {
			it.proceed(it.request().newBuilder()
				.addHeader("User-Agent", "Butler/1.0.0-ALPHA (Windows NT 10.0; Win64; x64)")
				.addHeader("Content-Type", "application/json")
				.build())
		}
		.build()
}
