package example

import zio.App
import zio._
import zio.console._

object Hello extends App {
  def run(args: List[String]) = printAnswerToLife *> ZIO.succeed(
    ExitCode(0)
  )

  val answerToLife = IO.succeed(21).map(_ * 2)

  val printAnswerToLife = answerToLife.flatMap(answer => putStrLn(s"The answer to Life, the Universe and Everything is $answer"))
}
