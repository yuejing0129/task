package com.jing.system.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * 字符串工具类
 * @author jing.yue
 * @version 1.0
 * @since 2012-10-26
 */
public class StringUtil {
	
	//链接正则表达式
	private static final String REGEX_URL = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null)  = true</li>
	 * <li>SysUtils.isEmpty("")    = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArr(Object value, Object ...equalArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值[不区分大小写]
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualsIcArr(Object value, Object ...equalArr) {
		if (value == null || "".equalsIgnoreCase(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArrSin(Object value, Object[] equalArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否不等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isNotEqualArr(Object value, Object ...notEqualArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if(value.toString().equals(object.toString())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断一个字符串是否不等于一个数组中的一个值[不区分大小写]
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isNotEqualIcArr(Object value, Object ...notEqualArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if(value.toString().equalsIgnoreCase(object.toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串是否不为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null)  = false</li>
	 * <li>SysUtils.isEmpty("")    = false</li>
	 * <li>SysUtils.isEmpty("   ") = false</li>
	 * <li>SysUtils.isEmpty("abc") = true</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isNotEmpty(String value) {
		if (value != null && !"".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查对象是否为数字型字符串。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	/**
	 * 过滤不可见字符
	 */
	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		return join(array, separator, 0, array.length);
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return "";
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 字符串以指定字符分隔,然后随机获取一个字符串
	 * @param string
	 * @param split
	 * @return
	 */
	public static String getStrsRandomStr(String string, String split) {
		if(isEmpty(string)) {
			return "";
		}
		int len = string.length();
		if(split.equals(string.substring(len -1, len))) {
			string = string.substring(0, len - 1);
		}
		String[] arr = string.split(split);
		if(arr.length == 0) {
			return "";
		}
		int index = (int) (Math.random() * arr.length);
		if(index >= arr.length) {
			index = arr.length - 1;
		}
		return arr[index];
	}

	/**
	 * 去掉前后所有小于该char的字符，如最后字符为(char)1，而参数传为(char)2，同样(char)1会被删除。
	 * @param str
	 * @param ch
	 * @return
	 */
	public static String trim(String str, char ch) {
		int i = str.length();
		int j = 0;
		int k = 0;
		char[] arrayOfChar = str.toCharArray();
		while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
			++j;
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
			--i;
		return (((j > 0) || (i < str.length())) ? str.substring(j, i) : str);
	}

	/**
	 * 将字符串的json不能识别字符转为可识别字符
	 * @param string
	 * @return
	 */
	public static String cvtJsonIglChar(String string) {
		StringBuffer str = new StringBuffer();
		str.append(string.replaceAll("	", " "));
		return str.toString();
	}

	/**
	 * 将数组转换成用逗号分隔的字符串
	 * @param strs
	 * @return
	 */
	public static String formatComma(List<?> strs) {
		//删除集合中空节点
		strs.remove(null);
		String str = ArrayUtils.toString(strs);
		return str.substring(1, str.length() - 1);
	}

	/**
	 * 将数组转换成用逗号分隔的字符串
	 * @param strs
	 * @return
	 */
	public static String formatComma(Object[] strs) {
		//删除集合中空节点
		ArrayUtils.removeElement(strs, null);
		String str = ArrayUtils.toString(strs);
		return str.substring(1, str.length() - 1);
	}
	
	/**
	 * 将已指定分隔符的字符串转为List
	 * @param string	字符串
	 * @param regex		分隔符
	 * @return
	 */
	public static List<String> toArray(String string, String regex) {
		List<String> list = new ArrayList<String>();
		if(isEmpty(string)) {
			return list;
		}
		String[] arr = string.split(regex);
		for (String str : arr) {
			if(isEmpty(str)) {
				continue;
			}
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 将数字转为保留2位小数的形式
	 * @param value
	 * @return
	 */
	public static String numRound(Object value) {
		return new java.text.DecimalFormat("0.00").format(value);
	}

	/**
	 * 将指定内容四舍五入
	 * 		如2345.567会格式化为2,345.57
	 * @param value
	 * @return
	 */
	public static String numRoundFormat(Object value) {
		return numRoundFormat(value, null, 2);
	}
	/**
	 * 将指定内容四舍五入
	 * 		如2345.567会格式化为2,345.57
	 * @param value
	 * @param minimum
	 * @param maximum
	 * @return
	 */
	public static String numRoundFormat(Object value, Integer minimum, Integer maximum) {
		NumberFormat nf = NumberFormat.getInstance();
		//设置四舍五入
		nf.setRoundingMode(RoundingMode.HALF_UP);
		//设置最小保留几位小数
		if(minimum != null) {
			nf.setMinimumFractionDigits(minimum);
		}
		//设置最大保留几位小数
		if(maximum != null) {
			nf.setMaximumFractionDigits(maximum);
		}
		return nf.format(value);
	}
	
	/**
	 * 正则表达式校验字符串是否包含指定规则的字符串
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean isRegexContains(String str, String regex) {
		Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(str);
        return matcher.find();
    }

	/**
	 * 将汉字转为%E4%BD%A0 <br>
	 * 将http://www.jokeji.cn/jokehtml/冷笑话/201402280001021.htm中的冷笑话类的汉字转为http://www.jokeji.cn/jokehtml/%E5%86%B7%E7%AC%91%E8%AF%9D/201402280001021.htm
	 * @param string
	 * @return
	 */
	public static String urlEncode(String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将%E4%BD%A0转换为汉字
	 * @param s
	 * @return
	 */
	public static String urlUnescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int l = s.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			/* Get next byte b from URL segment s */
			switch (ch = s.charAt(i)) {
			case '%':
				ch = s.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = s.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			/* Decode byte b as UTF-8, sumb collects incomplete chars */
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)   
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb   
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf   
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)   
				sbuf.append((char) b); // Store in sbuf   
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)   
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte   
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)   
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes   
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)   
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes   
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)   
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes   
			} else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)   
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes   
			}
			/* We don't test if the UTF-8 encoding is well-formed */
		}
		return sbuf.toString();
	}
	
	/**
	 * 将字符串中的带有url链接转为可以点击的链接
	 * @param str
	 * @return
	 */
	public static String urlToClick(String str) {
        Pattern pattern = Pattern.compile(REGEX_URL);
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            StringBuffer replace = new StringBuffer();
            replace.append("<a href=\"").append(matcher.group());
            replace.append("\" target=\"_blank\">").append(matcher.group()).append("</a>");
            matcher.appendReplacement(result, replace.toString());
        }
        matcher.appendTail(result);
		return result.toString();
	}
	
	/**
	 * 判断字符串中是否包含http链接
	 * @param str
	 * @return
	 */
	public static boolean urlExist(String str) {
		return isRegexContains(str, REGEX_URL);
	}
	
	/**
	 * 获取字符串从0开始到指定位置结束的字符串
	 * @param str
	 * @param len
	 * @return
	 */
	public static String getLenStr(String str, Integer len) {
		if(str == null) {
			return null;
		}
		if(str.length() > len) {
			return str.substring(0, len) + "...";
		}
		return str;
	}

	/**
	 * 把html的标签特殊字符转换成普通字符
	 * @param str
	 * @return
	 */
	public static String htmlEscape(String str) {
		return HtmlUtils.htmlEscape(str);
	}
	/**
	 * 将普通字符串转换为html的标签字符串
	 * @param str
	 * @return
	 */
	public static String htmlUnescape(String str) {
		return HtmlUtils.htmlUnescape(str);
	}

	public static void main(String[] args) {
		System.out.println(htmlUnescape("&lt;a&gt;asdf&lt;/a&gt;"));
		String str = (char)1+"dasda"+(char)2+"sdasd"+(char)1+"asdasdas"+(char)1+(char)1+(char)1;
		System.out.println(str);
		System.out.println(trim(str, (char)3));
		System.out.println("isEmpty: " + isEmpty(null));
		System.out.println("isNotEmpty: " + isNotEmpty("s"));
		System.out.println("isEqualArr: " + isEqualArr(2, 20521, 20513, 10024));
		System.out.println("isNotEqualArr: " + isNotEqualArr(null, 1, 2, 3));
		System.out.println("getStrsRandomStr: " + getStrsRandomStr("哈哈;嗯嗯;", ";"));
		
		System.out.println(numRound(1.2));
		
		String urlStr = "这是一个url链接http://www-test.company.com/view/1_2.html?a=%B8&f=%E4+%D3#td http://www.suyunyou.com/aid15.html需要转化成可点击";
		System.out.println(urlToClick(urlStr));
		System.out.println("包含http链接：" + urlExist("爱，http://sdfdf"));
	}

}