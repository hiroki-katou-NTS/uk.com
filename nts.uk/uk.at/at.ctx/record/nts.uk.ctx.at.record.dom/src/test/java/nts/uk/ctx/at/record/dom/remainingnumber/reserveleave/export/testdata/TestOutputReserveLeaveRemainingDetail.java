package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.testdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CsvRecordLoader;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TestDataCsvRecord;

/**
 * 積休明細リスト
 * @author masaaki_jinno
 *
 */
@Getter
public class TestOutputReserveLeaveRemainingDetail {

	/** ケース番号 */
	private String caseNo;
	
	/** 積休明細リスト 期待値（文字列のまま） */
	private Map<String, String> mapStringData;
	
	private static String fileName = "/nts/uk/ctx/at/record/dom/remainingnumber/Reserveleave/export/TestOutputReserveLeaveRemainingDetail.csv";
	
	public static List<TestOutputReserveLeaveRemainingDetail> build(){
		return CsvRecordLoader.load(fileName, builder, TestOutputReserveLeaveRemainingDetail.class);
	}

	static Function<TestDataCsvRecord, TestOutputReserveLeaveRemainingDetail> builder = record ->{
		
		TestOutputReserveLeaveRemainingDetail a = new TestOutputReserveLeaveRemainingDetail();
		a.mapStringData = new HashMap<String, String>();
		
		a.caseNo = record.asStr("NO");
		
		a.mapStringData.put("NO",  record.asStr("NO"));
		a.mapStringData.put("付与日",  record.asStr("付与日"));
		a.mapStringData.put("日数",  record.asStr("日数"));

		return a;
	};
}
