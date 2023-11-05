package serve

import java.io.PrintStream
import java.net.ServerSocket
import scala.io.BufferedSource
import scala.language.postfixOps
import scala.util.Try

trait Servable:

  def listen(port:Int): Unit =
    val server = new ServerSocket(port)

    while true
    do
      val s = server.accept()
      val in = new BufferedSource(s.getInputStream()).getLines()
      val out = new PrintStream(s.getOutputStream())

      while Try(in.hasNext).getOrElse(false)
      do

        println(in.next())

        val chunked = """HTTP/1.1 200 OK
                          |Content-Type: text/plain
                          |Transfer-Encoding: chunked
                          |
                          |7
                          |Mozilla
                          |
                          |7
                          |Mozilla
                          |
                          |0""".stripMargin


        out.write(chunked.getBytes("utf-8") )


        out.flush()
        out.close()




object Main extends  App with Servable:
  listen(8080)
