package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheAnyCondDayDto {
	private String erAlCheckLinkId;
	
	private List<WorkRecordExtraConAdapterDto> scheAnyItemDays;
}
