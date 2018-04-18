package nts.uk.ctx.at.request.pub.vacation.history.export;

import java.util.List;

/**
 * The Interface VacationHistoryPub.
 */
public interface VacationHistoryPub {
	
	/**
	 * Gets the vacation history.
	 *
	 * @param workTypeCode the work type code
	 * @return the vacation history
	 */
	//計画休暇のルールの履歴を取得する
	List<VacationHistoryExport> getVacationHistory(String workTypeCode);
	
	/**
	 * Gets the max day.
	 *
	 * @param historyId the historyId
	 * @return the max day
	 */
	//計画休暇を取得できる上限日数を取得する
	MaxDayExport getMaxDay(String historyId);
	
}
