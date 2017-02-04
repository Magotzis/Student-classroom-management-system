package com.magotzis.util;

/*
 * Created on 2005-7-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 *
 * 本类保存的是request.getParameter("VCGLDJH")的内容
 */
public class HTMLRequest {
	
	/*
	 * 初始化
	 * 其中，
	 * 自定义运算符包括：=、>、<、<>、<=、>=、isNull、isNotNull
	 * 			、%Like、Like%、%Like%。
	 */
	public String sParameterName;//名
	public String sParameterType;//类型
	public String sParameterWebName;//Web名
	public String sParameterValue;//值	
	public String sParameterOperator;//自定义运算符
	public String sParameterSQLOperator;//SQL的运算符
	
	/**
	 * 类的初始化函数
	 * @param request
	 * @param sName 名
	 * @param sValue 值
	 * @param sType 类型
	 * @param sOperator 自定义运算符
	 * @param bCheck 是否检查字符串
	 * @param sCheck 检查字符串
	 */
	public void HTMLRCreate(boolean isDecode,HttpServletRequest request
    					, String sName, String sValue
						, String sType, String sOperator
						, boolean bCheck, String sCheck)
    {
    	//根据运算符决定下一步操作
    	if((sOperator=="=")||(sOperator==">")||(sOperator=="<")
    			||(sOperator=="<>")||(sOperator=="<=")||(sOperator==">=")
				||(sOperator=="isNull")||(sOperator=="isNotNull"))
    	{
    		//合法
    	}else if((sOperator=="%Like")||(sOperator=="Like%")
				||(sOperator=="%Like%"))
    	{
    		//如果要用Like的，则类型必需是字符串
    		if(sType!="String")
    		{
    			sType = "String";
    		}
    	}else
    	{
    		//如果运算符非法，则用默认值
    		sOperator = "=";    		
    	}//根据运算符决定下一步操作
    	sParameterName     = sName;
    	sParameterType     = sType;

    	//2011-06-17修改
    	if(request==null){
    		//没有，则表示是新增参数。
        	sParameterWebName  = sName.trim();
        	sParameterValue    = sValue;
    	}else{//if(request==null){
        	sParameterWebName  = sValue.trim();
        	sParameterValue    = GetParameter(request, sValue , isDecode); 
    	}//if(request==null){

    	if(sParameterValue==null){
    		sParameterValue="";
    	}else{
    		try{
        		//sParameterValue = new String((request.getParameter(sValue)).getBytes("ISO8859_1"),"GBK");
    			//sParameterValue = java.net.URLDecoder.decode(request.getParameter(sValue));
    			//sParameterValue    = request.getParameter(sValue).toString();
    		}catch(Exception e)
    		{
    			////System.out.println(e.getMessage());
    		}
	
    	}
    	sParameterValue=sParameterValue.trim();

    	if(sType=="String"){
    		//如果是字符串，则判断是否进行检查
    		if(bCheck){
    			if(sCheck.equals("")){
    				sCheck=StringFunction.sCheckAll;
    			}
    			sParameterValue=StringFunction.CheckString(sParameterValue,sCheck);
    		}
    	}
    	sParameterOperator = sOperator;
    	//根据自定义运算符决定SQL的运算符
    	if((sParameterOperator=="=")||(sParameterOperator==">")
    			||(sParameterOperator=="<")||(sParameterOperator=="<>")
				||(sParameterOperator=="<=")||(sParameterOperator==">="))
    	{
    		sParameterSQLOperator   = sParameterOperator;
    	}else if(sParameterOperator=="isNull")
    	{
    		sParameterSQLOperator   = " is Null";
    	}else if(sParameterOperator=="isNotNull")
    	{
    		sParameterSQLOperator   = " is Not Null";
    	}else if((sParameterOperator=="%Like")
    			||(sParameterOperator=="Like%")
				||(sParameterOperator=="%Like%"))
    	{
    		sParameterSQLOperator   = " Like ";
    	}
    }

