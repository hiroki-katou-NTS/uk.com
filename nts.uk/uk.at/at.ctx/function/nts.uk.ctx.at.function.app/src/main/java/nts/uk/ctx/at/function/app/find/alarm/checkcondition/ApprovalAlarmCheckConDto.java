package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalAlarmCheckConDto {
	
	List<AppApprovalFixedExtractConditionDto> listFixedExtractConditionWorkRecord;
}
