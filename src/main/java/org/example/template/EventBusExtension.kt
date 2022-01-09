package org.example.template

import io.vertx.core.Future
import io.vertx.core.Future.succeededFuture
import io.vertx.core.Handler
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import kotlin.reflect.KClass


fun <T : Any> EventBus.on(clazz: KClass<T>, consuming: Handler<T>) {
  this.consumer<JsonObject>(clazz.simpleName) {
    consuming.handle(it.body().mapTo(clazz.java))
  }
}

fun EventBus.emit(message: Any): Future<Unit> {
  this.publish(message::class.java.simpleName, JsonObject.mapFrom(message))
  return succeededFuture()
}
