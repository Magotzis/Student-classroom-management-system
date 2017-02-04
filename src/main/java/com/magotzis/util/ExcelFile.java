package com.magotzis.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 读写Excel文件工具类
 * 
 */
public class ExcelFile {
	private String Title;
	private List<String> Header;
	private List<List<String>> Data;

	private String[] Titles;
	private List<List<String>> Headers;
	private List<List<List<String>>> Datas;
	private int Amount;

	private WritableWorkbook workbook;
	private WritableFont headerFont;
	private WritableCellFormat headerFormat;

	{
		// 创建表头样式
		headerFont = new WritableFont(WritableFont.createFont("宋体"), 13,
				WritableFont.BOLD);
		headerFormat = new WritableCellFormat(headerFont);
		try {
			headerFormat.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}

	} // init block

	public ExcelFile() {
	};

	public ExcelFile(String title, List<String> header, List<List<String>> data) {
		super();
		Title = title;
		Header = header;
		Data = data;
	}

	public ExcelFile(String[] title, List<List<String>> header,
			List<List<List<String>>> data, int amount) {
		super();
		Titles = title;
		Headers = header;
		Datas = data;
		Amount = amount;
	}

	public boolean save(HttpServletRequest request,
			HttpServletResponse response, String excelName) throws IOException {

		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment;  filename="
				+ URLEncoder.encode((excelName + ".xls"), "utf-8"));
		response.setHeader("Pragma", "No-cache");// 设置头
		response.setHeader("Cache-Control", "Private");// 设置头
		response.setDateHeader("Expires", 0);// 设置日期头
		/*
		 * response.setHeader("Content-disposition", "attachment; filename=" +
		 * new String(excelName.getBytes("GB2312"), "8859_1") + ".xls");//
		 * 设定输出文件头
		 */
		response.setContentType("application/msexcel");// 定义输出类型
		workbook = Workbook.createWorkbook(os); // 建立excel文件

		try {
			crateWsheet(Title, Header, Data);
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean saveMultisheet(HttpServletRequest request,
			HttpServletResponse response, String excelName) throws IOException {

		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(excelName.getBytes("GB2312"), "8859_1") + ".xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		workbook = Workbook.createWorkbook(os); // 建立excel文件
		try {
			for (int i = 0; i < Amount; i++) {
				crateWsheet(Titles[i], Headers.get(i), Datas.get(i));
			}
			workbook.write();
			workbook.close();
			os.close();
			response.flushBuffer();
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean crateWsheet(String title, List<String> header,
			List<List<String>> data) {
		WritableSheet wsheet = workbook.createSheet(title, 100);
		// 设置工作表全局设定
		SheetSettings sheetSettings = wsheet.getSettings();
		sheetSettings.setFitToPages(true);
		sheetSettings.setPrintHeaders(false);
		sheetSettings.setDisplayZeroValues(true);
		sheetSettings.setPrintGridLines(true);
		sheetSettings.setFitWidth(1);
		try {
			// 写入表头
			for (int i = 0; i < header.size(); ++i) {
				wsheet.setColumnView(i, 25);// 根据内容自动设置列宽
				Label nc = new Label(i, 0, header.get(i), headerFormat);
				wsheet.addCell(nc);
			}
			// 加入数据
			for (int i = 0; i < data.size(); ++i) {
				for (int j = 0; j < data.get(i).size(); ++j) {
					wsheet.setColumnView(j, 25);// 根据内容自动设置列宽
					Label nc = new Label(j, i + 1, data.get(i).get(j));
					wsheet.addCell(nc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @return the header
	 */
	public List<String> getHeader() {
		return Header;
	}

	/**
	 * @return the data
	 */
	public List<List<String>> getData() {
		return Data;
	}

	/**
	 * @return the workbook
	 */
	public WritableWorkbook getWorkbook() {
		return workbook;
	}

	/**
	 * @return the headerFont
	 */
	public WritableFont getHeaderFont() {
		return headerFont;
	}

	/**
	 * @return the headerFormat
	 */
	public WritableCellFormat getHeaderFormat() {
		return headerFormat;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(List<String> header) {
		Header = header;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<List<String>> data) {
		Data = data;
	}

	/**
	 * @param workbook
	 *            the workbook to set
	 */
	public void setWorkbook(WritableWorkbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * @param headerFont
	 *            the headerFont to set
	 */
	public void setHeaderFont(WritableFont headerFont) {
		this.headerFont = headerFont;
	}

	/**
	 * @param headerFormat
	 *            the headerFormat to set
	 */
	public void setHeaderFormat(WritableCellFormat headerFormat) {
		this.headerFormat = headerFormat;
	}

	public String[] getTitles() {
		return Titles;
	}

	public void setTitles(String[] titles) {
		Titles = titles;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public List<List<String>> getHeaders() {
		return Headers;
	}

	public void setHeaders(List<List<String>> headers) {
		Headers = headers;
	}

	public List<List<List<String>>> getDatas() {
		return Datas;
	}

	public void setDatas(List<List<List<String>>> datas) {
		Datas = datas;
	}

}