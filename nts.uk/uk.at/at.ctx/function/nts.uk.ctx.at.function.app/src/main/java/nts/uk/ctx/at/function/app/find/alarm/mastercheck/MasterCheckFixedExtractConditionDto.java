package nts.uk.ctx.at.function.app.find.alarm.mastercheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.ErrorAlarmAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractCondition;

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
				domain.getNo().value,
				domain.getMessage().isPresent() ? domain.getMessage().get().v() : "",
				domain.isUseAtr(),
				ErrorAlarmAtr.OTH.value
				);
	}
}
