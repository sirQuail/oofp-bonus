import processing.core.{PApplet, PConstants, PVector}

class GameBase extends PApplet {
  private val gameLogic = new GameLogic
  private val updateTimer = new UpdateTimer(60)

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

    //draw
    drawSegments(gameLogic.mouseRope.segList)
    gameLogic.mouseRope match {
      case rope : Centipede  =>
        drawSegmentFollowers(rope)
        drawLegs(rope)
      case _ =>
    }
  }

  private def drawLegs(rope : Centipede): Unit = {
    for (el <- rope.legList) {
      drawSegments(el.head.segList)
      drawSegments(el.last.segList)
    }
  }

  private def drawSegmentFollowers(rope : Centipede): Unit = {
    strokeWeight(10)
    stroke(255,0,0)
    for (el <- rope.segmentFollowers) {
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

  private class UpdateTimer(val framesPerSecond: Float) {
    private val frameDuration: Float = 1000 / framesPerSecond
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