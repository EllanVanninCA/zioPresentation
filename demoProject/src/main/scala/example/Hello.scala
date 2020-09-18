package example

import zio._
import zio.console._
import zio.duration._

import scala.language.postfixOps

object Hello extends App {
  def run(args: List[String]): URIO[ZEnv, ExitCode] =
    countWhileWaiting.fold(
      _ => ExitCode(1),
      _ => ExitCode(0)
    )

  val answerToLife: ZIO[Any, Nothing, Int] = IO.succeed(21).map(_ * 2)

  val printAnswerToLife: ZIO[Console, Nothing, Unit] = answerToLife.flatMap(answer =>
    putStrLn(s"The answer to Life, the Universe and Everything is $answer")
  )

  val askForAValueAndMultiplyBy2: ZIO[Console, Throwable, Unit] = for {
    _ <- putStrLn("Hello! What value do you want to multiply by 2?")
    numberStr <- getStrLn
    numberTimesTwo <- ZIO(numberStr.toInt * 2)
    _ <- putStrLn(s"'$numberStr' multiplied by 2 is '$numberTimesTwo'")
  } yield ()

  def counter(
      maxValue: Int = 10000,
      currentValue: Int = 0
  ): ZIO[ZEnv, Unit, Unit] = {
    if (currentValue >= maxValue)
      putStrLn("Finished !")
    else
      putStrLn(s"Current value is $currentValue") *>
        ZIO.unit.delay(100 milliseconds) *> counter(
        maxValue,
        currentValue + 1
      )
  }

  val waitAndComplete: ZIO[ZEnv, Nothing, Unit] = for {
    _ <- ZIO.unit.delay(3 seconds)
    _ <- putStrLn("I've waited for long enough!")
  } yield ()

  val countWhileWaiting: ZIO[ZEnv, Unit, Unit] = counter(1000000000) race waitAndComplete
}
