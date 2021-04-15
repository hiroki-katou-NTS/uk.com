package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarkDto;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarksOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_SUPPORT_TIMESHEET_NAME)
public class OuenWorkTimeSheetOfDailyDto extends AttendanceItemCommon {
	
	private static final long serialVersionUID = 1L;
	
	private String empId;
	
	private GeneralDate ymd;
	@AttendanceItemLayout(layout = LAYOUT_U, jpPropertyName = FAKED, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<OuenWorkTimeSheetOfDailyAttendanceDto> ouenTimeSheet;
	
	@Override
	public String employeeId() {
		return this.empId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public List<OuenWorkTimeSheetOfDailyAttendance> toDomain(String employeeId, GeneralDate date) {
		if (this.isHaveData()) {
			return ouenTimeSheet.stream()
					.map(c -> c.toDomain(employeeId, date))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	public static OuenWorkTimeSheetOfDailyDto getDto(String employeeId, GeneralDate date, List<OuenWorkTimeSheetOfDailyAttendance> domain) {
		OuenWorkTimeSheetOfDailyDto dto = new OuenWorkTimeSheetOfDailyDto();
		if(domain != null && !domain.isEmpty()){
			dto.setEmpId(employeeId);
			dto.setYmd(date);
			dto.setOuenTimeSheet(domain.stream().map(c -> OuenWorkTimeSheetOfDailyAttendanceDto.from(employeeId, date, c))
											.collect(Collectors.toList()));
			dto.exsistData();
		}
		return dto;
	}

}
