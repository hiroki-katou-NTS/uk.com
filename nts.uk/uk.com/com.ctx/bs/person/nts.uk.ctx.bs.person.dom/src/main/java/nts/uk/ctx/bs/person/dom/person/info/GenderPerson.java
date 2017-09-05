package nts.uk.ctx.bs.person.dom.person.info;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderPerson {
	/* 女性 */
	Female(0),
	/* 男性 */
	Male(1);

	public final int value;
}