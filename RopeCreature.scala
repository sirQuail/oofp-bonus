import processing.core.PVector
/*
RopeCreature classes have different bodies and behaviour
 */

abstract class RopeCreature(bodySize : Int, follows : Followers) {
  var segList: List[Segments] = List()
  def initRope() :Unit = for (_ <- 0 until bodySize) addSeg()

  def addSeg() : Unit
  def move(): Unit = {
    segList.head.boundTo(follows)
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }
  }
}

case class Centipede(bodySize : Int, bodyLength : Int = 50, follows : Followers, color : (Int,Int,Int) = (255,255,255)) extends RopeCreature(bodySize, follows) {
  segList = List(Head(length = bodyLength, initColor = color))
  var legList : List[List[FixedRope]] = List()
  var segmentFollowers : List[List[SegmentFollower]] = List() // Aids legs in legList

  override def move(): Unit = {
    segList.head.boundTo(follows)
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }
    updateFollowers()
    updateLegs()
  }

  def addSeg(): Unit = {
    val newSeg = Body(length = bodyLength, initColor = color) //Segments delay for a more realistic representation
    segList = segList :+ newSeg
    segmentFollowers = segmentFollowers :+ List(new SegmentFollower(1), new SegmentFollower(-1))

    legList = legList :+ List(FixedRope(follows = segmentFollowers.last.head, fixedTo = newSeg.getMid, color = color), //setup legList
      FixedRope(follows = segmentFollowers.last.last, fixedTo = newSeg.getMid, color = color))
    legList.last.head.initRope()
    legList.last.last.initRope()
  }

  private def updateFollowers(): Unit = {
    for (i <- 1 until segList.length) {
      val currentSegment = segList(i)
      val follower = segmentFollowers(i - 1)
      follower.head.follow(currentSegment)
      follower.last.follow(currentSegment)
    }
  }

  private def updateLegs(): Unit = {
    for (i <- 1 until segList.length) {
      val currentSegment = segList(i)
      for (fixedRope <- legList(i - 1)) {
        fixedRope.updateFixed(currentSegment.getMid)
        fixedRope.move()
      }
    }
  }
}

case class Snake(bodySize : Int, bodyLength : Int = 50, follows : Followers, color : (Int,Int,Int) = (255,255,255)) extends RopeCreature(bodySize, follows) {
  segList = List(Head(length = bodyLength, initColor = color))
  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength, initColor = color) //Snake segments have no delay when following
}

case class FixedRope(bodySize : Int = 3, bodyLength : Int = 25, follows : Followers,var fixedTo : PVector, color : (Int,Int,Int) = (255,255,255)) extends RopeCreature(bodySize, follows) {
  segList = List(Head(length = bodyLength, initColor = color))

  def addSeg(): Unit = segList = segList :+ Head(length = bodyLength, initColor = color) //no need for delay
  override def move(): Unit = {
    segList.head.boundTo(follows) // move to target
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
    }

    segList.last.a = fixedTo //fix base of Rope back

    for (i <- 0 until segList.length - 1) { //Pull all segments back to fixed source
      segList(i).a = segList(i + 1).b //TODO issue when segments move they disconnect for a moment
    }
  }

  def updateFixed(newAnchor : PVector) : Unit = { //used for legs as they are fixed to a moving segment
    fixedTo = newAnchor
  }
}