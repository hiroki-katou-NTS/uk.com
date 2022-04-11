package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.FixedConditionWorkRecordDto;

@Data
@NoArgsConstructor
public class ScheFixCondDayCommand {
	private String alermCondCode;
	private String erAlCheckLinkId;
	private List<FixedConditionWorkRecordDto> sheFixItemDays;
}
