package nts.uk.ctx.at.record.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 特定加給計算区分
 *
 */
@AllArgsConstructor
public enum SpecificSalaryCalSetting {
	
	// 0: 使用しない
	NOT_USE(0),
	// 1: 使用する
	USE(1);
	
	public final int value;

}
