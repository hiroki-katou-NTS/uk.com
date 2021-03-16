package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractedAlarmDto {
	private List<AlarmExtraValueWkReDto> extractedAlarmData;
	/**
	 * 処理中
	 */
	private boolean extracting;
	/**
	 * True: データなし
	 */
	private boolean nullData;	
}
