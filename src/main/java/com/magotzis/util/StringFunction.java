package com.magotzis.util;

/*
 * 创建日期 2005-6-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
import java.util.*;

/**
 * @author php
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class StringFunction {

	/**
	 * 合并字符串数组
	 * @param original 需要合并的字符串数组
	 * @param regex 连接字符串
	 * @param OmitSpace 忽略空字符串
	 * @return 合并后生成的字符串
	 */
	public static String unite(String[] original,String regex
			,boolean OmitSpace)
	{
		String str="";
		for(int i=0;i<original.length;i++){
			if(original[i]!=null){
				//System.out.println(original[i]);
				if((original[i].equals(""))&&OmitSpace){
					//应该忽略的
				}else{
					//工作
					if(!str.equals("")){
						str = str + regex;
					}
					str = str + original[i];
				}
			}
		}//for(int i=0;i<original.length;i++){
		//System.out.println(str);
		//返回生成的字符串
		return str;
	}
	
	/**
	  * 分割字符串，原理：检测字符串中的分割字符串，然后取子串
	  * @param original 需要分割的字符串
	  * @param regex    分割字符串
	  * @param OmitSpace    忽略空字符串
	  * @param United    合并重复的
	  * @return 分割后生成的字符串数组
	  */
	public static String[] split(String original,String regex
					,boolean OmitSpace,boolean United)
	 {

	  //取子串的起始位置
	  int startIndex = 0;

	  //标志
	  boolean bOK=false;
	  
	  //将结果数据先放入Vector中
	  Vector v = new Vector();

	  //返回的结果字符串数组
	  String[] str = null;

	  //存储取子串时起始位置
	  int index = 0;

	  if(original!=null)
	  {
		  //获得匹配子串的位置
		  startIndex = original.indexOf(regex); 
		  //System.out.println(index + " - " + startIndex); 

		  //如果起始字符串的位置小于字符串的长度，则证明没有取到字符串末尾。
		  //if ((startIndex == -1)&&(!original.equals("")))
		  {
		  	//表示根本没有regex
		  	//v.addElement(original);
		  }
		  //-1代表取到了末尾

		  while(startIndex <= original.length() && startIndex != -1)
		  {
		  	String temp = original.substring(index,startIndex);
		  	//System.out.println("<"+temp+">");
		  	bOK=true;
		  	//取子串
		  	if(OmitSpace)
		  	{
		  		//忽略空的，开头和结尾是空的也要忽略。
		  		temp = temp.trim();
		  		if(!temp.equals(""))
		  		{
		  			if(United){
				  		  for(int i=0;i<v.size();i++)
				  		  {
				  		  	if(temp.equals((String)v.elementAt(i))){
				  		  		//如果和已经存在的相等，则要忽略
				  		  		bOK=false;
				  		  		break;//可以退出了。
				  		  	}
				  		  }		  				
		  			}
		  		}else{
		  			bOK=false;
		  		}
		  	}

		  	if(bOK){
		  		//可以加入
			  	v.addElement(temp);
		  	}
		  	
		  	//设置取子串的起始位置
		  	index = startIndex + regex.length();

		  	//获得匹配子串的位置
		  	startIndex = original.indexOf(regex,index);

		  }
	 
		  if ((index>=0)&&(index<original.length()))
		  {
			  String temp = original.substring(index);	
			  bOK=true;
			  if(OmitSpace)
			  	{
			  		//忽略空的，开头和结尾是空的也要忽略。
			  		temp = temp.trim();
			  		if(!temp.equals(""))
			  		{
			  			if(United){
					  		  for(int i=0;i<v.size();i++)
					  		  {
					  		  	if(temp.equals((String)v.elementAt(i))){
					  		  		//如果和已经存在的相等，则要忽略
					  		  		bOK=false;
					  		  		break;//可以退出了。
					  		  	}
					  		  }		  				
			  			}
			  		}else{
			  			bOK=false;
			  		}
			  	}
			  if(bOK){
			  		//可以加入
				  	v.addElement(temp);
			  	}
		  }	  	
	  }else
	  {
	  	v.addElement(null);
	  }
	  
	  //将Vector对象转换成数组
	  str = new String[v.size()];

	  for(int i=0;i<v.size();i++)
	  {
	   str[i] = (String)v.elementAt(i);
	  }

	  //返回生成的数组
	  return str;

	 }

	public static String[] split(String original,String regex,boolean OmitSpace)
	{
		return split(original, regex, OmitSpace, false);
	}
	
	public static String[] split(String original,String regex)
	 {
		return split(original,regex,false);
	 }

	/**
	  * 格式化字符串
	  * @param original 需要格式化的字符串
	  * @param sBy 填充字符
	  * @param length 结果长度
	  * @return 格式化后生成的字符串
	  */
	public static String format(String original,String sBy,int length)
	{
		//不可以在输入参数的基础上进行修改，并且作为输出。
		String sby=sBy+"00";
		sby=sby.substring(0,1);//取第一位
		String sOr=original;
		//System.out.println(length+" - "+original.length());
		for(int i=0;i<(length-original.length());i++)
		{
			sOr = sby + sOr;
		}
		if(sOr.length()>length)
		{
			sOr=sOr.substring(0,length);
		}
		return sOr;
	}
		
	/**
	 * 用补0的方式格式化字符串
	 * @param original
	 * @param length
	 * @return
	 */
	public static String format(String original,int length)
	{
		return format(original,"0",length);
	}
	
	/**
	 * 从左到右得到字符串sOri的sDep左边部分
	 * @param sOri 源字符串
	 * @param sDep 分隔用字符串
	 * @return 左边部分
	 */
	public static String l2rls(String sOri, String sDep)
	{
		String sTemp=null;
		if(sOri!=null)
		{
			if(sDep!=null)
			{
				int Index = sOri.indexOf(sDep);
				if(Index>0)
				{
					sTemp = sOri.substring(0,Index);
				}else
				{
					//在sOri中找不到sDep，返回整个sOri
					sTemp = sOri;
				}
			}else
			{
				sTemp = sOri;
			}
		}
		//System.out.println("在"+sOri+"中"+sDep+"左边的是"+sTemp);
		return sTemp;
	}

	/**
	 * 从左到右得到字符串sOri的sDep右边部分
	 * @param sOri 源字符串
	 * @param sDep 分隔用字符串
	 * @return 右边部分
	 */
	public static String l2rrs(String sOri, String sDep)
	{
		String sTemp=null;
		if(sOri!=null)
		{
			if(sDep!=null)
			{
				int Index = sOri.indexOf(sDep);
				if(Index>=0)
				{
					Index = Index+sDep.length();//真正的开始
					if(Index<sOri.length())
					{
						sTemp = sOri.substring(Index);
					}else
					{
						//这个分隔已经是最后了。
						sTemp = "";
					}
				}else
				{
					//在sOri中找不到sDep，返回""
					sTemp = "";
				}
			}else
			{
				sTemp = "";
			}
		}
		//System.out.println("在"+sOri+"中"+sDep+"右边的是"+sTemp);
		return sTemp;
	}

	public static String ReplaceAll(String sOri, String regex, String replacement){
		String strtemp=sOri;
		//字符串替换操作
		if(regex != null)
		{
			if(!regex.equals(""))
			{
				//需要进行字符串替换，把字段中特殊的字符替换为分隔字符串
				//System.out.println("ReplaceAll:" + strtemp);
				strtemp=strtemp.replaceAll(regex,replacement);
				//如果开头或者结尾有sDep，则要忽略。
				//System.out.println("ReplaceAll:" + strtemp);
				while(strtemp.indexOf(replacement)==0){//第一个字符是0
					//开头就是了。
					if(strtemp.length()>replacement.length()){
						strtemp=strtemp.substring(replacement.length());									
					}
				}
				while((strtemp.lastIndexOf(replacement)>=0)&&
						(strtemp.lastIndexOf(replacement)==(strtemp.length()-replacement.length()))){
					//最后还是。
					//System.out.println("ReplaceAll:" + strtemp.lastIndexOf(replacement));
					if(strtemp.length()>replacement.length()){
						strtemp=strtemp.substring(0,strtemp.length()-replacement.length());
					}
				}
				//System.out.println("ReplaceAll:" + strtemp);
			}
		}
		return strtemp;
	}
	
	public static String sCheckAll="U' ";//检查所有的常量
	
	/**
	 * 格式化字符串
	 * @param sOri 源字符串
	 * @param sOpt 操作
	 * @return 输出格式化后的字符串
	 * stryggh = StringFunction.CheckString(stryggh, StringFunction.sCheckAll);
	 */
	public static String CheckString(String sOri, String sOpt){
		String strTemp=sOri;
		String strOpt=sOpt.toUpperCase()+"PHP";
		String strperOpt="";
		if((strTemp!=null)&&(!strTemp.equals(null))){
			for(int i=0;i<strOpt.length()-3;i++){
				//每一位的操作都有不同的含义
				strperOpt=strOpt.substring(i,i+1);
				if(strperOpt.equals("U")){//大写
					strTemp = strTemp.toUpperCase();
				}else if(strperOpt.equals("L")){//小写
					strTemp = strTemp.toLowerCase();
				}else if(strperOpt.equals("'")){//过滤非法字符"'"
					strTemp=strTemp.replaceAll("'","");
				}else if(strperOpt.equals(" ")){//过滤非法字符" "
					//一般情况下，如果过滤了" "的话，那些特殊的SQL字符就没有必要再过滤了。
					strTemp=strTemp.replaceAll(" ","");
				}
			}			
		}
		//返回结果
		return strTemp;
	}
	
	/**
	 * 默认的检查函数
	 * @param sOri 源字符串
	 * @return  输出格式化后的字符串
	 */
	public static String CheckString(String sOri){
		return  CheckString(sOri, sCheckAll);
	}
	
	/**
	 * JavaScrpit中的字符串格式化
	 * @param sOri 源字符串
	 * @return 处理完的字符串
	 */
	public static String JSReplace(String sOri){
		if(sOri == null){
			sOri = "";
		}
		String sOut="";
		sOut = sOri.replaceAll("'","&acute;");
		sOut = sOri.replaceAll("\"","&quot;");
		sOut = sOut.replaceAll("\r","");
		sOut = sOut.replaceAll("\n","");
		return sOut;
	}
	
}

