package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractInfoResult;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractionCondition;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OutputCheckResult {
	// 各チェック条件の結果
	List<ResultOfEachCondition> lstResultCondition;
	List<AlarmListCheckInfor> lstCheckType;

	private List<AlarmExtractInfoResult> alarmExtractInfoResults;
	private List<AlarmExtractionCondition> alarmExtractConditions;
}
