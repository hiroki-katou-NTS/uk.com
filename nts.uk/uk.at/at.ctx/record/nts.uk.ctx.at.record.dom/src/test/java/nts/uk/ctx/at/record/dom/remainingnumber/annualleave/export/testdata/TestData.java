package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import nts.arc.time.GeneralDate;

/**
 * テストデータ共通処理
 * @author masaaki_jinno
 *
 */
public class TestData {

	// 文字列(yyyyMMdd)　→　GeneralDateへ変換
	static public GeneralDate stringyyyyMMddToGeneralDate(String yyyyMMdd){
		if ( yyyyMMdd.length() < 8 ){
			return null;
		}
		int yyyy = Integer.valueOf(yyyyMMdd.substring(0,4));
		int MM = Integer.valueOf(yyyyMMdd.substring(4,2));
		int dd = Integer.valueOf(yyyyMMdd.substring(6,2));
		
		return GeneralDate.ymd(yyyy, MM, dd);
	}
	
	// 文字列　→　Doubleへ変換
	static public Double stringToDouble(String val){
		if ( val.trim().length() == 0 ){
			return 0.0;
		}
		return Double.valueOf(val);
	}
	
	// 文字列　→　Integerへ変換
	static public Integer stringToInteger(String val){
		if ( val.trim().length() == 0 ){
			return 0;
		}
		return Integer.valueOf(val);
	}
	
}
