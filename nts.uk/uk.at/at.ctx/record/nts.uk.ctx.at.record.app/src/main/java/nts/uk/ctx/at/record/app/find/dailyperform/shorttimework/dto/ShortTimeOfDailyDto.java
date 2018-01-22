package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName = "日別実績の短時間勤務時間帯")
@Data
public class ShortTimeOfDailyDto implements ConvertibleAttendanceItem {

	// TODO: item id not map
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** 時間帯: 短時間勤務時間帯 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間帯", listMaxLength = 2, indexField = "shortWorkTimeFrameNo", enumField = "childCareAttr")
	private List<ShortWorkTimeSheetDto> shortWorkingTimeSheets;

	public static ShortTimeOfDailyDto getDto(ShortTimeOfDailyPerformance domain){
		ShortTimeOfDailyDto result = new ShortTimeOfDailyDto();
		if (domain != null) {
			result.setEmployeeId(domain.getEmployeeId());
			result.setYmd(domain.getYmd());
			result.setShortWorkingTimeSheets(ConvertHelper.mapTo(domain.getShortWorkingTimeSheets(),
					(c) -> new ShortWorkTimeSheetDto(c.getShortWorkTimeFrameNo().v(), c.getChildCareAttr().value,
							c.getStartTime() == null ? null : c.getStartTime().valueAsMinutes(),
							c.getEndTime() == null ? null : c.getEndTime().valueAsMinutes(),
							c.getDeductionTime() == null ? null : c.getDeductionTime().valueAsMinutes(),
							c.getShortTime() == null ? null : c.getShortTime().valueAsMinutes())));
		}
		return result;
	}
}
