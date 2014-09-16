package controllers

import java.io.File

import play.api._
import play.api.mvc._
import play.api.data._
import play.Play

object HelloWorld extends Controller {

  def list = Action {
    Ok("default")
  }

  def get(code : String) = Action {
    Ok("test " + code)
  }
}