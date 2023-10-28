import processing.core.PVector

abstract class Rope(bodySize : Int, bodyLength : Int, follows : Followers) {
  var segList: List[Segments] = List(Head(length = bodyLength))
  var legsList : List[Segments] = List()
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
case class Centipede(bodySize : Int, bodyLength : Int = 50, follows : Followers) extends Rope(bodySize,bodyLength,follows) {
  //will have legs
  // will have behaviour
  //var legs : List[FixedRope] = List()


  def addSeg(): Unit = {
    val newSeg = Body(length = bodyLength)
    segList = segList :+ newSeg
    //val fixedPoint = new PVector((newSeg.b.x - newSeg.a.x)/2,(newSeg.b.y - newSeg.a.y)/2)
    //legs = legs :+ new FixedRope(follows = new SegmentFollower(0,0, segList.last), fixedTo = fixedPoint)
  }

  def updateLegs(): Unit = {

  }
}

//Snake segments have no delay when following
case class Snake(bodySize : Int, bodyLength : Int = 50, follows : Followers) extends Rope(bodySize,bodyLength,follows) {
  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength)
}

case class FixedRope(bodySize : Int = 1, bodyLength : Int = 50, follows : Followers, fixedTo : PVector) extends Rope(bodySize,bodyLength,follows) {
  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength) //no need for delay
  override def move(): Unit = {
    segList.head.boundTo(follows)

    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
      //segList(i).updateSeg()
    }
    segList.last.a = fixedTo

    for (i <- 0 until segList.length - 1) { //TODO issue where the segments arent moving in a fixed manner
      segList(i).a = segList(i + 1).b
      //segList(i).updateSeg()
    }
  }

}