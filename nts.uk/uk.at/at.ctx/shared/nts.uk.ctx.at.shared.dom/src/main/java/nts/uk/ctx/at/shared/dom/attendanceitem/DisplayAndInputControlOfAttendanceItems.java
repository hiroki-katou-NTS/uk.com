package nts.uk.ctx.at.shared.dom.attendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;

@Getter
public class DisplayAndInputControlOfAttendanceItems extends AggregateRoot {

	private String attendanceItemId;

	private BusinessTypeCode businessTypeCode;

	private boolean userCanSet;

	private boolean youCanChangeIt;

	private boolean canBeChangedByOthers;

	private boolean use;

	public DisplayAndInputControlOfAttendanceItems(String attendanceItemId, BusinessTypeCode businessTypeCode,
			 boolean userCanSet, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.businessTypeCode = businessTypeCode;
		this.userCanSet = userCanSet;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.use = use;
	}

	public static DisplayAndInputControlOfAttendanceItems createFromJavaType(String attendanceItemId,
			String businessTypeCode, boolean userCanSet, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use) {
		return new DisplayAndInputControlOfAttendanceItems(attendanceItemId, new BusinessTypeCode(businessTypeCode), userCanSet, youCanChangeIt, canBeChangedByOthers, use);

	}

}
