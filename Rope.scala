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
  var legList : List[List[FixedRope]] = List()
  var segmentFollowers : List[List[SegmentFollower]] = List()

  override def move(): Unit = {
    segList.head.boundTo(follows)
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }
    updateFollowers()
    updateLegs()
  }

  def addSeg(): Unit = {
    val newSeg = Body(length = bodyLength)
    segList = segList :+ newSeg
    segmentFollowers = segmentFollowers :+ List(new SegmentFollower(1), new SegmentFollower(-1))

    legList = legList :+ List(FixedRope(follows = segmentFollowers.last.head, fixedTo = newSeg.getMid),
      FixedRope(follows = segmentFollowers.last.last, fixedTo = newSeg.getMid))
    legList.last.head.initRope()
    legList.last.last.initRope()
  }

  def updateFollowers(): Unit = {
    for (i <- 1 until segList.length) {
      val currentSegment = segList(i)
      val follower = segmentFollowers(i - 1)
      follower.head.follow(currentSegment)
      follower.last.follow(currentSegment)
    }
  }

  def updateLegs(): Unit = {
    for (i <- 1 until segList.length) {
      val currentSegment = segList(i)
      for (fixedRope <- legList(i - 1)) {
        fixedRope.updateFixed(currentSegment.getMid)
        fixedRope.move()
      }
    }
  }
}

//Snake segments have no delay when following
case class Snake(bodySize : Int, bodyLength : Int = 50, follows : Followers) extends Rope(bodySize,bodyLength,follows) {
  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength)
}

case class FixedRope(bodySize : Int = 1, bodyLength : Int = 20, follows : Followers,var fixedTo : PVector) extends Rope(bodySize,bodyLength,follows) {
  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength) //no need for delay
  override def move(): Unit = {
    segList.head.boundTo(follows)

    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }
    segList.last.a = fixedTo

    for (i <- 0 until segList.length - 1) { //TODO issue where the segments arent moving in a fixed manner
      segList(i).a = segList(i + 1).b
    }
  }

  def updateFixed(newAnchor : PVector) : Unit = {
    fixedTo = newAnchor
  }
}