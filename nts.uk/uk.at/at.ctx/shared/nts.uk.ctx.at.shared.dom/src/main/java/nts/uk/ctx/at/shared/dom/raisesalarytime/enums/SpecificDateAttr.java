package nts.uk.ctx.at.record.dom.raisesalarytime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 早退
 *
 */
@AllArgsConstructor
public enum SpecificDateAttr {

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
