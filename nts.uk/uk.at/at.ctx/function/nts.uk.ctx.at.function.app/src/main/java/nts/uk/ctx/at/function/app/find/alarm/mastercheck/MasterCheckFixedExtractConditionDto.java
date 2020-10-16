package nts.uk.ctx.at.function.app.find.alarm.mastercheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.ErrorAlarmAtr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractCondition;

@Data
@AllArgsConstructor
public class MasterCheckFixedExtractConditionDto {
	
	private String errorAlarmCheckId;
	
	private String name;
	
	private int no;

	private String message;
	
	private boolean useAtr;

	private int erAlAtr;

	public static MasterCheckFixedExtractConditionDto fromDomain(MasterCheckFixedExtractCondition domain) {
		return new MasterCheckFixedExtractConditionDto(
				domain.getErrorAlarmCheckId(),
				"",
				domain.getNo(),
				domain.getMessage().v(),
				domain.isUseAtr(),
				ErrorAlarmAtr.OTH.value
				);
	}
}
