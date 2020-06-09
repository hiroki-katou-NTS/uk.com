package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CsvRecordLoader;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TestDataCsvRecord;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;

/**
 * 年休付与残数データ
 * @author masaaki_jinno
 *
 */
public class TestAnnualLeaveGrantRemainingData {

//	/** ケース番号 */
//	private String caseNo;
	
	/** 年休付与残数データ */
	//private AggrResultOfAnnualLeave aggrResultOfAnnualLeave;
	//private Map<String, AnnualLeaveGrantRemainingData> mapStringData;
	
	//private static String fileDir = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\テストコード\\";
	private static String fileName = "/nts/uk/ctx/at/record/dom/remainingnumber/annualleave/export/AnnualLeaveGrantRemainingData.csv";

//	public static Map<String, TestOutputAggrResultOfAnnualLeave> build(){
//		
//		Map<String, TestOutputAggrResultOfAnnualLeave> map 
//			= new HashMap<String, TestOutputAggrResultOfAnnualLeave>();
//		
//		CsvRecordLoader.load(fileDir+fileName, builder, TestOutputAggrResultOfAnnualLeave.class)
//				.stream()
//				.forEach(a -> map.put(a.getCaseNo(), a));
//		
//		return map;
//	}
	
	public static List<AnnualLeaveGrantRemainingData> build(){
		return CsvRecordLoader.load(fileName, builder, AnnualLeaveGrantRemainingData.class);
	}

	static Function<TestDataCsvRecord, AnnualLeaveGrantRemainingData> builder = record ->{
		
		String s;
		 //　s = record.asStr("No");
		 
		 String employeeId = record.asStr("社員ID");
		 //employeeId = employeeId.replace("", "");
		 
		 s = record.asStr("付与日");
		 GeneralDate grantDate = TestData.stringyyyyMMddToGeneralDate(s);
		 
		 s = record.asStr("期限日");
		 GeneralDate deadline = TestData.stringyyyyMMddToGeneralDate(s);
		 
		 s = record.asStr("期限切れ状態");
		 LeaveExpirationStatus expirationStatus = LeaveExpirationStatus.AVAILABLE; // 固定

		 s = record.asStr("登録種別");
		 GrantRemainRegisterType registerType = GrantRemainRegisterType.MONTH_CLOSE; // 固定
	 
		 s = record.asStr("明細付与数日数");
		 Double grantDays = TestData.stringToDouble(s);
		 
		 s = record.asStr("明細付与数時間");
		 Integer grantMinutes = TestData.stringToInteger(s);
		 
		 s = record.asStr("明細使用数日数");
		 Double usedDays = TestData.stringToDouble(s);
		 
		 s = record.asStr("明細使用数時間");
		 Integer usedMinutes = TestData.stringToInteger(s);
		 
		 s = record.asStr("明細使用数積み崩し日数");
		 Double stowageDays = TestData.stringToDouble(s);
		 
		 s = record.asStr("明細残数日数");
		 Double remainDays = TestData.stringToDouble(s);
		 
		 s = record.asStr("明細残数時間");
		 Integer remainMinutes = TestData.stringToInteger(s);
		 
		 s = record.asStr("明細使用率");
		 Double usedPercent = 0.0;
		 
		 // 明細
		 AnnualLeaveNumberInfo annualLeaveNumberInfo = new AnnualLeaveNumberInfo(
				 grantDays, grantMinutes, usedDays, usedMinutes,
				 stowageDays, remainDays, remainMinutes, usedPercent );
		 
		 AnnualLeaveGrantRemainingData a = new AnnualLeaveGrantRemainingData(
				 employeeId, grantDate, deadline, expirationStatus, registerType, annualLeaveNumberInfo );
				 
//		 s = record.asStr("年休付与条件情報所定日数");
//		 s = record.asStr("年休付与条件情報控除日数");
//		 s = record.asStr("年休付与条件情報労働日数");

		return a;
	};
}
