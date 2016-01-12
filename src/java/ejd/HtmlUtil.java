///////////////////////////////////////////////////////////////////////////////
// HtmlUtil.java
// =============
// collection of utility methods to print HTML content
///////////////////////////////////////////////////////////////////////////////

package ejd;

import java.io.PrintWriter;

public final class HtmlUtil
{
    // ctor, prevent from instantiate this class
    private HtmlUtil()
    {
    }



    ///////////////////////////////////////////////////////////////////////////
    // print header part of html page: <html><head>...</head><body>
    ///////////////////////////////////////////////////////////////////////////
 
    public static void printHeader(PrintWriter out, String title)
    {
        String header = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "<meta charset=\"utf-8\">\n" +
                        "<title>" +
                        title +
                        "</title>\n" +
                        "<link type='txt/css' rel='stylesheet' href='screen.css'/>" +
                        "</head>\n" +
                        "<body>\n";
        out.println(header);
    }



    ///////////////////////////////////////////////////////////////////////////
    // print bottom part of HTML page: </body></html>
    ///////////////////////////////////////////////////////////////////////////
    public static void printFooter(PrintWriter out)
    {
        out.println("</body></html>");
    }
}
