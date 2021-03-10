package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;

/**
 * Ｊａｖａのオブジェクト　←→　バイナリファイル　のテスト
 * @author masaaki_jinno
 *
 */
public class TestBinaryFile {

	public static String fileDir = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\テストコード\\";
	
	@Test
	public void test1(){
		//val aTestDataForOverWriteList = new TestDataForOverWriteList();
		//aTestDataForOverWriteList.SaveBinary();
		
//		Map<Integer, List<TmpAnnualLeaveMngWork>> map = TestDataForOverWriteList.build();
//		List<TmpAnnualLeaveMngWork> list = map.get(1);
		
		List<AnnualLeaveGrantRemainingData> list = TestAnnualLeaveGrantRemainingData.build();
		
//		Map<Integer, List<TmpAnnualLeaveMngWork>> map = TestDataForOverWriteList.build();
//		List<TmpAnnualLeaveMngWork> list = map.get(1);
		
		AnnualLeaveGrantRemainingData a = list.get(0);
	}
}
