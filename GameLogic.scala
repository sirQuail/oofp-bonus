import processing.core._
import scala.util.Random

class GameLogic {
  val WIDTH = 1200
  val HEIGHT = 900
  //var mouse : (Float, Float) = (0,0)

  val mouseFollower : Follower = new Follower(0,0)
  var mouseCenti : Centipede = new Centipede(9, 30, mouseFollower)

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
add legs
main
mini

 */
