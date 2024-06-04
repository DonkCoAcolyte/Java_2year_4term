import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", value = "/login.do")
public class LoginServlet extends ContextServlet {
    private static final long serialVersionUID = 1L;
    // Длительность сессии, в секундах
    private int sessionTimeout = 10*60;

    public void init() throws ServletException {
        super.init();
    }
    // Метод будет вызван при обращении к сервлету HTTP-методом GET
    // т.е. когда пользователь просто открывает адрес в браузере
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String errorMessage =
                (String)request.getSession().getAttribute("error");
/// Пользователю необходимо ввести имя. Показать форму
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
        pw.println("<form action='' method='post'>LOGIN Введите логин: <input type='text' name='login' value=''>; Введите пароль <input type='text' name='password' value=''><input type='submit' value='login'>");
        pw.println("</form> <a href = '/register.do'> register </a> </body></html>");
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
        if (login==null || "".equals(login) || password ==null || "".equals(password)) {
// Пустое имя недопустимо - сообщить об ошибке
            errorMessage = "login пользователя or password не может быть пустым!";
        } else {
// Если ия не пустое, то попытаться обработать запрос
            errorMessage = processLogonAttempt(login, password, request, response);
        }
        if (errorMessage!=null) {
// Сбросить имя пользователя в сессии
            request.getSession().setAttribute("login", null);
            request.getSession().setAttribute("password", null);
// Сохранить в сессии сообщение об ошибке
            request.getSession().setAttribute("error", errorMessage);
// Переадресовать обратно на исходную страницу с формой
            response.sendRedirect(response.encodeRedirectURL("/"));
        }
    }
    // Возвращает текстовое описание возникшей ошибки или null
    String processLogonAttempt(String login, String password, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
// Определить идентификатор Java-сессии пользователя
        String sessionId = request.getSession().getId();
// Извлечь из списка объект, связанный с этим именем
        User aUser = registeredUsers.get(login);
        if (aUser==null) {
            return "user with login " + login + " does not exist";
        } else if (!password.equals(aUser.getPassword())){
            return "wrong password, its " + aUser.getPassword() + ", not " + password;
        } else{

            aUser = activeUsers.get(login);
            if (aUser==null) {
// Если оно свободно, то добавить
// нового пользователя в список активных
                aUser = new User(login, password, sessionId);
// Так как одновременно выполняются запросы
// от множества пользователей
// то необходима синхронизация на ресурсе
                synchronized (activeUsers) {
                    activeUsers.put(aUser.getLogin(), aUser);
                }
// Обновить имя пользователя в сессии
                request.getSession().setAttribute("login", login);
// Перейти к главному окну чата
                response.sendRedirect(response.encodeRedirectURL("view.html"));
// Вернуть null, т.е. сообщений об ошибках нет
                return null;
            } else {
// Сохранѐнное в сессии имя уже закреплено за кем-то другим.
// Извиниться, отказать и попросить ввести другое имя
                return "Извините, но аккаунт <strong>" + login + "</strong> уже кем-то занят. Пожалуйста выберите другой!";
            }

        }
    }
}
