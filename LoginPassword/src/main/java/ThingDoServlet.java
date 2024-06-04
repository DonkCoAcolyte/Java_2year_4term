import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ThingDoServlet", value = "/thing.do")
public class ThingDoServlet extends ContextServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// Установить кодировку HTTP-ответа UTF-8
        response.setCharacterEncoding("utf8");
// Получить доступ к потоку вывода HTTP-ответа
        PrintWriter pw = response.getWriter();

        pw.println("<html><body>");
        pw.println(request.getSession().getAttribute("login"));
        pw.println("</body></html>");
    }
}

//// Записть в поток HTML-разметку страницы
//        pw.println("<html><head><meta http-equiv='Content-Type' content='text/html' charset='utf-8'><meta http-equiv='refresh' content='1'></head>");
//        pw.println("<body>");
//// В обратном порядке записать в поток HTML-разметку для каждого user
//        for (int i=activeUsers.size()-1; i>=0; i--) {
//            User aUser = activeUsers.get(i);
//            pw.println("<div><strong>" + aUser.getLogin()
//                    + "</strong>: " +  aUser.getData().getChosenNumber() + "</div>");
//        }