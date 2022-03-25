package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_TEMPORARY_TIME_NAME)
public class TemporaryTimeOfDailyPerformanceDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_TEMPORARY_TIME_NAME; }
	
	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;

	private List<WorkLeaveTimeDto> workLeaveTime;

	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	public static TemporaryTimeOfDailyPerformanceDto getDto(TemporaryTimeOfDailyPerformance domain) {
		TemporaryTimeOfDailyPerformanceDto dto = new TemporaryTimeOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkLeaveTime(ConvertHelper.mapTo(domain.getAttendance().getTimeLeavingWorks(), (c) -> newWorkLeaveTime(c)));
			dto.exsistData();
		}
		return dto;
	}
	public static TemporaryTimeOfDailyPerformanceDto getDto(String employeeID,GeneralDate ymd,TemporaryTimeOfDailyAttd domain) {
		TemporaryTimeOfDailyPerformanceDto dto = new TemporaryTimeOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(employeeID);
			dto.setYmd(ymd);
			dto.setWorkLeaveTime(ConvertHelper.mapTo(domain.getTimeLeavingWorks(), (c) -> newWorkLeaveTime(c)));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public TemporaryTimeOfDailyPerformanceDto clone() {
		TemporaryTimeOfDailyPerformanceDto dto = new TemporaryTimeOfDailyPerformanceDto();
			dto.setEmployeeId(employeeId());
			dto.setYmd(workingDate());
			dto.setWorkLeaveTime(workLeaveTime == null ? null : workLeaveTime.stream().map(t -> t.clone()).collect(Collectors.toList()));
		if (isHaveData()) {
			dto.exsistData();
		}
		return dto;
	}

	private static WorkLeaveTimeDto newWorkLeaveTime(TimeLeavingWork c) {
		return c == null ? null : new WorkLeaveTimeDto(c.getWorkNo().v(), WithActualTimeStampDto.toWithActualTimeStamp(c.getAttendanceStamp().orElse(null)),
				WithActualTimeStampDto.toWithActualTimeStamp(c.getLeaveStamp().orElse(null)));
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public TemporaryTimeOfDailyAttd toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		TemporaryTimeOfDailyPerformance domain = new TemporaryTimeOfDailyPerformance(emp,
						ConvertHelper.mapTo(workLeaveTime, (c) -> WorkLeaveTimeDto.toDomain(c)), date);
		return domain.getAttendance();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (path.equals(TIME_ZONE)) {
			return (List<T>) this.workLeaveTime;
		}
		return new ArrayList<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (path.equals(TIME_ZONE)) {
			this.workLeaveTime = (List<WorkLeaveTimeDto>) value;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return new WorkLeaveTimeDto();
		}
		return null;
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		return Optional.empty();
	}

	@Override
	public int size(String path) {
		return 10;
	}

	@Override
	public PropType typeOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}
}
