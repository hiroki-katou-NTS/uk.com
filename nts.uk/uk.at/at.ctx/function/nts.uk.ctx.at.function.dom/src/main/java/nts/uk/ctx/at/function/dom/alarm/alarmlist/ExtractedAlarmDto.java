package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractionCondition;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;

import java.util.List;

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

	private PersistenceAlarmListExtractResult persisAlarmExtractResult;

	private List<AlarmExtractionCondition> alarmExtractConditions;
}
