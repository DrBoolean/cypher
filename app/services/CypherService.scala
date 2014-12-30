package services
import org.anormcypher._

object CypherService {
  implicit val connection = Neo4jREST()

  def query(q: String, m: (String, Any)): List[Tuple2[NeoNode, NeoNode]] = {
    val req = Cypher(q).on(m)
    val stream = req();
    stream.map(row => {(row[NeoNode]("n"), row[NeoNode]("h"))}).toList
  }

  def all(user_id: String) : List[Map[String, Any]] = {

    val query = """
        MATCH (h:Headline)<-[:belongs_to]-(a:Activity)<-[c:created]-(u:User), (cu:User {id: {user_id}})
        WHERE (h)-[:belongs_to*1..3]->()<-[:can_access]-(cu)
        OPTIONAL MATCH (m)-[:belongs_to]->(a)
        RETURN a, u, h, c.at AS created_at, collect(m) AS media ORDER BY created_at DESC
        """
    val req = Cypher(query).on("user_id" -> user_id.toInt)
    val stream = req();
    stream.map(row => {
      Map("activity" -> row[NeoNode]("a"),
          "user" -> row[NeoNode]("u"),
          "headline" -> row[NeoNode]("h"),
          "created_at" -> row[Long]("created_at"),
          "media" -> row[Seq[NeoNode]]("media")
         )
    }).toList
  }
}
