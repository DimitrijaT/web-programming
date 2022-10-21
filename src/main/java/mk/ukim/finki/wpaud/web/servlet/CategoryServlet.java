package mk.ukim.finki.wpaud.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "category", urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {


    class Category {
        private String name;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Category(String name) {
            this.name = name;
        }

        public Category(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    private List<Category> categoryList = null;

    @Override
    public void init() throws ServletException {
        super.init();
        categoryList = new ArrayList<>();
        this.categoryList.add(new Category("Software", "Software Category"));
        this.categoryList.add(new Category("Books", "Books Category"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ipAddress = req.getRemoteAddr();
        String clientAgent = req.getHeader("User-Agent");

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h3>User Info</h3>");
        out.format("IP Address: %s", ipAddress);

        out.println("<h3>Client Agent</h3>");
        out.format("IP Address: %s", clientAgent);

        out.println("<h3>Category List</h3>");
        out.println("<ul>");
        this.categoryList.stream().forEach(x -> out.format("<li>%s (%s)</li>", x.getName(), x.getDescription()));
        out.println("</ul>");

        out.println("<h3>Add Category</h3>");
        out.println("<form method='POST' action='/servlet/category'>");
        out.println("<label for='name' >Name: </label>");
        out.println("<input id='name' type='text' name='name'/>");
        out.println("<label for='desc' >Description: </label>");
        out.println("<input id='desc' type='text' name='desc'/>");
        out.println("<input type='submit' value='submit'/>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        String categoryDesc = req.getParameter("desc");
        addCategory(categoryName, categoryDesc);
        resp.sendRedirect("/servlet/category");
    }

    public void addCategory(String name, String desc) {
        if (name != null && !name.isEmpty() && desc != null && !desc.isEmpty()) {
            this.categoryList.add(new Category(name, desc));
        }
    }
}
