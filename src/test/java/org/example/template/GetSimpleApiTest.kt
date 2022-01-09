package org.example.template

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.vertx.core.Vertx
import io.vertx.ext.web.Router.router
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(VertxExtension::class, JsonExtension::class)
internal class GetSimpleApiTest {

  private val port = Random.nextInt(9152, 65000)

  @BeforeEach
  internal fun setUp(vertx: Vertx, setup: VertxTestContext) {
    val router = router(vertx)
    GetSimpleApi(router)

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, setup.succeedingThenComplete())
  }

  @Test
  internal fun should_get_dummy_assert_json_path() {
    given()
      .port(port)
      .get("/api/simple")
      .then()
      .statusCode(200)
      .contentType(JSON)
      .body("size()", `is`(1))
      .body("[0].name", `is`("Gino"))
  }

  @Test
  internal fun should_get_dummy_extract_list() {
    val response = given()
      .port(port)
      .get("/api/simple")
      .then()
      .statusCode(200)
      .extract()
      .body()
      .jsonPath()
      .getList(".", GetSimpleApi.Response::class.java)

    assertThat(response).containsExactly(GetSimpleApi.Response("Gino"))
  }
}
