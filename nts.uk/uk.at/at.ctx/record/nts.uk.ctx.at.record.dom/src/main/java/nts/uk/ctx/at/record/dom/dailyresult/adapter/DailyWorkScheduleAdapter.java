package nts.uk.ctx.at.record.dom.dailyresult.adapter;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.Imported.日別勤務予定を取得する.日別勤務予定を取得する
 */
public interface DailyWorkScheduleAdapter {
	/**
	 * [1]取得する
	 * 
	 * @param sid      社員ID
	 * @param baseDate 基準日
	 * @return 勤務種類コード
	 */
	public Optional<String> getWorkTypeCode(String sid, GeneralDate baseDate);
}
