package commands;

import fanese.web.dao.TagDao;
import fanese.web.model.Tag;
import minimvc.Command;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class Tags extends Command {
    public void opcoes() throws ServletException, IOException {
        String filtro = getParameter("filtro");

        if (filtro != null) {
            TagDao dao = new TagDao();

            List<Tag> tags = dao.obterIniciamPor(filtro);
            setAttribute("tags", tags);

            forward("/tags/opcoes.jsp");
        }
    }
}
