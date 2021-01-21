package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.実績スケ区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum TrackRecordAtr {
	
	/**
	 * 日別実績
	 */
	DAILY_RESULTS(0, "日別実績"),
	
	/**
	 * スケジュール
	 */
	SCHEDULE(1, "スケジュール");
	
	public final int value;
	
	public final String name;
	
}
