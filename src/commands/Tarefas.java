package commands;

import fanese.web.dao.TarefaDao;
import fanese.web.model.Tarefa;
import minimvc.Command;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Tarefas extends Command {
    public void index() throws ServletException, IOException {
        TarefaDao dao = new TarefaDao();

        // Obtém lista de tarefas a fazer
        List<Tarefa> tarefas = dao.obterLista();
        setAttribute("tarefas", tarefas);

        // Exibe a lista de tarefas para o usuário
        forward("/tarefas.jsp");
    }

    public void criar() throws IOException {
        if ("POST".equals(request.getMethod())) {
            TarefaDao dao = new TarefaDao();

            // Obtém parâmetro da requisição
            String descricao = getParameter("descricao");

            // Prepara o objeto para inserção
            Tarefa tarefa = new Tarefa();
            tarefa.setDescricao(descricao);

            // Tenta inserir a tarefa no banco de dados
            if (!dao.adicionar(tarefa)) {
                response.sendError(400, "Erro inesperado ao inserir");
                return;
            }

            // Redireciona de volta para a lista de tarefas
            redirect(contextUrl("/ctrl?command=Tarefas:index"));
        }

        // Não aceita o método GET
        else {
            response.sendError(405);
        }
    }

    public void completar() throws IOException {
        if ("POST".equals(request.getMethod())) {
            TarefaDao dao = new TarefaDao();

            // Obtém parâmetro da requisição e converte
            String paramId = getParameter("tarefa");
            int id = Integer.parseInt(paramId);

            // Tenta marcar a tarefa como concluída no banco de dados
            if (!dao.concluir(id)) {
                response.sendError(400, "Erro inesperado ao atualizar");
            }
        }

        // Não aceita o método GET
        else {
            response.sendError(405);
        }
    }

    public void excluir() throws ServletException, IOException, ParseException {
        if ("POST".equals(request.getMethod())) {
            TarefaDao dao = new TarefaDao();

            // Obtém parâmetro da requisição e converte
            String paramId = getParameter("tarefa");
            int id = Integer.parseInt(paramId);

            // Tenta excluir a tarefa no banco de dados
            if (!dao.excluir(id)) {
                response.sendError(400, "Erro inesperado ao excluir");
            }
        }

        // Não aceita o método GET
        else {
            response.sendError(405);
        }
    }
}
