import com.jaunt.UserAgent
import scala.collection.JavaConversions._

case class Link(link :String);

class Page {
  this: Dependencies =>

  def printLink(link:Link,level :Int) = {
    for (i <- 1 to level) out.print(">")
    out.print(" "+link.link+"\n")
  }

  def pullPage(link:Link) :List[Link] = {
    val userAgent = new UserAgent()
    if (link.link.substring(0,4).equals("http"))
      userAgent.visit(link.link)
    else if (link.link.substring(0,4).equals("file"))
      userAgent.open(
        new java.io.File(
          getClass().getClassLoader()
            .getResource(link.link.substring(7))
            .getFile))
    else return List()
    val href =
      userAgent
      .doc
      .findEvery("<a>").toList.toList
      .filter(_.hasAttribute("href"))
      .map(x=>Link(x.getAt("href")))
    val img =
      userAgent
        .doc
        .findEvery("<img>").toList.toList
        .filter(_.hasAttribute("src"))
        .map(x=>Link(x.getAt("src")))
    img ::: href
  }

  def visitChildren(links :List[Link],count :Int, domain :String) :Unit = {
    val (nohttp, http) = links.partition(x => !x.link.startsWith("http"))
    http.filter(_.link.split("/")(2).equals(domain)).union(nohttp).foreach {
      x =>
      scraper.visit(x.link,count, domain)
    }
  }
}
