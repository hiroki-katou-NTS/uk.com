package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgeCriteriaAtr {
	/* 付与日 */
	GrantDate(0),
	/* 指定日 */
	DesignatedDate(1);

	public final int value;
}
