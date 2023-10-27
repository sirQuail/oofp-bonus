import processing.core._
import scala.util.Random

class GameLogic {
  val WIDTH = 1000
  val HEIGHT = 800
  var mouse : (Float, Float) = (0,0)

  var segList : List[Segments] = List( Head(300,200,0,20))
  for (i <- 0 until 20) addSeg()
  def addSeg() = segList = segList :+ Head(300,200,0,20)

  def step(newMouse : PVector): Unit = {
    mouse = (newMouse.x, newMouse.y)

    segList.head.follow(newMouse)
    for (i <- 1 until segList.length) {
      segList(i).follow(segList(i - 1).a)
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
add centipede behaviour NEXT
add slowed mouse follow using point MAIN
add growth
add centipede class
main
mini

 */
