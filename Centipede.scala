class Centipede(bodySize : Int, bodyLength : Int = 50, follows : Follower) {
  //will have legs
  // will have behaviour
  var segList : List[Segments] = List(Head(length = bodyLength))
  for (i <- 0 until bodySize) addSeg()

  private def addSeg(): Unit = segList = segList :+ Body(length = bodyLength)
}
