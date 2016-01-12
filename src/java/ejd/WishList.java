/*
 * Name: Jin Hwan Oh
 * Due Date: July 24, 2015

 * This web application get wish list item(s) from user, store the information 
 * into cookie (last 1 day) and prints the list to the screen. 
 */
package ejd;

import static ejd.HtmlUtil.printFooter;
import static ejd.HtmlUtil.printHeader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class WishList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Initialize variables
        Cookie cookie;
        String cookieValue = null;
        String item;
        String[] itemList = null;

        // Get cookie if there is existing one
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // Get cookie's value
            for (Cookie c : cookies) {
                if (c.getName().equals("wishList")) {
                    cookieValue = c.getValue();
                    break;
                }
            }
            // Seperate cookie's value by ~
            // For first cookie value, it stores itself
            if (cookieValue != null) {
                itemList = cookieValue.split("~");
            }
        }

        // HTML body content
        String body = "<div id='top'><h1>Wish List</h1>\n"
                + "<form action='WishList' method='GET'>\n"
                + "<input type='text' name='item' >\n"
                + "<input type='submit' value='Add Item' >\n"
                + "</form>\n";

        // close top div
        body += "</div>";
                
        
        body += "<div id=list>\n"
                + "<h3>Your Wishes</h3>\n"
                + "<ol>\n";

        // Get item from the form
        item = request.getParameter("item");

        // Display item in itemList
        if (itemList != null) {
            body += "<table>";
            for (String s : itemList) {
                body += "<tr><td><li>" + s + "</td>"
                        + "<td>"
                        + printUrl(s) + "</li></td></tr>";
            }
        }

        // Display current item
        if (item != null && !item.isEmpty()) {
            body += "<tr><td><li>" + item + "</td><td>" + printUrl(item) + "</li></td></tr>";
        }

        // Close ol and list div
        body += "</table></ol>\n</div>\n";

        // CLose body
        body += "</body>";

        // Check if the field is empty
        if (item != null && !item.isEmpty()) {
            // Add value to Cookie
            if (cookieValue == null) {
                cookieValue = item; // first item
            }
            else {
                cookieValue += "~" + item; // append the value
            }

            // Add cookie 
            cookie = new Cookie("wishList", cookieValue);
            cookie.setMaxAge(60 * 60 * 24); // set max age to 1 day (60 sec * 60 min * 24 hr)
           
            response.addCookie(cookie);
        }

        // HTML Contents
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            printHeader(out, "Wish List");
            out.println(body);
            printFooter(out);
        }
    }
    
    // Get the url of amazon.ca
    private String printUrl(String item){
        
        // Create an instance of Strigbuider with default url
        StringBuilder url = new StringBuilder("https://www.amazon.ca/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=");
        // If the keyword that we are looking for has space(s), url is going to be different
        // Check for for the space and store each keyword to array
        String[] keys = item.split(" ");
        if(item.contains(" ")){
            
            // Print each keyword
            for(int i = 0; i < keys.length; i++){
                // Last index of array should not put + sign at the end
                if(i != keys.length - 1){
                    url.append(keys[i] + "+");
                }else{
                    // Last index append the keyword itself
                    url.append(keys[i]);
                }
            }
            
            // 2nd part of the url
            url.append("&rh=i%3Aaps%2Ck%3A"); // default url address from amazon.ca
            
            // Same as previous print out
            for(int i = 0; i < keys.length; i++){
                if(i != keys.length - 1){
                    url.append(keys[i] + "+");
                }else{
                    url.append(keys[i]);
                }
            }
            
        }else{
            // Keyword without space
            url.append(item + "&rh=i%3Aaps%2Ck%3A" + item);
        }
    
        // return the string as a href
        return "<a href=" + url.toString()+ ">Get the price! </a>";
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
        processRequest(request, response);
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
        processRequest(request, response);
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
