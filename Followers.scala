import processing.core.{PApplet, PVector}
/*
Followers are points that, follow a target.
I use these to help Rope subclasses move the way I want without dealing without complicating the follow code in Segments.
This of course is at the cost of performance given each follower is a new object, so new Rope = new Follower.
Furthermore, each leg has its own follower. There is a method to reduce the use of followers discussed in the SegmentFollower part.
 */

abstract class Followers(var initialX : Float = 0, var initialY : Float = 0) {
  var position : PVector = new PVector(initialX,initialY)
  def follow(target : PVector): Unit

  def closeTo(pVector: PVector, range: Float): Boolean = { // Used to stop followers from shaking when on target
    val distance = PApplet.dist(position.x, position.y, pVector.x, pVector.y)
    distance <= range
  }
}

//Follower that moves with constant speed
class ConstantFollower(speed : Int) extends Followers(){
  def follow(target : PVector) : Unit = {
    val direction = PVector.sub(target, position)
    direction.normalize()
    direction.mult(speed)

    if (!closeTo(target, 14)) //range at which to stop following target
      position.x += direction.x
      position.y += direction.y
  }
}

//Follower that moves at speed of target
class TeleportFollower() extends Followers(){
  def follow(target : PVector): Unit = {position = target}
}

/*
Follower that tracks next step, used for legged segments.

This method is from RujiK: https://twitter.com/TheRujiK/status/969581641680195585
Another way to deal with steps is to give each segment two points at the front representing where the feet should be.
if the feet stray far from the two points move them towards their supposed place. This resolves us from having SegmentFollowers.
 */
class SegmentFollower(side : Float) extends Followers() {
  def follow(segment: Segments) : Unit = {
    position =
      if (!closeTo(segment.b, 80)) {
        val normalVec: PVector = new PVector(segment.b.x * -1, segment.b.y).normalize()
        normalVec.mult(30 * side) //side matters for which side the leg is on (1 or -1)
        PVector.add(normalVec, segment.b)
      }
      else position
  }
  def follow(target : PVector): Unit = throw new Error("SegmentFollower uses segments")
}