	//list.add(new HTMLRequest(request,"VCCZDJLX","CZDJLX"));
    public HTMLRequest(HttpServletRequest request
			,String sName,String sValue){
    
    	HTMLRCreate(false,request, sName, sValue, "String", "=", false, "");
    }
    
    public HTMLRequest(boolean isDecode,HttpServletRequest request
			,String sName,String sValue){
    
    	HTMLRCreate(isDecode,request, sName, sValue, "String", "=", false, "");
    }

    //list.add(new HTMLRequest(request,"VCSBTXM","SBTXM",true));
    public HTMLRequest(HttpServletRequest request
			,String sName,String sValue,boolean bCheck)
    {
    	HTMLRCreate(false,request, sName, sValue, "String", "=", bCheck, "");
    }
    
    public HTMLRequest(boolean isDecode,HttpServletRequest request
			,String sName,String sValue,boolean bCheck)
    {
    	HTMLRCreate(isDecode,request, sName, sValue, "String", "=", bCheck, "");
    }

    //list.add(new HTMLRequest(request,"VCCZDJH","CZDJH","Like%"));
    public HTMLRequest(HttpServletRequest request
			, String sName, String sValue
			, String sOperator)
    {
    	HTMLRCreate(false,request, sName.trim(), sValue.trim(), "String", sOperator, false, "");
    }
    
    public HTMLRequest(boolean isDecode,HttpServletRequest request
			, String sName, String sValue
			, String sOperator)
    {
    	HTMLRCreate(isDecode,request, sName.trim(), sValue.trim(), "String", sOperator, false, "");
    }

    //list.add(new HTMLRequest(request,"VCGLDJH","GLDJH","Like%",true));
    public HTMLRequest(HttpServletRequest request
			, String sName, String sValue
			, String sOperator,boolean bCheck)
    {
    	HTMLRCreate(false,request, sName, sValue, "String", sOperator, bCheck, "");
    }
    
    public HTMLRequest(boolean isDecode,HttpServletRequest request
			, String sName, String sValue
			, String sOperator,boolean bCheck)
    {
    	HTMLRCreate(isDecode,request, sName, sValue, "String", sOperator, bCheck, "");
    }

    //list.add(new HTMLRequest(request,"VCCZDJH","CZDJH","int",">"));
    public HTMLRequest(HttpServletRequest request
			, String sName, String sValue
			, String sType, String sOperator)
    {
    	HTMLRCreate(false,request, sName, sValue, sType, sOperator, false, "");
    }
    
    public HTMLRequest(boolean isDecode,HttpServletRequest request
			, String sName, String sValue
			, String sType, String sOperator)
    {
    	HTMLRCreate(isDecode,request, sName, sValue, sType, sOperator, false, "");
    }

