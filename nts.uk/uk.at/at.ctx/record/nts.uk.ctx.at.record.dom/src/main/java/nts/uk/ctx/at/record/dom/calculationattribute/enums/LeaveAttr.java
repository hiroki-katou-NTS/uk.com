package nts.uk.ctx.at.record.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 遅刻
 *
 */
@AllArgsConstructor
public enum LeaveAttr {
	
	// 0: 使用しない
	NOT_USE(0),
	// 1: 使用する
	USE(1);
	
	public final int value;
	
	/**
	 * USE であるか判定する
	 * @return　USEである
	 */
	public boolean isUse() {
		return this.equals(USE);
	}

}
