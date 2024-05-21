package com.example.demo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "laba5_mysql", value = "/rep")
public class laba5_mysqlServlet extends HttpServlet {
    private lab5_mysql SQLREP;

    @Override
    public void init() throws ServletException {
        super.init();
        SQLREP = new lab5_mysql();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            List<Rep> reps = SQLREP.getAll();
            Gson gson = new GsonBuilder().create();
            out.print(gson.toJson(reps));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при чтении списка книги из базы данных");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().create();
        Rep rep = gson.fromJson(request.getReader(), Rep.class);

        try {
            SQLREP.add(rep);
            // После вставки записи перенаправляем на GET для обновления списка
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при записи книги в базу данных");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            SQLREP.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Gson gson = new GsonBuilder().create();
        Rep rep = gson.fromJson(request.getReader(), Rep.class);

        try {
            SQLREP.update(rep);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}