package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantPeriodicCls {
	/* 残数管理をする */
	ManageRemainNumber(0),
	/* 残数管理をしない */
	DoNot_ManageRemainNumber(1);

	public final int value;
}
