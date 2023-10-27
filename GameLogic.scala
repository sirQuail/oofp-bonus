import processing.core._
import scala.util.Random

class GameLogic {
  val WIDTH = 1000
  val HEIGHT = 800

  var segList : List[Segments] = List( Head(300,200,0,50))
  for (i <- 0 until 2) addSeg()

  def addSeg() = segList = segList :+ Body(300,200,0,50)

  def step(mouse : PVector): Unit = {
    segList.head.follow(mouse)
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

