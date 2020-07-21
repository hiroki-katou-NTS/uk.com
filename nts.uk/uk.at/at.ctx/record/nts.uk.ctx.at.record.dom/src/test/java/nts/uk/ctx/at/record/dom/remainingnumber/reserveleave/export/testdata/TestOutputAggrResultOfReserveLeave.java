package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.testdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CsvRecordLoader;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TestDataCsvRecord;

/**
 * テスト　積休の集計結果 期待値　AggrResultOfReserveLeaveクラス
 * @author masaaki_jinno
 *
 */
@Getter
public class TestOutputAggrResultOfReserveLeave {

	/** ケース番号 */
	private String caseNo;
	
	/** 年休の集計結果 期待値（文字列のまま） */
	private Map<String, String> mapStringData;
	
	private static String fileName = "/nts/uk/ctx/at/record/dom/remainingnumber/Reserveleave/export/test/TestOutputAggrResultOfReserveLeave.csv";

	
	public static List<TestOutputAggrResultOfReserveLeave> build(){
		return CsvRecordLoader.load(fileName, builder, TestOutputAggrResultOfReserveLeave.class);
	}

	static Function<TestDataCsvRecord, TestOutputAggrResultOfReserveLeave> builder = record ->{
		
		TestOutputAggrResultOfReserveLeave a = new TestOutputAggrResultOfReserveLeave();
		a.mapStringData = new HashMap<String, String>();
		
		a.caseNo = record.asStr("テストケース");
		 a.mapStringData.put("テストケース",  record.asStr("テストケース"));
		 a.mapStringData.put("年月日",  record.asStr("年月日"));
		 a.mapStringData.put("残数積立年休（マイナスあり）使用数合計",  record.asStr("残数積立年休（マイナスあり）使用数合計"));
		 a.mapStringData.put("残数積立年休（マイナスあり）使用数付与前",  record.asStr("残数積立年休（マイナスあり）使用数付与前"));
		 a.mapStringData.put("残数積立年休（マイナスあり）使用数付与後",  record.asStr("残数積立年休（マイナスあり）使用数付与後"));
		 a.mapStringData.put("残数積立年休（マイナスあり）残数合計合計残日数",  record.asStr("残数積立年休（マイナスあり）残数合計合計残日数"));
		 a.mapStringData.put("残数積立年休（マイナスあり）残数合計明細",  record.asStr("残数積立年休（マイナスあり）残数合計明細"));
		 a.mapStringData.put("残数積立年休（マイナスあり）残数付与前合計残日数",  record.asStr("残数積立年休（マイナスあり）残数付与前合計残日数"));
		 a.mapStringData.put("残数積立年休（マイナスあり）残数付与前明細",  record.asStr("残数積立年休（マイナスあり）残数付与前明細"));
		 a.mapStringData.put("残数積立年休（マイナスあり）残数付与後合計残日数",  record.asStr("残数積立年休（マイナスあり）残数付与後合計残日数"));
		 a.mapStringData.put("残数積立年休（マイナスあり）残数付与後明細",  record.asStr("残数積立年休（マイナスあり）残数付与後明細"));
		 a.mapStringData.put("残数積立年休（マイナスなし）使用数合計",  record.asStr("残数積立年休（マイナスなし）使用数合計"));
		 a.mapStringData.put("残数積立年休（マイナスなし）使用数付与前",  record.asStr("残数積立年休（マイナスなし）使用数付与前"));
		 a.mapStringData.put("残数積立年休（マイナスなし）使用数付与後",  record.asStr("残数積立年休（マイナスなし）使用数付与後"));
		 a.mapStringData.put("残数積立年休（マイナスなし）残数合計合計残日数",  record.asStr("残数積立年休（マイナスなし）残数合計合計残日数"));
		 a.mapStringData.put("残数積立年休（マイナスなし）残数合計明細",  record.asStr("残数積立年休（マイナスなし）残数合計明細"));
		 a.mapStringData.put("残数積立年休（マイナスなし）残数付与前合計残日数",  record.asStr("残数積立年休（マイナスなし）残数付与前合計残日数"));
		 a.mapStringData.put("残数積立年休（マイナスなし）残数付与前明細",  record.asStr("残数積立年休（マイナスなし）残数付与前明細"));
		 a.mapStringData.put("残数積立年休（マイナスなし）残数付与後合計残日数",  record.asStr("残数積立年休（マイナスなし）残数付与後合計残日数"));
		 a.mapStringData.put("残数積立年休（マイナスなし）残数付与後明細",  record.asStr("残数積立年休（マイナスなし）残数付与後明細"));
		 a.mapStringData.put("残数未消化",  record.asStr("残数未消化"));
		 a.mapStringData.put("付与残数データ",  record.asStr("付与残数データ"));
		 a.mapStringData.put("付与情報",  record.asStr("付与情報"));
		 a.mapStringData.put("使用数",  record.asStr("使用数"));

		return a;
	};
}