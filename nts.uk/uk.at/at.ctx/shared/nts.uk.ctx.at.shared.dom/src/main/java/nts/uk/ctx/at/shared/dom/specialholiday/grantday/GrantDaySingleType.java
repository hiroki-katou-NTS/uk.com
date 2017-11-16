package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantDaySingleType {
	/** 0- 固定日数を付与する */
	FixDay(0),
	/** 1- 続柄を参照して付与する */
	ReferRelationship(1);

	public final int value;
}
