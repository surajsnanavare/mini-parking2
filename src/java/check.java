/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Suraj Nanavare
 */
public class check extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            long s_time=0;
            long e_time=0;
            long t_time=0;
            Connection conn=null;
            Statement st=null;
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/miniparking?zeroDateTimeBehavior=convertToNull","root","");
                st=conn.createStatement();
        
            }
            catch(Exception e)
            {
                   out.println("<div class=\"w3-container w3-red w3-animate-top \">\n" +
                   "  <p><h6 style=\"w3-center\"><strong class=\"\">Server if Offline!</strong></h5></p>\n" +
                   "</div>");
            }
            
            out.println("<!DOCTYPE html>"
                    + "<html>\n" 
                    +"    <head>\n" 
                    +"        <title>MiniParking</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"        <link rel=\"stylesheet\" href=\"w3.css\" type=\"text/css\"/>\n"+
"        <script>function msg(){alert(\"Checked Out Thank You!\");};</script>   "+
"    </head>");
            out.println("<body>\n" +
"        <div class=\"w3-container w3-teal w3-center\"><h1>MiniParking</h1></div>\n" +
"        <div class=\"w3-container w3-padding-16 w3-light-grey w3-padding-16\" style=\"min-height: 150px;\">\n" +
"        \n" +
"            <center>\n" );
             out.println("<h3>Four Wheeler</h3>");
            String q1="select * from slot";
            ResultSet rs = st.executeQuery(q1);
            int i=1;
            while (rs.next())
            {
                if(rs.getString(2).equals("A"))
               out.println (" <div id=\""+i+"\" class=\"w3-btn-floating-large w3-green\">"+i+"</div>\n"); 
                else
               out.println (" <div id=\""+i+"\" class=\"w3-btn-floating-large w3-red\">"+i+"</div>\n"); 
             i++;       
            }
           out.println("<br><h3>Two Wheeler</h3>");
            q1="select * from slot2";
            rs = st.executeQuery(q1);
            i=1;
            while (rs.next())
            {
                if(rs.getString(2).equals("A"))
               out.println (" <div id=\""+i+"\" class=\"w3-btn-floating-large w3-green\">"+i+"</div>\n"); 
                else
               out.println (" <div id=\""+i+"\" class=\"w3-btn-floating-large w3-red\">"+i+"</div>\n"); 
             i++;       
            }
            
            
            
           out.println(" </center>\n" +
"        </div> \n" +
"        <form action=\"\" method=\"post\">\n" +
"            <div class=\"w3-row w3-padding-32\">\n" +
"                <div class=\"w3-col m3 l3\">\n" +
"                .\n" +
"                </div>\n" +
"                <div class=\"w3-col m4 l4\">\n" +
"                    \n" +
"                    <div class=\"  w3-border\"  style=\"width:70%;margin-left: 15%; \">\n" +
"                        <div class=\"w3-container w3-blue\">\n" +
"                        <h2>Registration No.</h2>\n" +
"                        </div>\n" +
"                        \n" +
"                        <div class=\" w3-padding-32\" style=\"width: 80%;margin-left: 10%\">\n" +
"                        <label>Enter Last 4 digits of Vehicle No.</label>\n" +
"                        <input class=\"w3-input\" name=\"reg_id\" type=\"number\" required>\n"+
                   " <input class=\"w3-radio\" type=\"radio\" name=\"type\" value=\"two\" checked>\n" +
"                   <label class=\"\">2 Wheeler</label>\n" +
"\n" +
                    "<input class=\"w3-radio\" type=\"radio\" name=\"type\" value=\"four\">\n" +
                    "<label class=\"\">4 Wheeler</label>" +
"                        </div>\n" +
"                    </div>\n" +
"                </div>\n" +
"                <div class=\"w3-col m5 l5\">\n" +
"                    <center>\n" +
"                        <br>\n" +
"                    <input type=\"submit\"  class=\"w3-btn w3-large\" value=\"&nbsp;Check In&nbsp;\" name=\"check_in\"><br><br>\n" +
"                    <input type=\"submit\" class=\"w3-btn w3-large\" value=\"Check Out\" name=\"check_out\"><br><br>\n" +
"                    <input type=\"submit\" class=\"w3-btn w3-large\" value=\"Check Slot\" name=\"check_slot\">\n" +
"                    </center>\n" +
"                </div>\n" +
"            </div>\n" +
"        </form>\n" +
"\n" +
"    </body>");
            out.println("</html>");
     
       if(request.getParameter("check_in")!=null)
       {
           int rid=Integer.parseInt(request.getParameter("reg_id"));
            String type=request.getParameter("type");
           if(type.equals("four"))
            q1="select * from slot where status=\"A\" limit 1";
           else if(type.equals("two"))
            q1="select * from slot2 where status=\"A\" limit 1";   
            
           rs = st.executeQuery(q1);
            int flag=0;
             while (rs.next())
            {
                 flag=1;
                 int sno=rs.getInt("no");
                s_time=System.currentTimeMillis();
                 String q="insert into park(reg_id,slot_no,start_time,type) values('"+rid+"','"+sno+"','"+s_time+"','"+type+"')";
               String q2=null;
                  i=st.executeUpdate(q);
                 if(i>=0)
                 {
                     if(type.equals("four"))
                      q2="update slot set status=\"NA\" where no="+sno;
                     if(type.equals("two"))
                      q2="update slot2 set status=\"NA\" where no="+sno;
                     
                     st.executeUpdate(q2);
                       
                    
                 }
                 
             out.println("<div class=\"w3-container w3-teal w3-animate-bottom \">\n" +
              "  <h2><b>You are Checked In!</b></h2>\n" +
              "  <h3 style=\"font-family:cursive;\"><strong>Your vehicle is parked at Slot No:"+sno+" </strong></h3>\n" +
              "</div>");
                              
            }
             if(flag==0)
             {
                                    out.println("<div class=\"w3-container w3-red w3-animate-bottom\">\n" +
                "  <h2><b>Ohh Sorry..!</b></h2>\n" +
                "  <p><h3 style=\"font-family:cursive;\"><strong>No slot Available! </strong></h3></p>\n" +
                "</div>");
             }
       }
       
      else if(request.getParameter("check_out")!=null)
       {
             String type=request.getParameter("type");     
             int rid=Integer.parseInt(request.getParameter("reg_id"));
              q1="select start_time from park where reg_id="+rid;
              rs = st.executeQuery(q1);
                while (rs.next())
                {
                   s_time=rs.getLong("start_time");
                }
             e_time=System.currentTimeMillis();
             t_time=e_time-s_time;
             
             long amt=t_time/60000;
             q1="select * from park where reg_id="+rid;
             String q2=null;
              rs = st.executeQuery(q1);
             int flag=0;
             int sno=0;
           
                while (rs.next())
                {
                    flag=1;
                    sno=rs.getInt("slot_no");
                    String q3=null;
                    if(type.equals("four"))
                    {
                     q2="UPDATE `slot` SET `status`=\"A\" WHERE no="+sno;

                    st.executeUpdate(q2);
                     q3="delete from park where reg_id="+rid;
                    }
                    else if(type.equals("two"))
                    {
                     q2="UPDATE `slot2` SET `status`=\"A\" WHERE no="+sno;
                    st.executeUpdate(q2);
                      q3="delete from park where reg_id="+rid;
                    }
                  
                    st.executeUpdate(q3);
                    out.println("<div class=\"w3-container w3-teal w3-animate-bottom \">\n" +
                   "  <h2><b>You Are Checked Out!</b></h2>\n" +
                   "  <p><h3 style=\"font-family:cursive;\"><strong>Amount to Be Paid : RS. "+amt+" </strong></h3></p>\n" +
                   "</div>");

                }

             if(flag==0)
             {
                out.println("<div class=\"w3-container w3-red w3-animate-bottom \" >\n" +
                    "  <h2><b>Not Parked Yet!</b></h2>\n" +
                    "  <p><h3 style=\"font-family:cursive;\"><strong>Vehicle is not parked at any Slot.  </strong></h3></p>\n" +
                    "</div>");
             }
       }
        
      else if(request.getParameter("check_slot")!=null)
       {
              int rid=Integer.parseInt(request.getParameter("reg_id"));
             q1="select * from park where reg_id="+rid;
             rs = st.executeQuery(q1);
             int flag=0;
             int sno=0;
           
                while (rs.next())
                {
                    flag=1;
                    sno=rs.getInt("slot_no");
                      out.println("<div class=\"w3-container w3-teal w3-animate-bottom \">\n" +
                   "  <h2><b>Parked at!</b></h2>\n" +
                   "  <p><h3 style=\"font-family:cursive;\"><strong>Your car is parked at Slot No : "+sno+"</strong></h3></p>\n" +
                   "</div>");
                }
                
             if(flag==0)
             {
                out.println("<div class=\"w3-container w3-red w3-animate-bottom \" >\n" +
                    "  <h2><b>Not Parked Yet!</b></h2>\n" +
                    "  <p><h3 style=\"font-family:cursive;\"><strong>Vehicle is not parked at any Slot.  </strong></h3></p>\n" +
                    "</div>");
             }
       }    
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(check.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(check.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
