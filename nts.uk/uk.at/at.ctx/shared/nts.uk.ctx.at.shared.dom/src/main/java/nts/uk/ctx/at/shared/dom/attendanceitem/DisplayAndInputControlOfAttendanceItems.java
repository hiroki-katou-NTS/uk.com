package nts.uk.ctx.at.shared.dom.attendanceitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;

@Getter
public class DisplayAndInputControlOfAttendanceItems extends AggregateRoot {

	private BigDecimal attendanceItemId;

	private BusinessTypeCode businessTypeCode;

	private boolean youCanChangeIt;

	private boolean canBeChangedByOthers;

	private boolean use;

	public DisplayAndInputControlOfAttendanceItems(BigDecimal attendanceItemId, BusinessTypeCode businessTypeCode, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.businessTypeCode = businessTypeCode;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.use = use;
	}

	public static DisplayAndInputControlOfAttendanceItems createFromJavaType(BigDecimal attendanceItemId,
			String businessTypeCode, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use) {
		return new DisplayAndInputControlOfAttendanceItems(attendanceItemId, new BusinessTypeCode(businessTypeCode), youCanChangeIt, canBeChangedByOthers, use);

	}

}
