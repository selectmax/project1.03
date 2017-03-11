package controllers;

import models.Order;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.Date;

@Security.Authenticated(Secured.class)
public class DashboardController extends Controller {
    private FormFactory formFactory;

    @Inject
    public DashboardController(FormFactory formFactory) {this.formFactory = formFactory;}

    public Result index() {
        Form<Order> orderForm = formFactory.form(Order.class);
        return ok(views.html.dashboard.index.render(orderForm));
    }
    //Метод, который связывает и заносит данные из html формы в объект Order
    public Result submit() {
        Form<Order> orderForm = formFactory.form(Order.class).bindFromRequest(); //Закидывает данные со страницы в orderForm
        if (orderForm.hasErrors()) {
            return badRequest(views.html.dashboard.index.render(orderForm));
        } else {
            //1orderForm.get().client = session().get("login"); //1Закидывает логин в orderForm
            String login = session().get("login");
            //User user = User.findBylogin(login);
            orderForm.get().client = User.findBylogin(login);

            orderForm.get().time = new Date(); //Закидывает инфу о дате в orderForm
            orderForm.get().save();
            for (Order or: Order.find.all()) {
                System.out.println(or.toString()); //контроль в консоль
            }
            return redirect(routes.DashboardController.index());
        }
    }

    public Result logout() {
        session().clear();
        return redirect(routes.LoginController.index());
    }
}
