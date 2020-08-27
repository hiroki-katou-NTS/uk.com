package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.予定反映不可理由
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReasonNotReflect {
	
	/**
	 *  勤務スケジュール確定済
	 */
	WORK_FIXED(0, "勤務スケジュール確定済"),
	
	/**
	 *  作業スケジュール確定済
	 */
	WORK_CONFIRMED(1, "作業スケジュール確定済"),
	
	/**
	 * 実績がロックされている
	 */
	ACHIEVEMENTS_LOCKED(2, "実績がロックされている"),
	
	/**
	 * 本人確認、上司確認済
	 */
	SELF_CONFIRMED_BOSS_CONFIRMED(3, "本人確認、上司確認済"),
	
	/**
	 * 締め処理が完了している
	 */
	TIGHTENING_PROCESS_COMPLETED(4, "締め処理が完了している");
	
	public final int value;
	
	public final String name;
}
