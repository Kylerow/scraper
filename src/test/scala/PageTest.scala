import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, FlatSpec}

class PageTest
    extends FlatSpec
    with Matchers
    with MockFactory {
  "printlink" should "print the link with the number of levels with >" in {
    val stringBuffer = new StringBuffer()
    val page = new Page with Dependencies{
      override lazy val out = new Output{
        override def print(x :String) = stringBuffer.append(x)
      }
    }
    page.printLink(Link("link"),5)
    System.out.println("stringbuffer="+stringBuffer)
    assert(stringBuffer.toString.equals(">>>>> link"))
  }
  "pullpage" should "return a list of links on the page" in {
    val page = new Page with Dependencies
    val results = page.pullPage(Link("test.html"))

    assert(results(0).link.equals("test2.html"))
  }
  "pullpage" should "return a list of links on google" in {
    val page = new Page with Dependencies
    val results = page.pullPage(Link("http://www.google.com"))

    assert(results(0).link.contains("google"))
  }
  "visit children" should "call scraper.visit for every local link" in {

    class ScraperD extends Scraper with Dependencies
    val mockScraper = mock[ScraperD]

    (mockScraper.visit _).expects("http://xyz.com/test.html",1,"xyz.com")
    (mockScraper.visit _).expects("test.html",1,"xyz.com")
    val page = new Page with Dependencies{
      override lazy val scraper = mockScraper
    }

    page.visitChildren(
      List(
        Link("http://xyz.com/test.html"),
        Link("test.html"),
        Link("http://abc.com/test.html")),
      1,
      "xyz.com")

  }
}
