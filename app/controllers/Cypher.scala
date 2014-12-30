package controllers

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import anorm.NotAssigned
import com.codahale.jerkson.Json
import scala.concurrent.Future
import services.CypherService
import org.anormcypher._
import scalaz.Scalaz._

object Cypher extends Controller {

  def cypher() = Action.async { request =>
    val user = request.body.asFormUrlEncoded.get.mapValues(_.head).toSeq.head
    val xs = CypherService.query("""match (n:User {email: {email}})-[created]->h return n, h""", user)
    val json = Json.generate(xs)
    Future(Ok(json).as("application/json"))
  }

  def activity() = Action { request =>
    var user_id = request.queryString.get("user_id").head.head
    val json = Json.generate(CypherService.all(user_id))
    Ok(json).as("application/json")
  }
}

