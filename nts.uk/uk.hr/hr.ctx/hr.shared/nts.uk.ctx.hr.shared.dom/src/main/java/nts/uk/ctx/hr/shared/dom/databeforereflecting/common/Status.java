/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

import lombok.AllArgsConstructor;

/**
 * ステータス
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報反映前データ.個人情報反映前データ_共通.ステータス
 *
 */
@AllArgsConstructor
public enum Status {
	Unregistered(0), // 未登録

	Registered(1), // 登録済み/承認待ち
	
	WaitingReflection(2), // 承認済み/反映待ち
	
	Reflected(3), // 反映済み
	 
	Deny(4); // 否認/差戻し
	public final int value;
}
