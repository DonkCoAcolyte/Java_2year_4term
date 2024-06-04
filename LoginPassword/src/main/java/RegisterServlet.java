import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", value = "/register.do")
public class RegisterServlet extends ContextServlet {
    private static final long serialVersionUID = 1L;
    public void init() throws ServletException {
        super.init();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String errorMessage =
                (String)request.getSession().getAttribute("error");
// Пользователю необходимо ввести имя. Показать форму
// Задать кодировку HTTP-ответа
        response.setCharacterEncoding("utf8");
// Получить поток вывода для HTTP-ответа
        PrintWriter pw = response.getWriter();
        pw.println("<html><head><title>Мега-чат!</title><meta httpequiv='Content-Type' content='text/html'; charset='UTF-8'/></head>");
// Если возникла ошибка - сообщить о ней
        if (errorMessage!=null) {
            pw.println("<p><font color='red'>" + errorMessage +
                    "</font></p>");
        }
// Вывести форму
        pw.println("<form action='' method='post'>REGISTRATION Введите логин: <input type='text' name='login' value=''>; Введите пароль <input type='text' name='password' value=''><input type='submit' value='register'>");
        pw.println("</form></body></html>");
// Сбросить сообщение об ошибке в сессии
        request.getSession().setAttribute("error", null);
    }
    // Метод будет вызван при обращении к сервлету HTTP-методом POST
// т.е. когда пользователь отправляет сервлету данные
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// Задать кодировку HTTP-запроса - очень важно!
// Иначе вместо символов будет абракадабра
        request.setCharacterEncoding("UTF-8");
// Извлечь из HTTP-запроса значение параметра 'login'
        String login = (String)request.getParameter("login");
        String password = (String)request.getParameter("password");
// Полагаем, что изначально ошибок нет
        String errorMessage = null;
        if (login==null || "".equals(login) || password==null || "".equals(password) ) {
// Пустое имя недопустимо - сообщить об ошибке
            errorMessage = "login и password пользователя не может быть пустым!";
        } else {
// Если ия не пустое, то попытаться обработать запрос
            errorMessage = processRegisterAttempt(login, password, request, response);
        }
        if (errorMessage!=null) {
// Сбросить имя пользователя в сессии
            request.getSession().setAttribute("login", null);
            request.getSession().setAttribute("password", null);
// Сохранить в сессии сообщение об ошибке
            request.getSession().setAttribute("error", errorMessage);
// Переадресовать обратно на исходную страницу с формой
            response.sendRedirect(response.encodeRedirectURL("/register.do"));
        }
    }
    // Возвращает текстовое описание возникшей ошибки или null
    String processRegisterAttempt(String login, String password, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
// Определить идентификатор Java-сессии пользователя
        String sessionId = request.getSession().getId();
// Извлечь из списка объект, связанный с этим именем
        User aUser = registeredUsers.get(login);
        if (aUser==null) {
            String loginPairedWithPassword = passLogPairs.get(password);
            if (loginPairedWithPassword != null) return "Извините, но password <strong>" + password + "</strong> уже занят пользователем с логином " + loginPairedWithPassword + ". Пожалуйста выберите другой password!";
// Если оно свободно, то добавить
// нового пользователя в список активных
            aUser = new User(login, password, sessionId);
// Так как одновременно выполняются запросы
// от множества пользователей
// то необходима синхронизация на ресурсе
            synchronized (registeredUsers) {
                registeredUsers.put(aUser.getLogin(), aUser);
            }
            synchronized (passLogPairs) {
                passLogPairs.put(password, login);
            }
// Перейти к login
            response.sendRedirect(response.encodeRedirectURL("/"));
// Вернуть null, т.е. сообщений об ошибках нет
            return null;
        } else {
// Сохранѐнное в сессии имя уже закреплено за кем-то другим.
// Извиниться, отказать и попросить ввести другое имя
            return "Извините, но login <strong>" + login + "</strong> уже занят. Пожалуйста выберите другой login!";
        }
    }
}