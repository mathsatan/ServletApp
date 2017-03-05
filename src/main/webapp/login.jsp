<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>asd</title>
    </head>
    <body>
    <%
        Boolean isSuccess = (Boolean) request.getAttribute("sign_in_success");
        if (isSuccess) {
            String login = request.getAttribute("user_login").toString();
            session.setAttribute("user_login", login);
            session.setAttribute("sign_in_success", true);
            out.println("<h2>Hello, " + login + "</h2><a href='/my/log_out.jsp'>log out</a>");
        } else {
            session.setAttribute("sign_in_success", false);
            String err = request.getAttribute("error_message").toString();
            out.println("<h2>sign in fails: " + err + "</h2><a href='/my/index.jsp'>log in</a>");
        }
    %>

    </body>
</html>