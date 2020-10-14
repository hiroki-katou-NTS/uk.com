package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.MasterCheckFixedExtractConditionDto;

@Getter
@Setter
@NoArgsConstructor
public class MasterCheckAlarmCheckConditionCommand {

	List<MasterCheckFixedExtractConditionDto> listFixedMasterCheckCondition;
	
	public MasterCheckAlarmCheckConditionCommand(List<MasterCheckFixedExtractConditionDto> listFixedMasterCheckCondition) {
		super();
		this.listFixedMasterCheckCondition = listFixedMasterCheckCondition;
	}
	
}
