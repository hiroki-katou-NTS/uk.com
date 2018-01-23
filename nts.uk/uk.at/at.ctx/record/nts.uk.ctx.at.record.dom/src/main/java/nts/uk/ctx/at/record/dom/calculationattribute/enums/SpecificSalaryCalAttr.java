package nts.uk.ctx.at.record.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 特定加給計算区分
 *
 */
@AllArgsConstructor
public enum SpecificSalaryCalAttr {
	
	// 0: 使用しない
	NOT_USE(0),
	// 1: 使用する
	USE(1);
	
	public final int value;
	
	/**
	 * 使用するか判定する
	 * @return　使用する
	 */
	public boolean isUse() {
		return this.equals(USE);
	}

}
