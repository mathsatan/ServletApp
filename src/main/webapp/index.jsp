<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Example</title>
    </head>
    <body>
        <form method="POST" action="/my/servlet">
            <center>
            <table border="0" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Login Page</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="login" value="max" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value="123" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="sign in" /></td>
                    </tr>
                </tbody>
            </table>
            </center>
            <input type="hidden" name="action" value="1" />
        </form>
    </body>
</html>