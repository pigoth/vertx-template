package org.example.template.port.api

import io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE
import io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON
import io.vertx.core.Handler
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.slf4j.LoggerFactory

class GetSimpleApi(router: Router) : Handler<RoutingContext> {
  private val log = LoggerFactory.getLogger(javaClass)

  init {
    router.get("/api/simple")
      .handler(this)
  }

  override fun handle(context: RoutingContext) {
    log.info("Get Simple!!")
    val response = listOf(Response("Gino"))
    context.response()
      .putHeader(CONTENT_TYPE, APPLICATION_JSON)
      .end(JsonArray(response).toBuffer())
  }

  data class Response(val name: String)
}
