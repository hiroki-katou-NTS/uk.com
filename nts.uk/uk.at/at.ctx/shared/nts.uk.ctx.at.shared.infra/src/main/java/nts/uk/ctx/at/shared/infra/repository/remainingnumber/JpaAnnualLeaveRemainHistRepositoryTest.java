package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;

public class JpaAnnualLeaveRemainHistRepositoryTest {

	public JpaAnnualLeaveRemainHistRepositoryTest() {
		// TODO Auto-generated method stub
		
		JpaAnnualLeaveRemainHistRepository aJpaAnnualLeaveRemainHistRepository
			= new JpaAnnualLeaveRemainHistRepository();
		
		List<AnnualLeaveRemainingHistory> remainHistList 
			= aJpaAnnualLeaveRemainHistRepository.getInfoBySidAndYM(
			"this.employeeId", new nts.arc.time.YearMonth(201009));
	
		Map<String,Object> map = new HashMap<>();
		map.put("CalAttr",remainHistList );
		
		
		val file = Paths.get("c:\\BinaryWithCid.csv");
		ObjectBinaryFile.write(map, file);
		
	}

}
