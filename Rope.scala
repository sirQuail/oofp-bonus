import processing.core.PVector

abstract class Rope(bodySize : Int, bodyLength : Int, follows : Followers) {
  var segList: List[Segments] = List(Head(length = bodyLength))
  //var segmentFollowers : List[List[SegmentFollower]] = List() // TODO move to centipede
  def initRope() :Unit = for (_ <- 0 until bodySize) addSeg()

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
  // will have behaviour?
  var legs : List[FixedRope] = List()
  var segmentFollowers : List[List[SegmentFollower]] = List()

  override def move(): Unit = {
    segList.head.boundTo(follows)
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }
    updateLegs()
  }

  def addSeg(): Unit = {
    val newSeg = Body(length = bodyLength)
    segList = segList :+ newSeg
    segmentFollowers = segmentFollowers :+ List(new SegmentFollower(1), new SegmentFollower(-1))
    //val fixedPoint = new PVector((newSeg.b.x - newSeg.a.x)/2,(newSeg.b.y - newSeg.a.y)/2)
    //legs = legs :+ new FixedRope(follows = new SegmentFollower( segList.last), fixedTo = fixedPoint)
  }

  def updateLegs(): Unit = {
    for (i <- 1 until segList.length) {
      val currentSegment = segList(i)
      val legs = segmentFollowers(i - 1)
      legs.head.follow(currentSegment)
      legs.last.follow(currentSegment)
    }
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