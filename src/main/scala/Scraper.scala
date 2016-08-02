import java.security.DomainCombiner

import scala.collection.mutable

class Output {
  def print(x :String) = System.out.print(x)
}

object Scraper {
  def main(args: Array[String]) = {
    (new Scraper with Dependencies).startVisit(args(1))
  }
  val visited = new mutable.ArrayBuffer[String]
}

class Scraper {
    this: Dependencies =>

  def startVisit(pageReference :String) = {
    val domain = if (pageReference.startsWith("http")){
      val pieces = pageReference.split("/")
      pieces(2)
    } else ""
    visit(pageReference,0,domain);
  }

  def visit(pageReference :String, count :Int, domain :String) = {
    if (!Scraper.visited.contains(pageReference)) {
      Scraper.visited.append(pageReference)

      val link = Link(pageReference)
      val newCount = count + 1

      page.printLink(link, newCount)
      page.visitChildren(page.pullPage(link), newCount, domain)
    }
  }
}

