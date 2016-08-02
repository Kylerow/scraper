
trait Dependencies {
  lazy val page = new Page() with Dependencies
  lazy val scraper = new Scraper() with Dependencies

  lazy val out = new Output{
    override  def print(x :String) = {
      Dependencies.out.print(x);
      System.out.print(x);
    }
  }
}
object Dependencies {
  var out = new Output{
    override  def print(x :String) = {}
  }
}