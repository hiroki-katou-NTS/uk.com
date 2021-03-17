package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

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
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_ATTENDACE_LEAVE_NAME)
public class TimeLeavingOfDailyPerformanceDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_ATTENDACE_LEAVE_NAME; }
	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;

	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME_ZONE, 
			listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<WorkLeaveTimeDto> workAndLeave;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = COUNT)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer workTimes;

	public static TimeLeavingOfDailyPerformanceDto getDto(TimeLeavingOfDailyPerformance domain) {
		TimeLeavingOfDailyPerformanceDto dto = new TimeLeavingOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(domain.getAttendance().getWorkTimes() != null ? domain.getAttendance().getWorkTimes().v() : null);
			dto.setWorkAndLeave(ConvertHelper.mapTo(domain.getAttendance().getTimeLeavingWorks(),
					(c) -> new WorkLeaveTimeDto(c.getWorkNo().v(),
							WithActualTimeStampDto.toWithActualTimeStamp(c.getAttendanceStamp().orElse(null)),
							WithActualTimeStampDto.toWithActualTimeStamp(c.getLeaveStamp().orElse(null)))));
			dto.exsistData();
		}
		return dto;
	}
	
	public static TimeLeavingOfDailyPerformanceDto getDto(String employeeID,GeneralDate ymd,TimeLeavingOfDailyAttd domain) {
		TimeLeavingOfDailyPerformanceDto dto = new TimeLeavingOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(employeeID);
			dto.setYmd(ymd);
			dto.setWorkTimes(domain.getWorkTimes() != null ? domain.getWorkTimes().v() : null);
			dto.setWorkAndLeave(ConvertHelper.mapTo(domain.getTimeLeavingWorks(),
					(c) -> new WorkLeaveTimeDto(c.getWorkNo().v(),
							WithActualTimeStampDto.toWithActualTimeStamp(c.getAttendanceStamp().orElse(null)),
							WithActualTimeStampDto.toWithActualTimeStamp(c.getLeaveStamp().orElse(null)))));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public TimeLeavingOfDailyPerformanceDto clone() {
		TimeLeavingOfDailyPerformanceDto dto = new TimeLeavingOfDailyPerformanceDto();
		dto.setEmployeeId(employeeId());
		dto.setYmd(workingDate());
		dto.setWorkTimes(workTimes);
		dto.setWorkAndLeave(workAndLeave == null ? null : workAndLeave.stream().map(t -> t.clone()).collect(Collectors.toList()));
		if (isHaveData()) {
			dto.exsistData();
		}
		return dto;
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
	public TimeLeavingOfDailyAttd toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (emp == null) {
			emp = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		TimeLeavingOfDailyPerformance domain =  new TimeLeavingOfDailyPerformance(emp, new WorkTimes(toWorkTime()),
				ConvertHelper.mapTo(workAndLeave, c -> WorkLeaveTimeDto.toDomain(c)), date);
		return domain.getAttendance();
	}

	private int toWorkTime() {
		return workTimes == null ? (workAndLeave == null ? 0 : workAndLeave.size()) : (workTimes);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (path.equals(TIME_ZONE)) {
			return (List<T>) this.workAndLeave;
		}
		return new ArrayList<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (path.equals(TIME_ZONE)) {
			this.workAndLeave = (List<WorkLeaveTimeDto>) value;
		}
	}

	@Override
	public boolean isRoot() { return true; }

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return new WorkLeaveTimeDto();
		}
		return null;
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		
		if (path.equals(COUNT)) {
			return Optional.of(ItemValue.builder().value(workTimes).valueType(ValueType.COUNT));
		}
		
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (path.equals(COUNT)) {
			this.workTimes = value.valueOrDefault(null);
		}
	}

	@Override
	public int size(String path) {
		return 2;
	}

	@Override
	public PropType typeOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return PropType.IDX_LIST;
		}
		if (path.equals(COUNT)) {
			return PropType.VALUE;
		}
		return super.typeOf(path);
	}
}
