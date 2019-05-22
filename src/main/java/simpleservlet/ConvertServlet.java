package simpleservlet;

import java.io.IOException;

import java.util.List;
import java.util.Properties;
import java.io.PrintWriter;
import java.sql.*;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/")
public class ConvertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Inject
    private ConvertService convertService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws IOException {
      int input;
      String message;

      response.setContentType("text/html");
      response.setCharacterEncoding("UTF-8");

      List<Convert> history = convertService.findAll();

      PrintWriter writer = response.getWriter();
      writer.append("<!DOCTYPE html>\n")
      .append("<html>\n")
      .append("		<head>\n")
      .append("			<title>Input</title>\n")
      .append("		</head>\n")
      .append("		<body>\n")
      .append("			<form action=\"convert\" method=\"POST\">\n")
      .append("				Enter number: \n")
      .append("				<input type=\"text\" name=\"input\" />\n")
      .append("				<input type=\"submit\" value=\"Convert\" />\n")
      .append("			</form>\n");
      if(!(history.isEmpty())) {
        writer.append("    <p>Your history is: </p>\n")
        .append("     <ol>\n");
        for(Convert convert: history) {
          writer.append("       <li>" + convert + "</li>\n");
        }
        writer.append("     </ol>\n");
      } else {
        writer.append("     <p>You have no history yet!</p>\n");
      }
      writer.append("		</body>\n")
      .append("</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                  throws IOException {
      int input;

      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setContentType("text/html");
      response.setCharacterEncoding("UTF-8");


      PrintWriter writer = response.getWriter();
      writer.append("<!DOCTYPE html>\n")
      .append("<html>\n")
      .append("		<head>\n")
      .append("			<title>Converted!</title>\n")
      .append("		</head>\n")
      .append("   <body>\n");
      try{
        input = Integer.parseInt(request.getParameter("input"));

        convertService.inputData(input);
        Integer inputI = Integer.valueOf(input);

        if (inputI != null) {
          writer.append("	   <p>Your input: " + inputI + "	is successfully completed conversion.</p>\n");
        } else {
          writer.append("	   <p>You did not enter a number!</p>\n");
        }
      } catch(NumberFormatException e) {
        String errorMessage = e.getMessage();
        writer.append("    <p>Error: " + errorMessage + ". Enter a number.</p>");
      }
      writer.append("   </body>\n")
      .append("</html>\n");

      doGet(request, response);
    }

    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

