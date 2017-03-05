package com.kruchkov.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kruchkov.dao.*;

public class MyServlet extends HttpServlet {
    public void init() throws ServletException {
        super.init();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

    }

    private boolean signIn(String login, String pass) throws MyDaoException {
        UserDao userDao = new JdbcUserDao();
        User user = userDao.findByLogin(login);
        if (user != null) {
            if (user.getLogin().equals(login) || user.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int action = Integer.parseInt(request.getParameter("action"));
        String login = null;
        String pass = null;
        switch (action) {
            case 1:
                login = request.getParameter("login");
                pass = request.getParameter("pass");
                try {
                    signIn(login, pass);
                    request.setAttribute("sign_in_success", true);
                    request.setAttribute("user_login", login);
                    request.setAttribute("user_status", "1");
                } catch (MyDaoException e) {
                    request.setAttribute("error_message", e.getMessage());
                    request.setAttribute("sign_in_success", false);

                } finally {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;

            default: break;
        }
    }

    public void destroy() {
        super.destroy();
    }
}