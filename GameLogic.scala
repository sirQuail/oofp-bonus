import processing.core._

class GameLogic {
  val WIDTH = 1200
  val HEIGHT = 900

  val mouseFollower : Followers = new ConstantFollower(8)
  var mouseCenti : Centipede = Centipede(2, 50, mouseFollower)

  //var mouseCenti : FixedRope = new FixedRope(1,50, mouseFollower, new PVector(WIDTH/2,HEIGHT/2))
  //var mouseCenti : Snake = new Snake(10, follows = mouseFollower)

  mouseCenti.initRope()
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
add sprite CANCELLED
add centipede behaviour CANCELLED (if anyone is interested, I thought using followers to direct Ropes to a location)
add Followers DONE! bug: head jiggles sometimes if close to mouse
add growth CANCELLED
add centipede class DONE
add legs DONE
 */