    /*
     * 创建SQL语句中，select的where部分，或者update的set部分。
     * 参数说明：
     * list保存HTMLRequest格式的类；
     * sDep每个结果之间的分隔符号；
     * OmitSpace忽略“”的情况。
     * 例如：
     * list.add(new HTMLRequest(request,"VCCZYY","CZYY"));
     * list.add(new HTMLRequest(request,"VCCZDJZT","CZDJZT"));
     * String strtempsql=HTMLRequest.CreateSQL(list," and ",true);
     */
	public static String CreateSQL(List list,String sDep,boolean OmitSpace)
	{
		String sOut=""; 
		for(int i=0;i<list.size();i++)
		{
			HTMLRequest HTMLR=(HTMLRequest)list.get(i);
			if(HTMLR.sParameterValue!=null)
			{
				if((!OmitSpace)||(!HTMLR.sParameterValue.equals("")))
				{
					//不忽略空或者内容非空
					if(sOut!="")
					{
						sOut = sOut + sDep; 
					}
					String MyDep="";
					if(HTMLR.sParameterType=="String")
					{
						MyDep="'";
					}									
					//根据不同的自定义运算符，有不同的操作
			    	if((HTMLR.sParameterOperator=="=")
			    			||(HTMLR.sParameterOperator==">")
			    			||(HTMLR.sParameterOperator=="<")
							||(HTMLR.sParameterOperator=="<>")
							||(HTMLR.sParameterOperator=="<=")
							||(HTMLR.sParameterOperator==">="))
			    	{
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator
								+MyDep+HTMLR.sParameterValue+MyDep;									
			    	}else if(HTMLR.sParameterOperator=="isNull")
			    	{
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator;
			    	}else if(HTMLR.sParameterOperator=="isNotNull")
			    	{
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator;
			    	}else if(HTMLR.sParameterOperator=="%Like")
			    	{
			    		//左边模糊
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator
								+MyDep+"%"+HTMLR.sParameterValue+MyDep;									
					}else if(HTMLR.sParameterOperator=="Like%")
					{
			    		//右边模糊
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator
								+MyDep+HTMLR.sParameterValue+"%"+MyDep;									
					}else if(HTMLR.sParameterOperator=="%Like%")
			    	{
			    		//两边都模糊
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator
								+MyDep+"%"+HTMLR.sParameterValue+"%"+MyDep;									
			    	}
				}else//if((!OmitSpace)||(!HTMLR.sParameterValue.equals("")))
				{
					//如果运算符是isNull或者isNotNull则允许值是""
					if((HTMLR.sParameterOperator=="isNull")
							||(HTMLR.sParameterOperator=="isNotNull"))
					{
						if(sOut!="")
						{
							sOut = sOut + sDep; 
						}
						sOut = sOut+HTMLR.sParameterName
								+HTMLR.sParameterSQLOperator;														
					}
				}//if((!OmitSpace)||(!HTMLR.sParameterValue.equals("")))
			}else//if(HTMLR.sParameterValue!=null)
			{
				//如果运算符是isNull或者isNotNull则允许值是null
				if((HTMLR.sParameterOperator=="isNull")
						||(HTMLR.sParameterOperator=="isNotNull"))
				{
					if(sOut!="")
					{
						sOut = sOut + sDep; 
					}
					sOut = sOut+HTMLR.sParameterName
							+HTMLR.sParameterSQLOperator;														
				}
			}//if(HTMLR.sParameterValue!=null)
			
		}//for(int i=0;i<list.size();i++)
		//System.out.println("CreateSQL: "+sOut);
		return sOut;
	}
	
     /*
     * 返回查询参数
     * 参数说明：
     * list保存HTMLRequest格式的类；
     * 例如：
     * list.add(new HTMLRequest(request,"VCCZYY","CZYY"));
     */
	public static List CreateBackParamete(List list)
	{
		List outlist= new ArrayList();
		HashMap map = null;
		try
		{
			map = new HashMap();
			for(int i=0;i<list.size();i++)
			{
				HTMLRequest HTMLR=(HTMLRequest)list.get(i);
				//outlist.add(HTMLR.sParameterName+"="+HTMLR.sParameterValue);
				String name=HTMLR.sParameterWebName;
				String value=HTMLR.sParameterValue;
				if(value==null || value.equals(null))
				{
					value="";
				}
				//System.out.println(name+":"+value);
				map.put(name,value);
			}
			outlist.add(map);
		}
		catch(Exception e)
		{
			//System.out.println(e.toString());
		}
		//System.out.println(outlist);
		return outlist;
	}
	
	
	
    /*
    * 返回查询参数
    * 参数说明：
    * list保存HTMLRequest格式的类；
    * 例如：
    * list.add(new HTMLRequest(request,"VCCZYY","CZYY"));
    */
	public static String CreateBackParameteStr(List list)
	{
		String outstr="";
		try
		{
			for(int i=0;i<list.size();i++)
			{
				HTMLRequest HTMLR=(HTMLRequest)list.get(i);
				//outlist.add(HTMLR.sParameterName+"="+HTMLR.sParameterValue);
				String name=HTMLR.sParameterWebName;
				String value=HTMLR.sParameterValue;
				
				////System.out.println("查询条件name："+name);
				////System.out.println("查询条件value："+value);
				
				if(value!=null || !value.equals(null))
				{
					outstr+=name+"="+value;
					if(i<list.size()-1)
					{
						outstr+="&";
					}
				}
			}
			
		}
		catch(Exception e)
		{
			//System.out.println(e.toString());
		}
		//System.out.println(outstr);
		return outstr;
	}
	
