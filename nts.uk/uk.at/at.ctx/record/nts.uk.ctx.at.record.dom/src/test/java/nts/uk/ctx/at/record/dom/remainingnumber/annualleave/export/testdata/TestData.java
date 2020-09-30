package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * テストデータ共通処理
 * @author masaaki_jinno
 *
 */
public class TestData {

	/**
	 * 文字列(yyyyMMdd)　→　GeneralDateへ変換
	 * @param yyyyMMdd
	 * @return
	 */
	static public GeneralDate stringyyyyMMddToGeneralDate(String yyyyMMdd){
		if ( yyyyMMdd.length() < 8 ){
			return null;
		}
		int yyyy = Integer.valueOf(yyyyMMdd.substring(0,4));
		int MM = Integer.valueOf(yyyyMMdd.substring(4,6));
		int dd = Integer.valueOf(yyyyMMdd.substring(6,8));
		
		return GeneralDate.ymd(yyyy, MM, dd);
	}
	
	/**
	 * 文字列　→　Doubleへ変換
	 * @param val
	 * @return
	 */
	static public Double stringToDouble(String val){
		if ( val.trim().length() == 0 ){
			return 0.0;
		}
		return Double.valueOf(val);
	}
	
	/**
	 * 文字列　→　Integerへ変換
	 * @param val
	 * @return
	 */
	static public Integer stringToInteger(String val){
		if ( val.trim().length() == 0 ){
			return 0;
		}
		return Integer.valueOf(val);
	}
	
	/**
	 * 期間文字列(yyyyMMdd:yyyyMMdd)　→　DatePeriodへ変換
	 * @param str_period
	 * @return
	 */
	static public DatePeriod strToDatePeriod(String str_period){
		
		if ( str_period.length() != 17 ){
			return null;
		}
		String s1 = str_period.substring(0, 8);
		String s2 = str_period.substring(9, 17);
		
		GeneralDate startdate = stringyyyyMMddToGeneralDate(s1);
		GeneralDate enddate = stringyyyyMMddToGeneralDate(s2);
		
		return new DatePeriod(startdate, enddate);
	}
	
}
