package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UseAge {
	/* 許可する */
	Allow(0),
	/* 許可しない */
	NotAllow(1);

	public final int value;
}
