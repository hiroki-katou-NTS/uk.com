package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HolidayAtr {

	/* 法定内休日 */
    STATUTORY_HOLIDAYS(0),
	/* 法定外休日*/
    NON_STATUTORY_HOLIDAYS(1),
	/* 祝日*/
	PUBLIC_HOLIDAY(2);

	public final int value;
}
