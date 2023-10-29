import processing.core.PApplet._
import processing.core.{PApplet, PVector}
import scala.util.Random

/*
Segments used by Rope subclasses
 */

abstract class Segments(x : Float, y : Float, startAngle : Float, length : Float) {
  val color : (Int,Int,Int) = (255,255,255)
  var a: PVector = new PVector(x, y)
  var b: PVector = new PVector()
  private var angle: Float = startAngle

  def updateSeg(): Unit = CalcB()
  private def CalcB(): Unit = { //Update B position
    val dx: Float = length * cos(angle)
    val dy: Float = length * sin(angle)
    b.set(a.x + dx, a.y + dy)
  }

  def boundTo(follower: Followers): Unit = this.follow(follower.position) //Used for head of Rope. Not necessary, but it helps with comprehending
  def follow(target: PVector): Unit = {
    val direction: PVector = PVector.sub(target, a)
    angle = direction.heading()

    direction.setMag(length)
    direction.mult(-1)

    changeA(PVector.add(target, direction))
  }

  def getMid: PVector = new PVector((a.x + b.x) / 2, (a.y + b.y) / 2) //Used for giving segments legs
  def getRandomRGB: (Int, Int, Int) = (new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255))

  def changeA(pVec : PVector) : Unit //determine how segment moves
}

case class Head(x : Float = 0, y : Float = 0, startAngle : Float = 0, length : Float) extends Segments(x,y,startAngle,length) {
  def changeA(pVec: PVector) : Unit = a = pVec //instant, segment is connected to parent segment
}

case class Body(x : Float = 0, y : Float = 0, startAngle : Float = 0, length : Float) extends Segments(x,y,startAngle,length) {
  def changeA(pVec : PVector) : Unit = a.lerp(pVec, 0.35.toFloat) //segment lerps, gives a latency to movement
}
