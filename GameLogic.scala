import processing.core._
/*
Creatures that are procedurally animated using inverse kinematics.
This topic is very interesting to me so I made this demonstration of different skeletons that move "realistically"
 */

class GameLogic {
  val WIDTH = 1200
  val HEIGHT = 900

  //FEEL FREE TO SWITCH BETWEEN COMMENTED CODE TO SEE DIFFERENT BEHAVIOUR
  val mouseFollower : Followers = new ConstantFollower(8)
  //val mouseFollower : Followers = new TeleportFollower


  //FEEL FREE TO SWITCH BETWEEN COMMENTED CODE TO SEE DIFFERENT BEHAVIOUR
  //var mouseRope : Snake = Snake(0, follows = mouseFollower) // STICK
  //var mouseRope : Snake = Snake(10, follows = mouseFollower, color = (0,255,0)) // SNAKE
  //var mouseRope : FixedRope = FixedRope(2,50, mouseFollower, new PVector(WIDTH/2,HEIGHT/2)) // ARM. I suggest using TeleportFollower with it
  //var mouseRope : Centipede = Centipede(2, 50, mouseFollower) // DOG
  var mouseRope : Centipede = Centipede(15, 50, mouseFollower, color = (255,0,0)) // CENTIPEDE


  mouseRope.initRope()
  def step(newMouse : PVector): Unit = {
    mouseFollower.follow(newMouse)
    mouseRope.move()
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
