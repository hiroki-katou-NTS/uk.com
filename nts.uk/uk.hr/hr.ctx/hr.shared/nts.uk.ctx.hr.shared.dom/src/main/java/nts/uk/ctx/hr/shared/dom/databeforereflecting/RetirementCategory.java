/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting;

import lombok.AllArgsConstructor;

/**
 * 退職区分
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
