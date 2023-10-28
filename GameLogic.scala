import processing.core._
import scala.util.Random

class GameLogic {
  val WIDTH = 1000
  val HEIGHT = 800
  var mouse : (Float, Float) = (0,0)

  val mouseFollower : Follower = new Follower(0,0)

  var segList : List[Segments] = List( Head(length = 50))
  for (i <- 0 until 10) addSeg()
  def addSeg() = segList = segList :+ Body(length = 50)

  def step(newMouse : PVector): Unit = {
    mouse = (newMouse.x, newMouse.y)

    mouseFollower.follow(newMouse)

    segList.head.boundTo(mouseFollower)//find a different way to do the following be more clear
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a) //maybe turn this to inside class thing where each segment knows nextseg. Why? less step dirtiness
    }
  }

  def isPressed(key : Char) = {
    key match {
      case _ => key
    }
  }

  def changeDirectionImpulse(current: PVector) = { // Supposed to add behaviour
    //returns PVector if chance is correct
    val random = new Random()
    val newRand = random.nextInt(10)
    println(newRand)
    if (1 == newRand) {
      val randX = random.nextInt(WIDTH)
      val randY = random.nextInt(HEIGHT)
      new PVector(randX,randY)
    }
    else current
  }
}

/*TODO
add sprite
add centipede behaviour
add slowed mouse follow using point DONE! bug: head jiggles sometimes if close to mouse
add growth
add centipede class NEXT
add legs
main
mini

 */
