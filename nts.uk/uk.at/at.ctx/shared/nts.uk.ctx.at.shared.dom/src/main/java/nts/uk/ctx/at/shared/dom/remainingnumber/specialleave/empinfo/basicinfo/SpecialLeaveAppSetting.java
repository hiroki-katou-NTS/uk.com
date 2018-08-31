package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecialLeaveAppSetting {

	/**
	 * 個人の条件を適用する
	 */
	PERSONAL(1),

	/**
	 * 所定の条件を適用する
	 */
	PRESCRIBED(0);
	
	/** The value. */
	public final int value;
}
