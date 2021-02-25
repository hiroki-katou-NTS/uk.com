package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.日別実績反映不可理由
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReasonNotReflectDaily {
	
	/**
	 * 実績確定済
	 */
	ACTUAL_CONFIRMED(0, "実績確定済"),
	
	/**
	 * 実績がロックされている
	 */
	ACHIEVEMENTS_LOCKED(1, "実績がロックされている"),
	
	/**
	 * 本人確認、上司確認済
	 */
	SELF_CONFIRMED_BOSS_CONFIRMED(2, "本人確認、上司確認済"),
	
	/**
	 * 締め処理が完了している
	 */
	TIGHTENING_PROCESS_COMPLETED(3, "締め処理が完了している");
	
	public final int value;
	
	public final String name;
}
