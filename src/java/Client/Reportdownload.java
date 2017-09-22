/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.sf.jasperreports.engine.*;

import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Vatsal
 */
public class Reportdownload {
    
    public static void main(String[] args) throws IOException
    {
          try
        {
      
//Specify Your Report Path
JasperReport jasperReport=JasperCompileManager.compileReport("C:\\Users\\NaKs\\Desktop\\report generation\\Report\\TestReport.jrxml");
        
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio","root","");
Statement stmt = null;
ResultSet rset = null;
String queryString = "SELECT emp_name,salary from employee";

stmt = conn.createStatement();
rset = stmt.executeQuery(queryString);
 JRResultSetDataSource jasperReports = new JRResultSetDataSource(rset);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, jasperReports);
//Report saved in specified path
            JasperExportManager.exportReportToPdfFile(jasperPrint,"D:\\b1.pdf");
//Report open in Runtime
          Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " +"D:\\b1.pdf");
JRXlsExporter exporterXLS = new JRXlsExporter();
OutputStream output = new FileOutputStream(new File("D:\\b1.xls"));
 exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
 exporterXLS.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
 exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
 exporterXLS.exportReport();
            //JasperViewer.viewReport(jasperPrint);

        }
 catch (JRException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    
    
    
}
