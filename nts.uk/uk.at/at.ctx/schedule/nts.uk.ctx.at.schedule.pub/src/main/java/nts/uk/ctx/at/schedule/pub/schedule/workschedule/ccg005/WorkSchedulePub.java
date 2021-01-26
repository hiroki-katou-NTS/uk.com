package nts.uk.ctx.at.schedule.pub.schedule.workschedule.ccg005;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.Export.日別勤務予定を取得する.社員IDリスト、基準日から勤務予定を取得する
 */
public interface WorkSchedulePub {
	/**
	 * [1] 取得する
	 * 
	 * @param sid      社員ID
	 * @param baseDate 基準日
	 * @return 勤務種類コード
	 */
	public Optional<String> getWorkTypeCode(String sid, GeneralDate baseDate);
}
