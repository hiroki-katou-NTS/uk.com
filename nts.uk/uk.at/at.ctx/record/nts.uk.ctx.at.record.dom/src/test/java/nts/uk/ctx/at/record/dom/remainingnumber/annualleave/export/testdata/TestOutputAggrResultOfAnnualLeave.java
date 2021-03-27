package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CsvRecordLoader;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TestDataCsvRecord;

/**
 * テスト　年休の集計結果 期待値　AggrResultOfAnnualLeaveクラス
 * @author masaaki_jinno
 *
 */
@Getter
public class TestOutputAggrResultOfAnnualLeave {

	/** ケース番号 */
	private String caseNo;
	
	/** 年休の集計結果 期待値（文字列のまま） */
	//private AggrResultOfAnnualLeave aggrResultOfAnnualLeave;
	private Map<String, String> mapStringData;
	
	//private static String fileDir = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\テストコード\\";
	private static String fileName = "/nts/uk/ctx/at/record/dom/remainingnumber/annualleave/export/test/TestOutputAggrResultOfAnnualLeave.csv";

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
	
	public static List<TestOutputAggrResultOfAnnualLeave> build(){
//		return CsvRecordLoader.load(fileDir+fileName, builder, TestOutputAggrResultOfAnnualLeave.class);
		return CsvRecordLoader.load(fileName, builder, TestOutputAggrResultOfAnnualLeave.class);
	}

