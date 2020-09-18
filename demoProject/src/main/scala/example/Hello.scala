package example

import zio.App
import zio._
import zio.console._
import zio.duration._

import scala.language.postfixOps

object Hello extends App {
  def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    combine.fold(
      err => ExitCode(1),
      ok => ExitCode(0)
    )

  val answerToLife: ZIO[Any, Nothing, Int] = ZIO.succeed(21).map(_ * 2)

  val printAnswerToLife = answerToLife.flatMap(answer =>
    putStrLn(s"The answer to Life, the Universe and Everything is $answer")
  )

  val askForValueAndMultiplyBy2 = for {
    _ <- putStrLn("Please enter a value to multiply by 2")
    numberString <- getStrLn
    number <- ZIO(numberString.toInt * 2)
    _ <- putStrLn(s"$numberString multiplied by 2 is $number")
  } yield ()

  def counter(maxValue: Int, currentValue: Int): ZIO[ZEnv, Unit, Unit] = {
    if (currentValue >= maxValue)
      putStrLn("Counter complete!!!")
    else
      putStrLn(s"Current value is $currentValue") *>
        ZIO.unit.delay(100 milliseconds) *>
        counter(maxValue, currentValue + 1)
  }

  val longRunning = ZIO.unit.delay(3 seconds) *> putStrLn("I've waited long enough!!!")

  val combine = counter(1000, 0) race longRunning
}