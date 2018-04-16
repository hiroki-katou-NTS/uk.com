package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecialLeaveAppSetting {

	//個人
	PERSONAL(1),

	//所定
	PRESCRIBED(0);
	
	/** The value. */
	public final int value;
}
