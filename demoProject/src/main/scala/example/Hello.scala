package example

import zio.App
import zio._
import zio.console._

object Hello extends App {
  def run(args: List[String]) =
    askForAValueAndMultiplyBy2.fold(
      _ => ExitCode(1),
      _ => ExitCode(0)
    )

  val answerToLife = IO.succeed(21).map(_ * 2)

  val printAnswerToLife = answerToLife.flatMap(answer =>
    putStrLn(s"The answer to Life, the Universe and Everything is $answer")
  )

  val askForAValueAndMultiplyBy2: ZIO[Console, Throwable, Unit] = for {
    _ <- putStrLn("Hello! What value do you want to multiply by 2?")
    numberStr <- getStrLn
    number <- ZIO(numberStr.toInt * 2)
    _ <- putStrLn(s"'$numberStr' multiplied by 2 is '$number'")
  } yield ()
}
