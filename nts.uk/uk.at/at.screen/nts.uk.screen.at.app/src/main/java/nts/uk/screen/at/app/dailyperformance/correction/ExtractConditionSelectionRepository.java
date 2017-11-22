package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorAlarmWorkRecordDto;

public interface ExtractConditionSelectionRepository {
	
	// 対応する「勤務実績のエラーアラーム」をすべて取得する
	List<ErrorAlarmWorkRecordDto> getErrorAlarmWorkRecordList(String companyId);
}
