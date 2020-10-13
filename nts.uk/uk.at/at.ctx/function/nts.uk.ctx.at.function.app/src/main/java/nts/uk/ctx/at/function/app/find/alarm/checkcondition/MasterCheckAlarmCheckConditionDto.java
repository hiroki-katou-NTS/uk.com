package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.MasterCheckFixedExtractConditionDto;

@Data
@AllArgsConstructor
public class MasterCheckAlarmCheckConditionDto {

	List<MasterCheckFixedExtractConditionDto> listMasterCheckFixedCon;
	
}
