package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistoryTest;
import org.junit.Test;

public class AnnualLeaveRemainHistRepositoryTest implements Serializable{

	@Test
	public void test() {
		Map<String,Object> toBinaryMap = new HashMap<String,Object>();
		
//		// 「年休付与残数履歴データ」を取得
//		AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo
//			= new JpaAnnualLeaveRemainHistRepository();	
		
		List<AnnualLeaveRemainingHistoryTest> remainHistList 
			= new ArrayList<AnnualLeaveRemainingHistoryTest>();
		
		AnnualLeaveRemainingHistoryTest aAnnualLeaveRemainingHistoryTest
			= new AnnualLeaveRemainingHistoryTest();
		
		
//					"cID", "employeeId", 201909, 1, 20, true,
//					GeneralDate.ymd(2019, 3, 30),
//					GeneralDate.ymd(2019, 3, 30),
//					1, 0, 1.5, 10, 1.0, 30, 20.5, 4.5, 30,
//					4.5, 4.5, 4.5, 4.5);
		
		remainHistList.add(aAnnualLeaveRemainingHistoryTest);
		
		toBinaryMap.put("2", remainHistList);
		
		val file = Paths.get("c:\\jinno\\binarytest.csv");
		ObjectBinaryFile.write(toBinaryMap, file);
	}
	
}
