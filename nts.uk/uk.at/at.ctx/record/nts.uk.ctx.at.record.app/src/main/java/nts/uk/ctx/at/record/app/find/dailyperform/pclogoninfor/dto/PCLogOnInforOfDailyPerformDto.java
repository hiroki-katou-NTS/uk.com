package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnNo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AttendanceItemRoot(rootName = "日別実績のPCログオン情報")
@Data
public class PCLogOnInforOfDailyPerformDto extends AttendanceItemCommon {

	private String employeeId;

	private GeneralDate ymd;

	 @AttendanceItemLayout(layout = "A", jpPropertyName = "ログオン情報", listMaxLength = 2, indexField = "logNo")
	private List<LogonInfoDto> logonTime;

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}
	
	public static PCLogOnInforOfDailyPerformDto from(PCLogOnInfoOfDaily domain){
		PCLogOnInforOfDailyPerformDto dto = new PCLogOnInforOfDailyPerformDto();
		if (domain != null) {
			dto.setLogonTime(ConvertHelper.mapTo(domain.getLogOnInfo(),
					(c) -> new LogonInfoDto(
								c.getWorkNo() == null ? null : c.getWorkNo().v(),
								c.getLogOn().isPresent() ? c.getLogOn().get().valueAsMinutes() : null,
								c.getLogOff().isPresent() ? c.getLogOff().get().valueAsMinutes() : null
					)));
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public PCLogOnInfoOfDaily toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new PCLogOnInfoOfDaily(employeeId, date, ConvertHelper.mapTo(logonTime, 
							c -> new LogOnInfo(new PCLogOnNo(c.getLogNo()),
								toWorkStamp(c.getLogOff()), toWorkStamp(c.getLogOn()))));
	}

	private TimeWithDayAttr toWorkStamp(Integer time){
		return time == null ? null : new TimeWithDayAttr(time);
	}
}
