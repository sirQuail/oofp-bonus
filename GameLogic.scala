import processing.core._
import scala.util.Random

class GameLogic {
  val WIDTH = 1200
  val HEIGHT = 900
  //var mouse : (Float, Float) = (0,0)

  val mouseFollower : Followers = new ConstantFollower(8)
  var mouseCenti : Centipede = Centipede(10, 50, mouseFollower)
  mouseCenti.initRope()
  //var mouseCenti : FixedRope = new FixedRope(20,50, mouseFollower, new PVector(WIDTH/2,HEIGHT/2))

  def step(newMouse : PVector): Unit = {
    //mouse = (newMouse.x, newMouse.y)

    mouseFollower.follow(newMouse)
    mouseCenti.move()
  }

  def isPressed(key : Char) = {
    key match {
      case _ => key
    }
  }
}

/*TODO
add sprite
add centipede behaviour
add slowed mouse follow using point DONE! bug: head jiggles sometimes if close to mouse
add growth
add centipede class IMPLEMENTED
add legs add fixed Rope
main
mini

 */
