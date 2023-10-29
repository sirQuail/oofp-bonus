import processing.core.{PApplet, PConstants, PVector}

class GameBase extends PApplet {

  var gameLogic = new GameLogic
  val updateTimer = new UpdateTimer(60)

  override def settings(): Unit = {
    size(gameLogic.WIDTH, gameLogic.HEIGHT)
  }

  override def setup(): Unit = {
    updateTimer.init()
  }

  override def draw(): Unit = {
    if (updateTimer.timeForNextFrame()) {
      gameLogic.step(new PVector(mouseX,mouseY))
      updateTimer.advanceFrame()
    }
    //clear
    background(0)

    strokeWeight(8)


    //draw seg
    drawSegments(gameLogic.mouseCenti.segList)
    drawLegs()
    drawPoints()
    //drawSegments(gameLogic.mouseCenti.legsList)
  }
  def drawLegs(): Unit = {
    for (el <- gameLogic.mouseCenti.legList) {
      drawSegments(el.head.segList)
      drawSegments(el.last.segList)
    }
  }
  def drawPoints(): Unit = {
    strokeWeight(10)
    for (el <- gameLogic.mouseCenti.segmentFollowers) {
      point(el.head.position.x, el.head.position.y)
      point(el.last.position.x, el.last.position.y)
    }
  }
  private def drawSegments(segList : List[Segments]): Unit = {
    strokeWeight(6)
    for (seg <- segList) {
      stroke(seg.color._1,seg.color._2,seg.color._3)
      seg.updateSeg()
      val a = seg.a
      val b = seg.b
      line(a.x, a.y, b.x, b.y)
    }
    stroke(0,0,0)
    strokeWeight(1)
  }

  override def keyPressed(): Unit = {
    gameLogic.isPressed(key)
  }

  class UpdateTimer(val framesPerSecond: Float) {

    private val frameDuration: Float = 1000 / framesPerSecond // ms
    private var nextFrame: Float = Float.MaxValue

    def init(): Unit = nextFrame = currentTime() + frameDuration
    def timeForNextFrame(): Boolean = currentTime() >= nextFrame
    def advanceFrame(): Unit = nextFrame = nextFrame + frameDuration

  }

  private def currentTime(): Int = millis()
}

object GameBase {
  def main(args: Array[String]): Unit = {
    PApplet.main("GameBase")
  }
}