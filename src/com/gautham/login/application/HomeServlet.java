package com.gautham.login.application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/homeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String file;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet()");

		file = request.getParameter("outputfile");
		String str = null;

		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html");
		
		
		if (file != null && file != "") {

			String text = request.getParameter("TextValue");
			str = text;

			// Create blank workbook
			XSSFWorkbook workbook = new XSSFWorkbook();

			// Create a blank spreadsheet
			XSSFSheet spreadsheet = workbook.createSheet("Convert");

			// Create row object
			XSSFRow row;

			// This data needs to be written (Object[]) to Map

			String[] s = str.split("\n");
			Map<String, Object[]> info = new TreeMap<String, Object[]>();

			try {
			for (int i = 1; i <= s.length; i++) {
				String[] val = s[i - 1].split("[\\|]+");
				info.put(Integer.toString(i), new Object[] {val[0], val[1], val[2]});
				System.out.println(val[0] + val[1] + val[2]);
			}
			}catch(ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.html");
				requestDispatcher.forward(request, response);
			}
	

			// Iterate over data and write to sheet
			Set<String> keyid = info.keySet();
			int rowid = 0;

			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++); // Create Row
				Object[] objectArr = info.get(key);
				int cellid = 0;

				for (Object obj : objectArr) {
					Cell cell = row.createCell(cellid++); // Create Cell
					cell.setCellValue((String) obj);
				}
			}

			// Write the workbook in file system
			String f = file + "/newExcel.xlsx";
			f = f.replaceAll("\\s", "");
			FileOutputStream out = null;
			try {
				// out =new FileOutputStream("ConvertedFile.xlsx");

				out = new FileOutputStream(f);
				workbook.write(out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				String msg = "Provided path is invalid!";
				printWriter.println("<script>alert(\"" + msg + "\")</script>");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.html");
				requestDispatcher.include(request, response);
			} catch (IOException e) {
				e.printStackTrace();
				String msg = "Provided path is invalid!";
				printWriter.println("<script>alert(\"" + msg + "\")</script>");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.html");
				requestDispatcher.include(request, response);
			} finally {
				try {
					out.close();
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			String msg = "Text is successfully written to " + f;
			printWriter.println("<script>alert(\"" + msg + "\")</script>");
		} else {
			String msg = "Provided path is invalid!";
			printWriter.println("<script>alert(\"" + msg + "\")</script>");
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.html");
		requestDispatcher.include(request, response);

	}

	// "C:\\Users/gauth/Desktop/Demo.txt"

//		XSSFWorkbook workbook = new XSSFWorkbook();
//		
//		// Create a blank spreadsheet
//		XSSFSheet spreadsheet = workbook.createSheet("Convert.xlsx");
//
//		// Create row object
//		XSSFRow row;
//
//		// This data needs to be written (Object[]) to Map
//		String[] s = str.split("\n");
//		Map<String, Object[]> info = new TreeMap<String, Object[]>();
//
//		for (int i = 1; i <= s.length; i++) {
//			String[] val = s[i - 1].split("[\\|]+");
//			info.put(Integer.toString(i), new Object[] { val[0], val[1], val[2] });
//			System.out.println(val[0] + val[1] + val[2]);
//		}
//
//		// Iterate over data and write to sheet
//		Set<String> keyid = info.keySet();
//		int rowid = 0;
//
//		for (String key : keyid) {
//			row = spreadsheet.createRow(rowid++); // Create Row
//			Object[] objectArr = info.get(key);
//			int cellid = 0;
//
//			for (Object obj : objectArr) {
//				Cell cell = row.createCell(cellid++); // Create Cell
//				cell.setCellValue((String) obj);
//			}
//		}
//
//		// Write the workbook in file system
//		FileOutputStream out = null;
//		try {
//			response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename = Convert.xlsx");
//            workbook.write(response.getOutputStream());
//            workbook.close();
//           // workbook.dispose();
//			
//            String test = "Successfully Written to the Excel Sheet";
//    		  out1.println("<script>alert(\"" +test+ "\")</script>");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				out.close();
//				workbook.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("download.html");
//		//requestDispatcher.include(request, response);
//		}
//		else {
//			response.setContentType("text/html");
//			String msg = "The uploaded file is empty";
//			//out1.println("<script>alert(\"" +msg+ "\")</script>");
//		}

}
