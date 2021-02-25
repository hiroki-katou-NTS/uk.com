/**
 * 10:05:43 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums;

import lombok.AllArgsConstructor;

/**
 * 使用するしない区分
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum UseAtr {

	/** 使用しない */
	NOT_USE(0, "Enum_UseAtr_NotUse"),
	/** 使用する */
	USE(1, "Enum_UseAtr_Use");

	public final int value;

	public final String nameId;
	
	/**
	 * 使用するか判定する
	 * @return 使用する
	 */
	public boolean isUse() {
		return USE.equals(this);
	}

}
