abstract class Rope(bodySize : Int, bodyLength : Int, follows : Follower) {
  var segList: List[Segments] = List(Head(length = bodyLength))
  for (i <- 0 until bodySize) addSeg()

  def addSeg() : Unit
  def move(): Unit = {
    segList.head.boundTo(follows)
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }
  }
}

//Segments delay for a more realistic representation
case class Centipede(bodySize : Int, bodyLength : Int = 50, follows : Follower) extends Rope(bodySize,bodyLength,follows) {
  //will have legs
  // will have behaviour
  def addSeg(): Unit = segList = segList :+ Body(length = bodyLength)
}

//Snake segments have no delay when following
case class Snake(bodySize : Int, bodyLength : Int = 50, follows : Follower) extends Rope(bodySize,bodyLength,follows) {
  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength)
}