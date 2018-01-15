package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UseAge {
	/* 許可する */
	Allow(1),
	/* 許可しない */
	NotAllow(0);

	public final int value;
}
