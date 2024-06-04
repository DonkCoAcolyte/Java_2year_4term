import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SendThingServlet", value = "/sendThing.do")
public class SendThingServlet extends ContextServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// По умолчанию используется кодировка ISO-8859. Так как мы
// передаѐм данные в кодировке UTF-8
// то необходимо установить соответствующую кодировку HTTP-запроса
        request.setCharacterEncoding("UTF-8");
// Извлечь из HTTP-запроса параметр 'number'
        String number = (String)request.getParameter("number");
// Если сообщение не пустое, то
        if (number!=null && !"".equals(number)) {
// По имени из сессии получить ссылку на объект ChatUser
            Integer numToSet = Integer.parseInt(number);
            User author = activeUsers.get((String)
                    request.getSession().getAttribute("login"));
            synchronized (author) {
// Добавить в список сообщений новое
                UserData data = author.getData();
                data.setChosenNumber(numToSet);
                author.setData(data);
            }
        }
// Перенаправить пользователя на страницу с формой сообщения
        response.sendRedirect("/do_thing.html");
    }
}