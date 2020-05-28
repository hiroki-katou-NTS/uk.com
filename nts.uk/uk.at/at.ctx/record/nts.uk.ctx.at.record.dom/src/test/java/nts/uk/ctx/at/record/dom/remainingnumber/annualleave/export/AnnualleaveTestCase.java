package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 年休テストケース
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class AnnualleaveTestCase {

	/** ケース番号 */
	private String caseNo;
	
	/** パターンＮｏ */
	private String patternNo;
	
	/** パターン名 */
	private String patternName;
	
	/** 社員 */
	private String employee;
	
	/** 集計期間  */
	private String aggrPeriod;
	
	/** モード　*/
	private String mode;
	
	/** 基準日 */
	private String criteriaDate;
	
//	/** 翌月管理データ取得フラグ */
//	private String isGetNextMonthData;
//	
//	/** 出勤率計算フラグ */
//	private String isCalcAttendanceRate ;
//
//	/** 上書きフラグ */
//	private String isOverWrite;

	/** 上書き用の暫定年休管理データ　*/
	private String forOverWriteList;

	/** 集計開始日を締め開始日とする　（締め開始日を確認しない） */
	private String noCheckStartDate;

	/** 年月 */
	private String yearMonth;
	
	/** 付与テーブル設定 */
	private String grantHdTblSet;
	
	/** 付与テーブル */
	private String grantYearHoliday;
	
	/** 暫定残数 */
	private String interimRemain;
	
	/** 暫定年休 */
	private String tmpAnnualHolidayMng;
	
	/** 年休付与履歴 */
	private String annualLeaveRemainHist;
	
	/** 会社設定 */
	private String companyConfig;
	
	//private static String fileDir = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\テストコード\\";
	private static String fileName = "/nts/uk/ctx/at/record/dom/remainingnumber/annualleave/export/testcase.csv";

//	public static Map<String, AnnualleaveTestCase> build(){
//		
//		Map<String, AnnualleaveTestCase> map 
//			= new HashMap<String, AnnualleaveTestCase>();
//		
//		CsvRecordLoader.load(fileDir+fileName, builder, AnnualleaveTestCase.class)
//				.stream()
//				.forEach(a -> map.put(a.getCaseNo(), a));
//		
//		return map;
//	}
	
	public static List<AnnualleaveTestCase> build(){
//		return CsvRecordLoader.load(fileDir+fileName, builder, AnnualleaveTestCase.class);
		return CsvRecordLoader.load(fileName, builder, AnnualleaveTestCase.class);
	}

	static Function<TestDataCsvRecord, AnnualleaveTestCase> builder = record ->{
		AnnualleaveTestCase a = new AnnualleaveTestCase();
		
		a.setCaseNo(record.asStr("No"));
		a.setEmployee(record.asStr("社員"));
		a.setAggrPeriod(record.asStr("集計期間"));
		a.setMode(record.asStr("モード"));
		a.setCriteriaDate(record.asStr("基準日"));
		a.setForOverWriteList(record.asStr("上書き暫定"));
		a.setNoCheckStartDate(record.asStr("締め開始日"));
		a.setYearMonth(record.asStr("年月"));
		a.setGrantHdTblSet(record.asStr("付与テーブル設定"));
		a.setGrantYearHoliday(record.asStr("付与テーブル"));
		a.setInterimRemain(record.asStr("暫定残数"));
		a.setTmpAnnualHolidayMng(record.asStr("暫定年休"));
		a.setAnnualLeaveRemainHist(record.asStr("年休付与履歴"));
		a.setCompanyConfig(record.asStr("会社設定"));

		return a;
	};
}
