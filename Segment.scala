import processing.core.PApplet._
import processing.core.PVector
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
  def follow(target: PVector) : Unit = {
    val direction: PVector = PVector.sub(target, a)
    angle = direction.heading()

    direction.setMag(length)
    direction.mult(-1)

    changeA(PVector.add(target,direction))
  }
  def changeA(pVec : PVector) : Unit
}

case class Head(x : Float, y : Float, startAngle : Float, length : Float) extends Segments(x,y,startAngle,length) {
  def changeA(pVec: PVector) : Unit = a = pVec
}

case class Body(x : Float, y : Float, startAngle : Float, length : Float) extends Segments(x,y,startAngle,length) {
  def changeA(pVec : PVector) : Unit = a.lerp(pVec, 0.5.toFloat)
}
