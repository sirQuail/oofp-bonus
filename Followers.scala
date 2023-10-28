import processing.core.{PApplet, PVector}

abstract class Followers(var initialX : Float = 0, var initialY : Float = 0) {
  var position : PVector = new PVector(initialX,initialY)
  def follow(target : PVector): Unit

  def closeTo(pVector: PVector, range: Float): Boolean = { //TODO rename and this is still a bit jumpy, use point
    val distance = PApplet.dist(position.x, position.y, pVector.x, pVector.y)
    distance <= range
  }
}
//For head usually
class ConstantFollower(speed : Int) extends Followers(){
  def follow(target : PVector) : Unit = {
    val direction = PVector.sub(target, position)
    direction.normalize()
    direction.mult(speed)

    if (!closeTo(target, 14))
      position.x += direction.x
    position.y += direction.y
  }
}

class TeleportFollower() extends Followers(){
  def follow(target : PVector): Unit = {position = target}
}

class SegmentFollower(side : Float) extends Followers() {
  def follow(segment: Segments) : Unit = {
    position =
      if (!closeTo(segment.b, 50)) {
        val normalVec: PVector = new PVector(segment.b.x * -1, segment.b.y).normalize() //dir matters for which side
        normalVec.mult(30 * side)
        PVector.add(normalVec, segment.b)
      }
      else position
  }
  def follow(target : PVector): Unit = throw new Error("SegmentFollower uses segments")
}