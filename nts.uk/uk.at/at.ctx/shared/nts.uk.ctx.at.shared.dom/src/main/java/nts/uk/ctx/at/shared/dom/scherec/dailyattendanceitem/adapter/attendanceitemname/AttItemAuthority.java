package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import lombok.Data;

@Data
public class AttItemAuthority {
	/** 利用する */
	private boolean toUse;
	/** 他人が変更できる */
	private boolean canBeChangedByOthers;
	/** 本人が変更できる */
	private boolean youCanChangeIt;
}
