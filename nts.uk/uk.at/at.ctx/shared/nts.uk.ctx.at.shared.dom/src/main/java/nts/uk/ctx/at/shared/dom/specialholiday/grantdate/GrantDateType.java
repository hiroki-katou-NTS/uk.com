package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantDateType {
	/* 会社 */
	
	COMPANY(0),
	
	/* 人 */
	PERSON(1);
	

	public final int value;
}
