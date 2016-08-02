import org.scalatest._

class ScraperAcceptanceTest extends FlatSpec with Matchers {
  "scraper" should "follow one link" in{
    val stringBuffer = new StringBuffer()

    Dependencies.out = new Output {
      override def print(x :String) = stringBuffer.append(x);
    }
    Scraper.main(Array("0","file://test.html"))
    assert(stringBuffer.toString.equals("> file://test.html\n>> test2.html\n"))
  }
  "scraper" should "follow a bunch of links from google" in{
    val stringBuffer = new StringBuffer()

    Dependencies.out = new Output {
      override def print(x :String) = stringBuffer.append(x);
    }
    Scraper.main(Array("0","http://www.google.com/"))
    assert(stringBuffer.toString.equals("> test.html\n>> test2.html\n"))
  }
}
