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
    background(255)

    strokeWeight(8)

    for (el <- gameLogic.mouseCenti.segmentFollowers){
      point(el.head.position.x,el.head.position.y)
      point(el.last.position.x,el.last.position.y)
    }
    //draw seg
    drawSegments(gameLogic.mouseCenti.segList)
    for (el <- gameLogic.mouseCenti.legList) {
      drawSegments(el.head.segList)
      drawSegments(el.last.segList)
    }
    //drawSegments(gameLogic.mouseCenti.legsList)
  }

  private def drawSegments(segList : List[Segments]): Unit = {
    strokeWeight(5)
    for (seg <- segList) {
      stroke(seg.rgb._1,seg.rgb._2,seg.rgb._3)
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