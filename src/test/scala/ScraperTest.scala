import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, FlatSpec}

class ScraperTest
  extends FlatSpec
  with Matchers
  with MockFactory {

  "visit" should "print link, pull page, and visit children" in{
    class PageD extends Page with Dependencies
    val pageMock = mock[PageD]

    (pageMock.printLink _) expects(Link("hello.html"),1)
    (pageMock.pullPage _) expects(Link("hello.html")) returning (List(Link("hi")))
    (pageMock.visitChildren _) expects(List(Link("hi")),1,"")

    val scraper = new Scraper() with Dependencies{
      lazy override val page = pageMock
    }

    scraper.visit("hello.html",0,"")
  }
  "startVisit" should "print link, pull page, and visit children" in {
    class PageD extends Page with Dependencies
    val pageMock = mock[PageD]

    (pageMock.printLink _) expects(Link("http://www.google.com/hello.html"), 1)
    (pageMock.pullPage _) expects (Link("http://www.google.com/hello.html")) returning (List(Link("hi")))
    (pageMock.visitChildren _) expects(List(Link("hi")),1,"www.google.com")

    val scraper = new Scraper() with Dependencies {
      lazy override val page = pageMock
    }

    scraper.startVisit("http://www.google.com/hello.html")
  }
}
