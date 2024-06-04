import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.HashMap;

@WebServlet(name = "ContextServlet", value = "/ContextServlet")
public class ContextServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Карта текущих zareganyx пользователей
    protected HashMap<String, User> registeredUsers;
    protected HashMap<String, User> activeUsers;
    protected HashMap<String, String> passLogPairs;
    @SuppressWarnings("unchecked")
    public void init() throws ServletException {
// Вызвать унаследованную от HttpServlet версию init()
        super.init();
// Извлечь из контекста карту пользователей и список сообщений
        registeredUsers = (HashMap<String, User>)
                getServletContext().getAttribute("registeredUsers");
        passLogPairs = (HashMap<String, String>)
                getServletContext().getAttribute("passLogPairs");
        activeUsers = (HashMap<String, User>)
                getServletContext().getAttribute("activeUsers");
// Если карта пользователей не определена ...
        if (registeredUsers ==null) {
// Создать новую карту
            registeredUsers = new HashMap<String, User>();
// Поместить еѐ в контекст сервлета,
// чтобы другие сервлеты могли до него добраться
            getServletContext().setAttribute("registeredUsers",
                    registeredUsers);
        }
        if (activeUsers ==null) {
// Создать новую карту
            activeUsers = new HashMap<String, User>();
// Поместить еѐ в контекст сервлета,
// чтобы другие сервлеты могли до него добраться
            getServletContext().setAttribute("activeUsers",
                    activeUsers);
        }
        if (passLogPairs ==null) {
// Создать новую карту
            passLogPairs = new HashMap<String, String>();
// Поместить еѐ в контекст сервлета,
// чтобы другие сервлеты могли до него добраться
            getServletContext().setAttribute("passLogPairs",
                    passLogPairs);
        }
    }
}