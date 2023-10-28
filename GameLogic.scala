import processing.core._
import scala.util.Random

class GameLogic {
  val WIDTH = 1200
  val HEIGHT = 900
  //var mouse : (Float, Float) = (0,0)

  val mouseFollower : Followers = new ConstantFollower(0,0,2)
  var mouseCenti : Rope = new Centipede(1, 50, mouseFollower)
  //var mouseCenti : FixedRope = new FixedRope(20,50, mouseFollower, new PVector(WIDTH/2,HEIGHT/2))
  var legfollower: SegmentFollower = new SegmentFollower(0, 0, mouseCenti.segList.head, 1)
  var legfollower2: SegmentFollower = new SegmentFollower(0, 0, mouseCenti.segList.head, -1)

  def step(newMouse : PVector): Unit = {
    //mouse = (newMouse.x, newMouse.y)

    mouseFollower.follow(newMouse)
    legfollower.update(mouseCenti.segList.head)
    legfollower2.update(mouseCenti.segList.head)
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