	static Function<TestDataCsvRecord, TestOutputAggrResultOfAnnualLeave> builder = record ->{
		
		TestOutputAggrResultOfAnnualLeave a = new TestOutputAggrResultOfAnnualLeave();
		a.mapStringData = new HashMap<String, String>();
		
		a.caseNo = record.asStr("テストケース"); 
		a.mapStringData.put("テストケース",  record.asStr("テストケース"));
		 a.mapStringData.put("年月日",  record.asStr("年月日"));
		 a.mapStringData.put("残数年休マイナスあり使用数付与前使用日数",  record.asStr("残数年休マイナスあり使用数付与前使用日数"));
		 a.mapStringData.put("残数年休マイナスあり使用数付与前使用時間",  record.asStr("残数年休マイナスあり使用数付与前使用時間"));
		 a.mapStringData.put("残数年休マイナスあり使用数合計使用日数",  record.asStr("残数年休マイナスあり使用数合計使用日数"));
		 a.mapStringData.put("残数年休マイナスあり使用数合計使用時間",  record.asStr("残数年休マイナスあり使用数合計使用時間"));
		 a.mapStringData.put("残数年休マイナスあり使用数使用回数",  record.asStr("残数年休マイナスあり使用数使用回数"));
		 a.mapStringData.put("残数年休マイナスあり使用数付与後使用日数",  record.asStr("残数年休マイナスあり使用数付与後使用日数"));
		 a.mapStringData.put("残数年休マイナスあり使用数付与後使用時間",  record.asStr("残数年休マイナスあり使用数付与後使用時間"));
		 a.mapStringData.put("残数年休マイナスあり残数合計合計残日数",  record.asStr("残数年休マイナスあり残数合計合計残日数"));
		 a.mapStringData.put("残数年休マイナスあり残数合計明細",  record.asStr("残数年休マイナスあり残数合計明細"));
		 a.mapStringData.put("残数年休マイナスあり残数合計合計残時間",  record.asStr("残数年休マイナスあり残数合計合計残時間"));
		 a.mapStringData.put("残数年休マイナスあり残数付与前合計残日数",  record.asStr("残数年休マイナスあり残数付与前合計残日数"));
		 a.mapStringData.put("残数年休マイナスあり残数付与前明細",  record.asStr("残数年休マイナスあり残数付与前明細"));
		 a.mapStringData.put("残数年休マイナスあり残数付与前合計残時間",  record.asStr("残数年休マイナスあり残数付与前合計残時間"));
		 a.mapStringData.put("残数年休マイナスあり残数付与後合計残日数",  record.asStr("残数年休マイナスあり残数付与後合計残日数"));
		 a.mapStringData.put("残数年休マイナスあり残数付与後明細",  record.asStr("残数年休マイナスあり残数付与後明細"));
		 a.mapStringData.put("残数年休マイナスあり残数付与後合計残時間",  record.asStr("残数年休マイナスあり残数付与後合計残時間"));
		 a.mapStringData.put("残数年休マイナスなし使用数付与前使用日数",  record.asStr("残数年休マイナスなし使用数付与前使用日数"));
		 a.mapStringData.put("残数年休マイナスなし使用数付与前使用時間",  record.asStr("残数年休マイナスなし使用数付与前使用時間"));
		 a.mapStringData.put("残数年休マイナスなし合計使用日数",  record.asStr("残数年休マイナスなし合計使用日数"));
		 a.mapStringData.put("残数年休マイナスなし合計使用時間",  record.asStr("残数年休マイナスなし合計使用時間"));
		 a.mapStringData.put("残数年休マイナスなし使用回数",  record.asStr("残数年休マイナスなし使用回数"));
		 a.mapStringData.put("残数年休マイナスなし付与後使用日数",  record.asStr("残数年休マイナスなし付与後使用日数"));
		 a.mapStringData.put("残数年休マイナスなし付与後使用時間",  record.asStr("残数年休マイナスなし付与後使用時間"));
		 a.mapStringData.put("残数年休マイナスなし残数合計合計残日数",  record.asStr("残数年休マイナスなし残数合計合計残日数"));
		 a.mapStringData.put("残数年休マイナスなし残数合計明細",  record.asStr("残数年休マイナスなし残数合計明細"));
		 a.mapStringData.put("残数年休マイナスなし残数合計合計残時間",  record.asStr("残数年休マイナスなし残数合計合計残時間"));
		 a.mapStringData.put("残数年休マイナスなし残数付与前合計残日数",  record.asStr("残数年休マイナスなし残数付与前合計残日数"));
		 a.mapStringData.put("残数年休マイナスなし残数付与前明細",  record.asStr("残数年休マイナスなし残数付与前明細"));
		 a.mapStringData.put("残数年休マイナスなし残数付与前合計残時間",  record.asStr("残数年休マイナスなし残数付与前合計残時間"));
		 a.mapStringData.put("残数年休マイナスなし残数付与後合計残日数",  record.asStr("残数年休マイナスなし残数付与後合計残日数"));
		 a.mapStringData.put("残数年休マイナスなし残数付与後明細",  record.asStr("残数年休マイナスなし残数付与後明細"));
		 a.mapStringData.put("残数年休マイナスなし残数付与後合計残時間",  record.asStr("残数年休マイナスなし残数付与後合計残時間"));
		 a.mapStringData.put("残数半日年休（マイナスあり）使用数回数",  record.asStr("残数半日年休（マイナスあり）使用数回数"));
		 a.mapStringData.put("残数半日年休（マイナスあり）使用数回数付与前",  record.asStr("残数半日年休（マイナスあり）使用数回数付与前"));
		 a.mapStringData.put("残数半日年休（マイナスあり）使用数回数付与後",  record.asStr("残数半日年休（マイナスあり）使用数回数付与後"));
		 a.mapStringData.put("残数半日年休（マイナスあり）残数回数",  record.asStr("残数半日年休（マイナスあり）残数回数"));
		 a.mapStringData.put("残数半日年休（マイナスあり）残数回数付与前",  record.asStr("残数半日年休（マイナスあり）残数回数付与前"));
		 a.mapStringData.put("残数半日年休（マイナスあり）残数回数付与後",  record.asStr("残数半日年休（マイナスあり）残数回数付与後"));
		 a.mapStringData.put("残数未消化未消化日数",  record.asStr("残数未消化未消化日数"));
		 a.mapStringData.put("残数未消化未消化時間",  record.asStr("残数未消化未消化時間"));
		 a.mapStringData.put("残数半日年休（マイナスなし）使用数回数",  record.asStr("残数半日年休（マイナスなし）使用数回数"));
		 a.mapStringData.put("残数半日年休（マイナスなし）使用数回数付与前",  record.asStr("残数半日年休（マイナスなし）使用数回数付与前"));
		 a.mapStringData.put("残数半日年休（マイナスなし）使用数回数付与後",  record.asStr("残数半日年休（マイナスなし）使用数回数付与後"));
		 a.mapStringData.put("残数半日年休（マイナスなし）残数回数",  record.asStr("残数半日年休（マイナスなし）残数回数"));
		 a.mapStringData.put("残数半日年休（マイナスなし）残数回数付与前",  record.asStr("残数半日年休（マイナスなし）残数回数付与前"));
		 a.mapStringData.put("残数半日年休（マイナスなし）残数回数付与後",  record.asStr("残数半日年休（マイナスなし）残数回数付与後"));
		 a.mapStringData.put("残数時間年休（マイナスあり）時間",  record.asStr("残数時間年休（マイナスあり）時間"));
		 a.mapStringData.put("残数時間年休（マイナスあり）時間付与前",  record.asStr("残数時間年休（マイナスあり）時間付与前"));
		 a.mapStringData.put("残数時間年休（マイナスあり）時間付与後",  record.asStr("残数時間年休（マイナスあり）時間付与後"));
		 a.mapStringData.put("残数時間年休（マイナスなし）時間",  record.asStr("残数時間年休（マイナスなし）時間"));
		 a.mapStringData.put("残数時間年休（マイナスなし）時間付与前",  record.asStr("残数時間年休（マイナスなし）時間付与前"));
		 a.mapStringData.put("残数時間年休（マイナスなし）時間付与後",  record.asStr("残数時間年休（マイナスなし）時間付与後"));
		 a.mapStringData.put("付与残数データ",  record.asStr("付与残数データ"));
		 a.mapStringData.put("上限データ社員ID",  record.asStr("上限データ社員ID"));
		 a.mapStringData.put("上限データ半日年休上限上限回数",  record.asStr("上限データ半日年休上限上限回数"));
		 a.mapStringData.put("上限データ半日年休上限使用回数",  record.asStr("上限データ半日年休上限使用回数"));
		 a.mapStringData.put("上限データ半日年休上限残回数",  record.asStr("上限データ半日年休上限残回数"));
		 a.mapStringData.put("上限データ時間年休上限上限時間",  record.asStr("上限データ時間年休上限上限時間"));
		 a.mapStringData.put("上限データ時間年休上限使用時間",  record.asStr("上限データ時間年休上限使用時間"));
		 a.mapStringData.put("上限データ時間年休上限残時間",  record.asStr("上限データ時間年休上限残時間"));
		 a.mapStringData.put("付与情報付与日数",  record.asStr("付与情報付与日数"));
		 a.mapStringData.put("付与情報付与所定日数",  record.asStr("付与情報付与所定日数"));
		 a.mapStringData.put("付与情報付与労働日数",  record.asStr("付与情報付与労働日数"));
		 a.mapStringData.put("付与情報付与控除日数",  record.asStr("付与情報付与控除日数"));
		 a.mapStringData.put("付与情報控除日数付与前",  record.asStr("付与情報控除日数付与前"));
		 a.mapStringData.put("付与情報控除日数付与後",  record.asStr("付与情報控除日数付与後"));
		 a.mapStringData.put("付与情報出勤率",  record.asStr("付与情報出勤率"));
		 a.mapStringData.put("使用日数",  record.asStr("使用日数"));
		 a.mapStringData.put("使用時間",  record.asStr("使用時間"));
		
		return a;
	};
}
