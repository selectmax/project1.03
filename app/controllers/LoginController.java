package controllers;

import models.Order;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;

import static play.data.Form.form;


public class LoginController extends Controller {
    private FormFactory formFactory;
    @Inject

    public LoginController(FormFactory formFactory) {this.formFactory = formFactory;}

    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();

        Form<Register> registerForm = form(Register.class);

        if (loginForm.hasErrors()) {
            return badRequest(index.render(registerForm, loginForm));
        } else {
            session("login", loginForm.get().login);
            return redirect(routes.HomeController.index() );
        }
    }

    public Result index() {
        // Check that the email matches a confirmed user before we redirect
        String login = ctx().session().get("login");
        if (login != null) {
            User user = User.findBylogin(login);
            if (user != null) {
                return redirect(routes.HomeController.index() );
            } else {
                Logger.debug("Clearing invalid session credentials");
                session().clear();
            }
        }

        return ok(index.render(form(Register.class), form(Login.class))); // возвращает форму логина и регистрации, которые обернуты в class
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
    public static class Login {  // класс логина static!

        @Constraints.Required //
        public String login;
        @Constraints.Required
        public String password;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() { // метод класса Login, который проверяет login и пароль

            User user = null; // создается объект типа User
            try {
                user = User.authenticate(login, password);  // вызывается метод authenticate, для проверки email и пароль
            } catch (Exception e) {
                return "error.technical";
            }
            if (user == null) {
                return "invalid.user.or.password";
            }
            return null;
        }

    }

    public static class Register {

        @Constraints.Required
        public String email;

        @Constraints.Required
        public String fullname;

        @Constraints.Required
        public String age;

        @Constraints.Required
        public String inputPassword;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {     // метод записи данных от пользователя
            if (isBlank(email)) {
                return "Email is required";
            }

            if (isBlank(fullname)) {
                return "Full name is required";
            }

            if ( isBlank(age)){
                return "Age is required";
            }

            if (isBlank(inputPassword)) {
                return "Password is required";
            }

            return null;
        }

        private boolean isBlank(String input) {
            return input == null || input.isEmpty() || input.trim().isEmpty();
        }
    }




}
