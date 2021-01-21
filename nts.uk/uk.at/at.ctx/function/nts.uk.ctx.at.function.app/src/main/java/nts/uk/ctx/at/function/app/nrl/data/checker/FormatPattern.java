package nts.uk.ctx.at.function.app.nrl.data.checker;

import java.util.regex.Pattern;

/**
 * Format pattern.
 * 
 * @author manhnd
 */
public class FormatPattern {

	/**
	 * HUALPHA
	 */
	public static final String HUALPHA = "^[A-Z]+$"; 
	
	/**
	 * HUALPHANUM
	 */
	public static final String HUALPHANUM = "^[A-Z0-9]+$";
	
	/**
	 * HALPHANUM_S
	 */
	public static final String HALPHANUM_S = "^[A-Za-z0-9 ]+$";
	
	/**
	 * NUMBER
	 */
	public static final String NUMBER = "^[0-9]+$";
	
	/**
	 * isHwUAlpha.
	 * @param text
	 * @return
	 */
	public static boolean isHwUAlpha(String text) {
		return Pattern.matches(HUALPHA, text);
	}
	
	/**
	 * isHwUAlphanumeric.
	 * @param text
	 * @return
	 */
	public static boolean isHwUAlphanumeric(String text) {
		return Pattern.matches(HUALPHANUM, text);
	}
	
	/**
	 * isHwAlphanumericS.
	 * @param text
	 * @return
	 */
	public static boolean isHwAlphanumericS(String text) {
		return Pattern.matches(HALPHANUM_S, text);
	}
	
	/**
	 * isNumber
	 * @param text
	 * @return
	 */
	public static boolean isNumeric(String text) {
		return Pattern.matches(NUMBER, text);
	}
	
	/**
	 * isYymmdd.
	 * @param text
	 * @return
	 */
	public static boolean isYymmdd(String text) {
		if (text.length() != 6) return false;
		int yy = Integer.parseInt(text.substring(0, 2));
		if (yy < 0 || yy > 99) return false;
		int mm = Integer.parseInt(text.substring(2, 4));
		if (mm < 1 || mm > 12) return false;
		int dd = Integer.parseInt(text.substring(4));
		if (dd < 1 || dd > 31) return false;
		return true;
	}
	
	/**
	 * isHHmm.
	 * @param text
	 * @return
	 */
	public static boolean isHHmm(String text) {
		if (text.length() != 4) return false;
		int hh = Integer.parseInt(text.substring(0, 2));
		if (hh < 0 || hh > 23) return false;
		int mm = Integer.parseInt(text.substring(2));
		if (mm < 0 || mm > 59) return false;
		return true;
	}
	
	/**
	 * isHHmmss
	 * @param text
	 * @return
	 */
	public static boolean isHHmmss(String text) {
		if (text.length() != 6) return false;
		int hh = Integer.parseInt(text.substring(0, 2));
		if (hh < 0 || hh > 23) return false;
		int mm = Integer.parseInt(text.substring(2, 4));
		if (mm < 0 || mm > 59) return false;
		int ss = Integer.parseInt(text.substring(4));
		if (ss < 0 || ss > 59) return false;
		return true;
	}
}
