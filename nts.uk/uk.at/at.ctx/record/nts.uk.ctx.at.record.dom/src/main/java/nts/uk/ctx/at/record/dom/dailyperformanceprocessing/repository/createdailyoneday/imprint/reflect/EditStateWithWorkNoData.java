package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditStateWithWorkNoData {
	private Map<Integer, EditStateOfDailyAttd> editStateMap;
	
	private Map<Integer, Integer> mapNo;
}
