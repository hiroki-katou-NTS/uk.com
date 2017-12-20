package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class DailyAttendanceItemAuthority extends AggregateRoot {
	private int attendanceItemId;

	private String authorityId;

	private boolean youCanChangeIt;

	private boolean canBeChangedByOthers;

	private boolean use;

	private int userCanSet;

	public DailyAttendanceItemAuthority(int attendanceItemId, String authorityId,
			boolean youCanChangeIt, boolean canBeChangedByOthers, boolean use, int userCanSet) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.authorityId = authorityId;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.use = use;
		this.userCanSet = userCanSet;
	}
	
	
	public static DailyAttendanceItemAuthority createFromJavaType(int attendanceItemId, String authorityId,
			boolean youCanChangeIt, boolean canBeChangedByOthers, boolean use,
			int userCanSet) {
		
			return new DailyAttendanceItemAuthority(attendanceItemId, authorityId, youCanChangeIt, canBeChangedByOthers, use, userCanSet);
		
	}
	

}
