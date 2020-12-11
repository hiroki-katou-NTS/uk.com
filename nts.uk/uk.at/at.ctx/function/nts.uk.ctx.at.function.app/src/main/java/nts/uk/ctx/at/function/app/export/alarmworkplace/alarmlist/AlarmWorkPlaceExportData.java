package nts.uk.ctx.at.function.app.export.alarmworkplace.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.dto.AlarmListExtractResultWorkplaceDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmWorkPlaceExportData {
	
	/** Data export */
	private List<AlarmListExtractResultWorkplaceDto> data;

	private String alarmCode;

	private String alarmName;
}
