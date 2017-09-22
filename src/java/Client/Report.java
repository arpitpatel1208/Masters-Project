/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.swing.JFileChooser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Vatsal
 */
public class Report {

    public static void main(String[] args) throws IOException {
        JFileChooser fc = new JFileChooser();
        File file = null;
        String downloadloaction;
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = fc.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            System.out.println(file.getAbsolutePath());
        } else {
            System.out.println("Failed to open directory");

        }
        try {

//Specify Your Report Path


            JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Users\\Desktop\\report generation\\Report\\TestReport.jrxml");

            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio", "root", "");
            Statement stmt;
            ResultSet rset;
            String queryString = "SELECT emp_name,salary from employee";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(queryString);
            JRResultSetDataSource jasperReports = new JRResultSetDataSource(rset);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jasperReports);
            downloadloaction = file.getAbsolutePath() + "report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, downloadloaction);
//Report open in Runtime
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + downloadloaction);
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
            exporterXLS.exportReport();
            //JasperViewer.viewReport(jasperPrint);

        } catch (JRException e) {
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }
}
