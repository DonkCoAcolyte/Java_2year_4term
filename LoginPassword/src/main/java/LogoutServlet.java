import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "LogoutServlet", value = "/logout.do")
public class LogoutServlet extends ContextServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String login = (String) request.getSession().getAttribute("login");
// Если в сессии имеется имя пользователя...
        if (login!=null) {
// Получить объект, описывающий пользователя с таким именем
            User aUser = activeUsers.get(login);
// Если идентификатор сессии пользователя, вошедшего под 
// этим именем, совпадает с идентификатором сессии 
// пользователя, пытающегося выйти из чата
// (т.е. выходит тот же, кто и входил)
            if (aUser.getSessionId().equals((String)
                    request.getSession().getId())) {
// Удалить пользователя из списка активных
// Т.к. запросы обрабатываются одновременно, 
// нужна синхронизация
                synchronized (activeUsers) {
                    activeUsers.remove(login);
                }
// Сбросить имя пользователя в сессии
                request.getSession().setAttribute("login", null);
                request.getSession().setAttribute("password", null);
// Перенаправить на главную страницу
                response.sendRedirect(response.encodeRedirectURL("/"));
            } else {
// Пользователь пытается аннулировать чужую сессию –
// не делать ничего
                response.sendRedirect(response.encodeRedirectURL("/view.html"));
            }
        } else {
// Перенаправить пользователя на главное окно
            response.sendRedirect(response.encodeRedirectURL("/"));
        }
    }
}