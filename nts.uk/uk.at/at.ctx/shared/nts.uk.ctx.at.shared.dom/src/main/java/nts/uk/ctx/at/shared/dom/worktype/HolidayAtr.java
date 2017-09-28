package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HolidayAtr {

	/* 法定内休日 */
    STATUTORY_HOLIDAYS(0, "Enum_HolidayAtr_STATUTORY_HOLIDAYS"),
	/* 法定外休日*/
    NON_STATUTORY_HOLIDAYS(1, "Enum_HolidayAtr_NON_STATUTORY_HOLIDAYS"),
	/* 祝日*/
	PUBLIC_HOLIDAY(2, "Enum_HolidayAtr_PUBLIC_HOLIDAY");

	public final int value;
	public final String nameId;
}
