package org.example.template

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.web.Router
import org.slf4j.LoggerFactory

class MainVerticle : AbstractVerticle() {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun start(bootstrap: Promise<Void>) {
    loadMapperModules()

    val router = Router.router(vertx)

    GetSimpleApi(router)

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .onSuccess { server ->
        log.info("HTTP server started on port " + server.actualPort())
        bootstrap.complete()
      }
      .onFailure {
        log.error("failed starting vertx", it)
        bootstrap.fail(it)
      }
  }
}

fun loadMapperModules() {
  DatabindCodec.mapper()
    .registerKotlinModule()
}
