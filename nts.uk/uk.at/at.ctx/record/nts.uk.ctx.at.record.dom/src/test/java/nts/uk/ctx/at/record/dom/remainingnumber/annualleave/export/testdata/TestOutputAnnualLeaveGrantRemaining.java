package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CsvRecordLoader;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TestDataCsvRecord;

/**
 * テスト　年休付与残数データ 期待値　AnnualLeaveGrantRemainingクラス
 * @author masaaki_jinno
 *
 */
@Getter
public class TestOutputAnnualLeaveGrantRemaining {
	
	/** ケース番号 */
	private String caseNo;
	
	/** 年休の集計結果 期待値（文字列のまま） */
	private Map<String, String> mapStringData;
	
	private static String fileName = "/nts/uk/ctx/at/record/dom/remainingnumber/annualleave/export/TestOutputAnnualLeaveGrantRemaining.csv";
	
	public static List<TestOutputAnnualLeaveGrantRemaining> build(){
		return CsvRecordLoader.load(fileName, builder, TestOutputAnnualLeaveGrantRemaining.class);
	}

	static Function<TestDataCsvRecord, TestOutputAnnualLeaveGrantRemaining> builder = record ->{
		
		TestOutputAnnualLeaveGrantRemaining a = new TestOutputAnnualLeaveGrantRemaining();
		a.mapStringData = new HashMap<String, String>();
		
		a.caseNo = record.asStr("テストケース");
		
		 a.mapStringData.put("テストケース",  record.asStr("テストケース"));
		 a.mapStringData.put("社員ID",  record.asStr("社員ID"));
		 a.mapStringData.put("付与日",  record.asStr("付与日"));
		 a.mapStringData.put("期限日",  record.asStr("期限日"));
		 a.mapStringData.put("期限切れ状態",  record.asStr("期限切れ状態"));
		 a.mapStringData.put("登録種別",  record.asStr("登録種別"));
		 a.mapStringData.put("明細付与数日数",  record.asStr("明細付与数日数"));
		 a.mapStringData.put("明細付与数時間",  record.asStr("明細付与数時間"));
		 a.mapStringData.put("明細使用数日数",  record.asStr("明細使用数日数"));
		 a.mapStringData.put("明細使用数時間",  record.asStr("明細使用数時間"));
		 a.mapStringData.put("明細使用数積み崩し日数",  record.asStr("明細使用数積み崩し日数"));
		 a.mapStringData.put("明細残数日数",  record.asStr("明細残数日数"));
		 a.mapStringData.put("明細残数時間",  record.asStr("明細残数時間"));
		 a.mapStringData.put("明細使用率",  record.asStr("明細使用率"));
		 a.mapStringData.put("年休付与条件情報所定日数",  record.asStr("年休付与条件情報所定日数"));
		 a.mapStringData.put("年休付与条件情報控除日数",  record.asStr("年休付与条件情報控除日数"));
		 a.mapStringData.put("年休付与条件情報労働日数",  record.asStr("年休付与条件情報労働日数"));

		return a;
	};
}
