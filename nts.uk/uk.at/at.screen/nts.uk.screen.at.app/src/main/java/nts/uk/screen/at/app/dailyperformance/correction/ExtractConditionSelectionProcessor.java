package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorAlarmWorkRecordDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExtractConditionSelectionProcessor {

	@Inject
	private ExtractConditionSelectionRepository repository;

	/**
	 * 対応する「勤務実績のエラーアラーム」をすべて取得する
	 * @return 「勤務実績のエラーアラーム」一覧
	 */
	public List<ErrorAlarmWorkRecordDto> getErrorAlarmWorkRecordList() {
		return repository.getErrorAlarmWorkRecordList(AppContexts.user().companyId());
	}
}
