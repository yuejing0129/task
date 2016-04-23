package com.jing.system.utils;

/**
 * 匹配字符类
 * @author jing.yue
 * @version 1.0
 * @since 2012-10-15
 *
 */
public class MatcheUtil {

	private static int min(int once, int second, int third) {
		int min = once;
		if(second < min) {
			min = second;
		}
		if(third < min) {
			min = third;
		}
		return min;
	}

	/**
	 * 匹配字符串时编辑的次数
	 * @param current	匹配的字符串
	 * @param original	被匹配的字符串
	 * @return
	 */
	public static int matcheLd(String current, String original) {
	    //矩阵
		int d[][];
		int n = current.length();
		int m = original.length();
	    //遍历str1的
		int i;
	    //遍历str2的
		int j;
	    //str1的
		char ch1;
	    //str2的
		char ch2;
	    //记录相同字符,在某个矩阵位置值的增量,不是0就是1
		int temp;
		if(n == 0) {
			return m;
		}
		if(m == 0) {
			return n;
		}
		d = new int[n+1][m+1];
	    //初始化第一列
		for(i=0; i<=n; i++) {
			d[i][0] = i;
		}
	    //初始化第一行
		for(j=0; j<=m; j++) {
			d[0][j] = j;
		}
	    //遍历str1
		for(i=1; i<=n; i++) {
			ch1 = current.charAt(i-1);
			//去匹配str2
			for(j=1; j<=m; j++) {
				ch2 = original.charAt(j-1);
				if(ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				//左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1]+temp);
			}
		}
		return d[n][m];
	}

	/**
	 * 匹配字符串相似度
	 * @param current	匹配的字符串
	 * @param original	被匹配的字符串
	 * @return
	 */
	public static double matcheSim(String current, String original) {
		int ld = matcheLd(current, original);
		return 1 - (double) ld / Math.max(current.length(), original.length());
	}

	/**
	 * 这个方法的思路是: 用第一个字符替换成第二个字符所编辑的次数，如果编辑次数越少，证明相似度越高，就可以将它们归为一类
	 * 这样for循环两两比较，值小的归为一类
	 * @param args
	 */
	public static void main(String[] args) {
		String str1 = "这是一个神奇的网站!~";
		String str2 = "这是一个神奇的网站!~";
		System.out.println("ld = " + matcheLd(str1, str2));
		System.out.println("sim = " + matcheSim(str1, str2));
	}
}