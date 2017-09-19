package nts.uk.ctx.bs.person.dom.person.info;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderPerson {
	/* 女 */
	Female(2),
	/* 男 */
	Male(1);

	public final int value;
}