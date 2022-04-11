package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.ExtractionCondScheduleDayDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;

@Data
@NoArgsConstructor
public class ScheAnyCondDayCommand {
	private String alermCondCode;

	private String erAlCheckLinkId;
	
	private List<WorkRecordExtraConAdapterDto> scheAnyCondDays;
}
