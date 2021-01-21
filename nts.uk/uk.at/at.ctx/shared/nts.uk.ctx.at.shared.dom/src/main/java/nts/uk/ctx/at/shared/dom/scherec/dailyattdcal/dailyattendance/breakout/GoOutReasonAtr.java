package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakout;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.休憩・外出.外出理由
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum GoOutReasonAtr {
	
	/**
	 * 私用
	 */
	PRIVATE(0, "私用"),
	
	/**
	 * 公用
	 */
	PUBLIC(1, "公用"),
	
	/**
	 * 有償
	 */
	COMPENSATION(2, "有償"),
	
	/**
	 * 組合
	 */
	UNION(3, "組合");

	public final int value;
	public final String name;
	
}
