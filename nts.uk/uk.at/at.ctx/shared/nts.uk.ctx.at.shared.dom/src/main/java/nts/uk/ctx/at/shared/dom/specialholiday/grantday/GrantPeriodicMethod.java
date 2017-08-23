package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantPeriodicMethod {
	/* 許可する */
	Allow(0),
	/* 許可しない */
	DoNotAllow(1);

	public final int value;
}
