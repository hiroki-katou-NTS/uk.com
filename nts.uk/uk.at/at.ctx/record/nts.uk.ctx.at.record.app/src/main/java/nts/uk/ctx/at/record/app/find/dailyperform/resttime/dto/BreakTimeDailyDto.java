package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_BREAK_TIME_NAME)
public class BreakTimeDailyDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_BREAK_TIME_NAME; }
	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;
	
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = FAKED, 
			listMaxLength = 2, enumField = DEFAULT_ENUM_FIELD_NAME, listNoIndex = true)
	private List<DailyBreakDto> breakTimes = new ArrayList<>();
		
	public static BreakTimeDailyDto getDto(List<BreakTimeOfDailyPerformance> domain) {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		if(domain != null && !domain.isEmpty()){
			dto.setEmployeeId(domain.get(0).getEmployeeId());
			dto.setYmd(domain.get(0).getYmd());
			dto.setBreakTimes(domain.stream().map(tz -> new DailyBreakDto(ConvertHelper.mapTo(tz.getTimeZone().getBreakTimeSheets(), 
					(c) -> new TimeSheetDto(
							c.getBreakFrameNo().v().intValue(),
							getTimeStamp(c.getStartTime()),
							getTimeStamp(c.getEndTime()),
							c.getBreakTime() == null ? 0 : c.getBreakTime().valueAsMinutes())),
					tz.getTimeZone().getBreakType() == null ? 0 : tz.getTimeZone().getBreakType().value)).collect(Collectors.toList()));
//			dto.setAttr(x.getBreakType() == null ? 0 : x.getBreakType().value);
//			dto.setTimeZone(ConvertHelper.mapTo(x.getBreakTimeSheets(), 
//													(c) -> new TimeSheetDto(
//														c.getBreakFrameNo().v().intValue(),
//														getTimeStamp(c.getStartTime()),
//														getTimeStamp(c.getEndTime()),
//														c.getBreakTime() == null ? 0 : c.getBreakTime().valueAsMinutes())));
			dto.exsistData();
		}
		return dto;
	}
	
	public static BreakTimeDailyDto getDto(String employeeID, GeneralDate ymd, List<BreakTimeOfDailyAttd> domain) {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		if(domain != null && !domain.isEmpty()){
			dto.setEmployeeId(employeeID);
			dto.setYmd(ymd);
			dto.setBreakTimes(domain.stream().map(tz -> new DailyBreakDto(ConvertHelper.mapTo(tz.getBreakTimeSheets(), 
					(c) -> new TimeSheetDto(
							c.getBreakFrameNo().v().intValue(),
							getTimeStamp(c.getStartTime()),
							getTimeStamp(c.getEndTime()),
							c.getBreakTime() == null ? 0 : c.getBreakTime().valueAsMinutes())),
					tz.getBreakType() == null ? 0 : tz.getBreakType().value)).collect(Collectors.toList()));
			dto.exsistData();
		}
		return dto;
	}
	