	public static String GetParameter(HttpServletRequest request, String sValue){
		String sOut="";
		if((sValue!=null)&&(!sValue.equals(""))){
			sOut  = request.getParameter(sValue);
	    	if(sOut==null){
	    		sOut=""; 
	    	}
		}
		return sOut;
	}
	
	public static String GetRequestAttribute(HttpServletRequest request, String sValue){
		String sOut="";
		if((sValue!=null)&&(!sValue.equals(""))){
			sOut  = request.getAttribute(sValue)==null ? "" : (String)request.getAttribute(sValue);
		}
		return sOut;
	}
	
	public static String GetSessionAttribute(HttpServletRequest request, String sValue){
		String sOut="";
		if((sValue!=null)&&(!sValue.equals(""))){
			sOut  = request.getSession().getAttribute(sValue)==null ? "" : (String)request.getSession().getAttribute(sValue);
		}
		return sOut;
	}
	
	public static String GetSessionAttribute(HttpServletRequest request, String sValue,boolean encode ){
		String sOut="";
		if((sValue!=null)&&(!sValue.equals(""))){
			sOut  = request.getSession().getAttribute(sValue)==null ? "" : (String)request.getSession().getAttribute(sValue);
			if(encode){
				try {
					sOut = URLEncoder.encode(sOut, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return sOut;
	}
	
	/**
	 * 获得请求参数值
	 * @param request
	 * @param sValue 值名
	 * @param isDecode 是否需要解码
	 * @return
	 */
	public static String GetParameter(HttpServletRequest request, String sValue,boolean isDecode){
		String sOut="";
		if((sValue!=null)&&(!sValue.equals(""))){
			sOut  = request.getParameter(sValue);
	    	if(sOut==null){
	    		sOut=""; 
	    	}else{
	    		if(isDecode){
	    			try {
						sOut = URLDecoder.decode(sOut,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
	    		}
	    	}
		}
		return sOut;
	}
	
//	变量数组
	public static String GetParameters(HttpServletRequest request, String sValue, String sOmit){
		String sOut="";
		if((sValue!=null)&&(!sValue.equals(""))){
			String pValue[] = request.getParameterValues(sValue);
	    	if(pValue!=null){
	    		//数组非空，可以继续。
	    		sOmit = ","+sOmit+",";
	    		for(int i=0; i<pValue.length;i++){
	    			if(!sOmit.contains(","+pValue[i]+",")){
	    				//不包含忽略的数据
		    			if(!sOut.equals("")){
		    				sOut = sOut + ",";
		    			}
		    			sOut = sOut + pValue[i];
	    			}
	    		}
	    	}//if(pValue!=null){
		}
		return sOut;
	}
	
	//变量数组
	public static String GetParameters(HttpServletRequest request, String sValue){
		String sOut="";
		String sOmit = "abc";
		if((sValue!=null)&&(!sValue.equals(""))){
			String pValue[] = request.getParameterValues(sValue);
	    	if(pValue!=null){
	    		//数组非空，可以继续。
	    		sOmit = ","+sOmit+",";
	    		sOut += "'";
	    		for(int i=0; i<pValue.length;i++){
	    			if(!sOmit.contains(","+pValue[i]+",")){
		    			sOut = sOut + pValue[i];
		    			if(i != pValue.length-1){
		    				sOut = sOut + "','";
		    			}
	    			}
	    		}
	    		sOut += "'";
	    	}//if(pValue!=null){
		}
		return sOut;
	}
	
	
}


