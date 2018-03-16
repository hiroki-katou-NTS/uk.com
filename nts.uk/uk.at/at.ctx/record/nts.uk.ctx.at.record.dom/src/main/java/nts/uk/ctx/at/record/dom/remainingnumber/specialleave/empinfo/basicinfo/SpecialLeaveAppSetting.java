package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecialLeaveAppSetting {

	//個人
	PERSON(0),

	//所定
	PRESCRIBED(1);
	
	/** The value. */
	public final int value;
}
