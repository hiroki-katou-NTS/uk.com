package nts.uk.ctx.at.shared.dom.attendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.AttendanceItemName;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.WorkTypeCode;

@Getter
public class DisplayAndInputControlOfAttendanceItems extends AggregateRoot {

	private String attendanceItemId;

	private WorkTypeCode workTypeCode;

	private AttendanceItemName attendanceItemName;

	private boolean userCanSet;

	private boolean youCanChangeIt;

	private boolean canBeChangedByOthers;

	private boolean use;

	public DisplayAndInputControlOfAttendanceItems(String attendanceItemId, WorkTypeCode workTypeCode,
			AttendanceItemName attendanceItemName, boolean userCanSet, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.workTypeCode = workTypeCode;
		this.attendanceItemName = attendanceItemName;
		this.userCanSet = userCanSet;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.use = use;
	}

	public static DisplayAndInputControlOfAttendanceItems createFromJavaType(String attendanceItemId,
			String workTypeCode, String attendanceItemName, boolean userCanSet, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use) {
		return new DisplayAndInputControlOfAttendanceItems(attendanceItemId, new WorkTypeCode(workTypeCode),
				new AttendanceItemName(attendanceItemName), userCanSet, youCanChangeIt, canBeChangedByOthers, use);

	}

}
