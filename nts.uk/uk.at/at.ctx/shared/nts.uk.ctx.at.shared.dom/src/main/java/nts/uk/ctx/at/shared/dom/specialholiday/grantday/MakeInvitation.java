package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MakeInvitation {
	/* 利用しない */
	DoNotUse(0),
	/* 利用する */
	Use(1);

	public final int value;
}
