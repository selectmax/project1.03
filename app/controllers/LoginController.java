package controllers;

import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;


public class LoginController extends Controller {

    private FormFactory formFactory;

    @Inject
    public LoginController(FormFactory formFactory) {this.formFactory = formFactory;}

    public Result index() {
        String login = session().get("login");
        if (login != null) {
            User user = User.findBylogin(login);
            if (user != null) {
                return redirect(routes.DashboardController.index());
            } else {
                Logger.debug("Clearing invalid session credentials");
                session().clear();
            }
        }
        Form<Login> loginForm = formFactory.form(Login.class);
        Form<Register> registerForm = formFactory.form(Register.class);
        return ok(index.render(registerForm, loginForm)); // возвращает форму логина и регистрации, которые обернуты в class
    }

    public Result auth() {
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        Form<Register> registerForm = formFactory.form(Register.class);
        if (loginForm.hasErrors()) {
            Logger.debug("ERROR loginForm");
            return badRequest(index.render(registerForm, loginForm));
        } else {
            session("login", loginForm.get().login);
            return redirect(routes.DashboardController.index());
        }
    }

    public Result register() {
        Form<Login> loginForm = formFactory.form(Login.class);
        Form<Register> registerForm = formFactory.form(Register.class).bindFromRequest();
        if (registerForm.hasErrors()) {
            return badRequest(index.render(registerForm, loginForm));
        } else {
            session("login", registerForm.get().login);
            return redirect(routes.DashboardController.index());
        }
    }

    public static class Login {

        @Constraints.Required
        public String login;
        @Constraints.Required
        public String password;

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

        //@Constraints.Required
        public String login;

        //@Constraints.Required
        public String inputPassword;

        public String validate() {     // метод записи данных от пользователя
            if (isBlank(login)) {
                return "Login is required";
            }
            if (isBlank(inputPassword)) {
                return "Password is required";
            }

            if (User.findBylogin(login) != null) {
                return "Login is already exists!";
            }

            User user = new User();
            user.login = login;
            user.password = inputPassword;
            user.save();


            return null;
        }

        private boolean isBlank(String input) {
            return input == null || input.isEmpty() || input.trim().isEmpty();
        }
    }
}
