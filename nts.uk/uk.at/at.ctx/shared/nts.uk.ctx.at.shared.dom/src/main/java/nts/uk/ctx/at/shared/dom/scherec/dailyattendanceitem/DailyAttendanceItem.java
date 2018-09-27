package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceName;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;

/**
 * 日次の勤怠項目
 * @author nampt
 *
 */
@Getter
public class DailyAttendanceItem extends AggregateRoot {

	private String companyId;

	/* 勤怠項目ID */
	private int attendanceItemId;

	/* 勤怠項目名称 */
	private AttendanceName attendanceName;

	/* 表示番号 */
	private int displayNumber;

	/* ユーザーが値を変更できる */
	private UseSetting userCanUpdateAtr;

	/* 勤怠項目属性 */
	@Setter
	private DailyAttendanceAtr dailyAttendanceAtr;

	/* 名称の改行位置 */
	private int nameLineFeedPosition;
	
	private Optional<TypesMasterRelatedDailyAttendanceItem> masterType;
	
	@Setter
	private Optional<PrimitiveValueOfAttendanceItem> primitiveValue;

	public DailyAttendanceItem(String companyId, int attendanceItemId, AttendanceName attendanceName, int displayNumber,
			UseSetting userCanUpdateAtr, DailyAttendanceAtr dailyAttendanceAtr, int nameLineFeedPosition,
			Optional<TypesMasterRelatedDailyAttendanceItem> masterType,
			Optional<PrimitiveValueOfAttendanceItem> primitiveValue) {
		super();
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.attendanceName = attendanceName;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
		this.dailyAttendanceAtr = dailyAttendanceAtr;
		this.nameLineFeedPosition = nameLineFeedPosition;
		this.masterType = masterType;
		this.primitiveValue = primitiveValue;
	}

	public static DailyAttendanceItem createFromJavaType(String companyId, int attendanceItemId, String attendanceName,
			int displayNumber, int userCanUpdateAtr, int dailyAttendanceAtr, int nameLineFeedPosition,
			Integer masterType, Integer primitiveValue) {
		return new DailyAttendanceItem(companyId, attendanceItemId, new AttendanceName(attendanceName), displayNumber,
				EnumAdaptor.valueOf(userCanUpdateAtr, UseSetting.class),
				EnumAdaptor.valueOf(dailyAttendanceAtr, DailyAttendanceAtr.class), nameLineFeedPosition,
				masterType == null ? Optional.empty() : Optional.ofNullable(EnumAdaptor.valueOf(masterType, TypesMasterRelatedDailyAttendanceItem.class)),
				primitiveValue == null ? Optional.empty() : Optional.ofNullable(EnumAdaptor.valueOf(primitiveValue, PrimitiveValueOfAttendanceItem.class)));
	}

}
