package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UseEmployee {
	/* 利用しない */
	DoNotUse(0),
	/* 利用する */
	Use(1);

	public final int value;
}
