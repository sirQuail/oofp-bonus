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

  def getMid: PVector = new PVector((a.x + b.x) / 2, (a.y + b.y) / 2)
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
