package controllers

import java.io.File

import play.api._
import play.api.mvc._
import play.api.data._
import play.Play

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  /** load an HTML page from public/html */
  def loadPublicHTML(any: String) = Action {
    val projectRoot = Play.application().path()
    var file = new File(projectRoot + getURI(any))
    if (file.exists())
      Ok(scala.io.Source.fromFile(file.getCanonicalPath()).mkString).as("text/html");
    else
      NotFound
  }

  /** resolve "any" into the corresponding HTML page URI , required by angular JS*/
  private def getURI(any: String): String = any match {
    case _ =>  "/public/partials/" + any +".html"
  }
}