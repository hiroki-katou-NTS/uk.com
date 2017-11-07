package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;

@Getter
public class DailyServiceTypeControl extends AggregateRoot {

	private int attendanceItemId;

	private BusinessTypeCode businessTypeCode;
	
	private String attendanceItemName;

	private boolean youCanChangeIt;

	private boolean canBeChangedByOthers;

	private boolean use;

	private int userCanSet;

	public static DailyServiceTypeControl createFromJavaType(int attendanceItemId, String businessTypeCode,
			boolean youCanChangeIt, boolean canBeChangedByOthers, boolean use, String attendanceItemName,
			int userCanSet) {
		return new DailyServiceTypeControl(attendanceItemId, new BusinessTypeCode(businessTypeCode), youCanChangeIt,
				canBeChangedByOthers, use, attendanceItemName, userCanSet);

	}

	public DailyServiceTypeControl(int attendanceItemId, BusinessTypeCode businessTypeCode, boolean youCanChangeIt,
			boolean canBeChangedByOthers, boolean use, String attendanceItemName, int userCanSet) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.businessTypeCode = businessTypeCode;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.use = use;
		this.attendanceItemName = attendanceItemName;
		this.userCanSet = userCanSet;
	}

}
