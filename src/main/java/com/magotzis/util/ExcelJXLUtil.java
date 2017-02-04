package com.magotzis.util;

import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WriteException;

/**
 * 导出excel jxl 工具类
 * @author kevin
 *
 */
public class ExcelJXLUtil {
	
	/**
	 * 创建 cellform
	 * @param c 背景颜色
	 * @return
	 * @throws WriteException
	 */
	public static jxl.write.WritableCellFormat createwcfF(Colour c) throws WriteException{
		jxl.write.WritableCellFormat wcfF= null;
		jxl.write.WritableFont wf=null;
		wf = new jxl.write.WritableFont(WritableFont.TIMES, 12,
				 WritableFont.BOLD, false); //设置字体并加粗
		wcfF = new jxl.write.WritableCellFormat(wf);
		wcfF.setAlignment(jxl.format.Alignment.CENTRE); //水平居中
		wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
		wcfF.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,c);
		
		
		return wcfF;
	}
	
	public static jxl.write.WritableCellFormat createwcfF() throws WriteException{
		jxl.write.WritableCellFormat wcfF= null;
		jxl.write.WritableFont wf=null;
		wf = new jxl.write.WritableFont(WritableFont.TIMES, 12,
				 WritableFont.BOLD, false); //设置字体并加粗
		wcfF = new jxl.write.WritableCellFormat(wf);
		wcfF.setAlignment(jxl.format.Alignment.CENTRE); //水平居中
		wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
		wcfF.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK);
		
		
		return wcfF;
	}
	
	public static jxl.write.WritableCellFormat createNumberwcfF() throws WriteException{
		jxl.write.WritableCellFormat wcfF= null;
		jxl.write.WritableFont wf=null;
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat("0");
		wf = new jxl.write.WritableFont(WritableFont.TIMES, 12,
				 WritableFont.BOLD, false); //设置字体并加粗
		wcfF = new jxl.write.WritableCellFormat(wf,nf);
		wcfF.setAlignment(jxl.format.Alignment.CENTRE); //水平居中
		wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
		wcfF.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK);
		return wcfF;
	}
	
}
