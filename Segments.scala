import processing.core.PApplet._
import processing.core.{PApplet, PVector}

import scala.util.Random

abstract class Segments(x : Float, y : Float, startAngle : Float, length : Float) {
  val rgb : (Int,Int,Int) = (new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255))
  var a: PVector = new PVector(x, y)
  var b: PVector = new PVector()
  var angle: Float = startAngle

  private def CalcB(): Unit = {
    val dx: Float = length * cos(angle)
    val dy: Float = length * sin(angle)
    b.set(a.x + dx, a.y + dy)
  }

  def updateSeg(): Unit = CalcB()
  def boundTo(follower : Followers) : Unit = this.follow(follower.position)
  def follow(target: PVector) : Unit = {
    val direction: PVector = PVector.sub(target, a)
    angle = direction.heading()

    direction.setMag(length)
    direction.mult(-1)

    changeA(PVector.add(target,direction))
  }
  def changeA(pVec : PVector) : Unit
}

case class Head(x : Float = 0, y : Float = 0, startAngle : Float = 0, length : Float) extends Segments(x,y,startAngle,length) {
  def changeA(pVec: PVector) : Unit = a = pVec
}

case class Body(x : Float = 0, y : Float = 0, startAngle : Float = 0, length : Float) extends Segments(x,y,startAngle,length) {
  def changeA(pVec : PVector) : Unit = a.lerp(pVec, 0.35.toFloat)
}

case class Carpace(x : Float = 0, y : Float = 0, startAngle : Float = 0, length : Float) extends Segments(x,y,startAngle,length){
  def changeA(pVec : PVector) : Unit = a.lerp(pVec, 0.35.toFloat)

  //four new points left knee left leg right knee right leg
  var midPoint: PVector = new PVector((a.x+b.x) / 2, (a.y+b.y) / 2)
}

abstract class Followers(var initialX : Float, var initialY : Float) {
  var position : PVector = new PVector(initialX,initialY)
  def follow(target : PVector): Unit

  def closeTo(pVector: PVector, range: Float): Boolean = { //TODO rename and this is still a bit jumpy, use point
    val distance = PApplet.dist(position.x, position.y, pVector.x, pVector.y)
    distance <= range
  }
}
//For head usually
class ConstantFollower(x : Float, y : Float, speed : Int) extends Followers(x,y){
  def follow(target : PVector) : Unit = {
    val direction = PVector.sub(target, position)
    direction.normalize()
    direction.mult(speed)

    if (!closeTo(target, 14))
      position.x += direction.x
    position.y += direction.y
  }
}

class TeleportFollower(x : Float, y : Float) extends Followers(x,y){
  def follow(target : PVector): Unit = {position = target}
}

class SegmentFollower(x : Float, y : Float, segment : Segments, side : Float) extends Followers(x,y) {
  def follow(target : PVector): Unit = ""
  def update(segment : Segments): Unit = {
    position =
      if (!closeTo(segment.b, 50)) {
        val normalVec : PVector = new PVector(segment.b.x * - 1, segment.b.y).normalize() //dir matters for which side
        normalVec.mult(30 * side)
        PVector.add(normalVec, segment.b)
      }
      else position
  }
}
