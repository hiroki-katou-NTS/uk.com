/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo;

import lombok.AllArgsConstructor;

/**
 * 退職区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報反映前データ.個人情報反映前データ_退職者情報.退職区分
 */

@AllArgsConstructor
public enum RetirementCategory {
	
	retirement(1, "退職"), // 退職

	transfer(2, "退職"), // 転籍

	dismissal(3, "解雇"), // 解雇

	retirementAge(4, "定年"); // 定年

	public int value;
	public String name;
}
