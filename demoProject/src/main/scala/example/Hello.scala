package example

import zio.App
import zio._

object Hello extends App {
  def run(args: List[String]) =
    ZIO.succeed(
      ExitCode(0)
    )
}
