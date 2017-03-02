package controllers;

import models.Order;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.dashboard.index;

import javax.inject.Inject;


public class HomeController extends Controller {
    private FormFactory formFactory;
    @Inject

    public HomeController(FormFactory formFactory) {this.formFactory = formFactory;}

    public Result index() {

        Form<Order> orderForm = formFactory.form(Order.class);
        return ok(index.render(orderForm));
    }
//Метод, который связывает и заносит данные из html формы в объект Order
    public Result submit() {
       Form<Order> orderForm = formFactory.form(Order.class).bindFromRequest();
       orderForm.get().save();
       for (Order or: Order.find.all()) {
           System.out.println(or.toString()); //контроль в консоль
       }
        return redirect("/");
    }
}
