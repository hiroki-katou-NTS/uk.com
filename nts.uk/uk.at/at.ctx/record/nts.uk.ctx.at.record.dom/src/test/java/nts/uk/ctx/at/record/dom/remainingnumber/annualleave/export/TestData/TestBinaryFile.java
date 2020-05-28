package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TestData;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;

/**
 * Ｊａｖａのオブジェクト　←→　バイナリファイル　のテスト
 * @author masaaki_jinno
 *
 */
public class TestBinaryFile {

	@Test
	public void test1(){
		//val aTestDataForOverWriteList = new TestDataForOverWriteList();
		//aTestDataForOverWriteList.SaveBinary();
		
		Map<Integer, List<TmpAnnualLeaveMngWork>> map = TestDataForOverWriteList.build();
		List<TmpAnnualLeaveMngWork> list = map.get(1);
		
		TmpAnnualLeaveMngWork aTmpAnnualLeaveMngWork = list.get(0);
	}
}