//ichiokaDEL
//	public static BreakTimeDailyDto getDto(String employeeID,GeneralDate ymd,BreakTimeOfDailyAttd x) {
//		BreakTimeDailyDto dto = new BreakTimeDailyDto();
//		if(x != null){
//			dto.setEmployeeId(employeeID);
//			dto.setYmd(ymd);
//			dto.setAttr(x.getBreakType() == null ? 0 : x.getBreakType().value);
////			dto.setTimeZone(ConvertHelper.mapTo(x.getBreakTimeSheets(), 
////													(c) -> new TimeSheetDto(
////														c.getBreakFrameNo().v().intValue(),
////														getTimeStamp(c.getStartTime()),
////														getTimeStamp(c.getEndTime()),
////														c.getBreakTime() == null ? 0 : c.getBreakTime().valueAsMinutes())));
//			dto.setBreakTimes(new DailyBreakDto(ConvertHelper.mapTo(x.getBreakTimeSheets(), 
//					(c) -> new TimeSheetDto(
//							c.getBreakFrameNo().v().intValue(),
//							getTimeStamp(c.getStartTime()),
//							getTimeStamp(c.getEndTime()),
//							c.getBreakTime() == null ? 0 : c.getBreakTime().valueAsMinutes()),
//					tz.getTimeZone().getBreakType() == null ? 0 : tz.getTimeZone().getBreakType().value)));
//			dto.exsistData();
//		}
//		return dto;
//	}
	
	@Override
	public BreakTimeDailyDto clone() {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		dto.setEmployeeId(employeeId());
		dto.setYmd(workingDate());
		dto.setBreakTimes(breakTimes.stream().map(c -> c.clone()).collect(Collectors.toList()));
		if(isHaveData()){
			dto.exsistData();
		}
		return dto;
	}
	
	private static TimeStampDto getTimeStamp(TimeWithDayAttr c) {
		return c == null ? null : new TimeStampDto(c.valueAsMinutes(), null, null, 0);
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}
	
//	@Override
//	public List<BreakTimeOfDailyPerformance> toDomain(String emp, GeneralDate date) {
//		if(!this.isHaveData()) {
//			return null;
//		}
//		String empId = emp == null ? this.employeeId() : emp;
//		GeneralDate ymd = date == null ? this.workingDate() : date;
//		
//		return breakTimes.stream().map(c -> new BreakTimeOfDailyPerformance(empId,
//				c.getAttr() == BreakType.REFER_SCHEDULE.value ? BreakType.REFER_SCHEDULE : BreakType.REFER_WORK_TIME,
//				c.getTimeZone().stream().filter(tz -> judgNotNull(tz)).map(tz -> toTimeSheet(tz)).collect(Collectors.toList()),
//				ymd)).collect(Collectors.toList());
//	}
	
	@Override
	public List<BreakTimeOfDailyAttd> toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		String empId = emp == null ? this.employeeId() : emp;
		GeneralDate ymd = date == null ? this.workingDate() : date;
		
		return breakTimes.stream()
				.map(c -> new BreakTimeOfDailyAttd(
						c.getAttr() == BreakType.REFER_SCHEDULE.value ? BreakType.REFER_SCHEDULE : BreakType.REFER_WORK_TIME,
						c.getTimeZone().stream()
								.filter(tz -> judgNotNull(tz))
								.map(tz -> toTimeSheet(tz))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
	}
	
	private boolean judgNotNull(TimeSheetDto d){
		return d != null && ((d.getEnd() != null && d.getEnd().getTimesOfDay() != null) || (d.getStart() != null && d.getStart().getTimesOfDay() != null));
	}
	
	private BreakTimeSheet toTimeSheet(TimeSheetDto d){
		return new BreakTimeSheet(new BreakFrameNo(d.getNo()),
				createWorkStamp(d.getStart()),
				createWorkStamp(d.getEnd()),
				new AttendanceTime(d.getBreakTime()));
	}

	private TimeWithDayAttr createWorkStamp(TimeStampDto d) {
		return d == null || d.getTimesOfDay() == null ? null : new TimeWithDayAttr(d.getTimesOfDay());
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(FAKED)) {
			return new DailyBreakDto();
		}
		return super.newInstanceOf(path);
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (path.equals(FAKED)) {
			return (List<T>) this.breakTimes;
		}
		
		return super.gets(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		
		if (path.equals(FAKED)) {
			this.breakTimes = (List<DailyBreakDto>) value;
		}
	}
	
	@Override
	public int size(String path) {
		if (path.equals(FAKED)) {
			return 2;
		}
		return 0;
	}

	@Override
	public PropType typeOf(String path) {
		if (!path.equals(FAKED)) {
			return PropType.OBJECT;
		}
		return PropType.ENUM_HAVE_IDX;
	}
}
