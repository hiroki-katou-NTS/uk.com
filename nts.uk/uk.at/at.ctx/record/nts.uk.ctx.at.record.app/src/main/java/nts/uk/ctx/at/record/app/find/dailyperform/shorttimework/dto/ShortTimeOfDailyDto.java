package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_SHORT_TIME_NAME)
public class ShortTimeOfDailyDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_SHORT_TIME_NAME; }
	/***/
	private static final long serialVersionUID = 1L;
	
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月日: 年月日 */
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	/** 時間帯: 短時間勤務時間帯 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME_ZONE, 
			indexField = DEFAULT_INDEX_FIELD_NAME, listMaxLength = 2, 
			enumField = DEFAULT_ENUM_FIELD_NAME, removeConflictEnum = true)
	private List<ShortWorkTimeSheetDto> shortWorkingTimeSheets;

	public static ShortTimeOfDailyDto getDto(ShortTimeOfDailyPerformance domain){
		ShortTimeOfDailyDto result = new ShortTimeOfDailyDto();
		if (domain != null) {
			result.setEmployeeId(domain.getEmployeeId());
			result.setYmd(domain.getYmd());
			result.setShortWorkingTimeSheets(ConvertHelper.mapTo(domain.getTimeZone().getShortWorkingTimeSheets(),
					(c) -> new ShortWorkTimeSheetDto(c.getShortWorkTimeFrameNo().v(), 
														c.getChildCareAttr() == null ? 0 : c.getChildCareAttr().value,
														c.getStartTime() == null ? null : c.getStartTime().valueAsMinutes(),
														c.getEndTime() == null ? null : c.getEndTime().valueAsMinutes())));
			result.exsistData();
		}
		return result;
	}
	public static ShortTimeOfDailyDto getDto(String employeeID,GeneralDate ymd,ShortTimeOfDailyAttd domain){
		ShortTimeOfDailyDto result = new ShortTimeOfDailyDto();
		if (domain != null) {
			result.setEmployeeId(employeeID);
			result.setYmd(ymd);
			result.setShortWorkingTimeSheets(ConvertHelper.mapTo(domain.getShortWorkingTimeSheets(),
					(c) -> new ShortWorkTimeSheetDto(c.getShortWorkTimeFrameNo().v(), 
														c.getChildCareAttr() == null ? 0 : c.getChildCareAttr().value,
														c.getStartTime() == null ? null : c.getStartTime().valueAsMinutes(),
														c.getEndTime() == null ? null : c.getEndTime().valueAsMinutes())));
			result.exsistData();
		}
		return result;
	}

	@Override
	public ShortTimeOfDailyDto clone(){
		ShortTimeOfDailyDto result = new ShortTimeOfDailyDto();
		result.setEmployeeId(employeeId());
		result.setYmd(workingDate());
		result.setShortWorkingTimeSheets(shortWorkingTimeSheets == null ? null 
				: shortWorkingTimeSheets.stream().map(t -> t.clone()).collect(Collectors.toList()));
		if (isHaveData()) {
			result.exsistData();
		}
		return result;
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
	public ShortTimeOfDailyAttd toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (emp == null) {
			emp = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		ShortTimeOfDailyPerformance domain = new ShortTimeOfDailyPerformance(emp, toTimeSheetDomain(), date);
		return domain.getTimeZone();
	}
	
	private List<ShortWorkingTimeSheet> toTimeSheetDomain() {
		if (shortWorkingTimeSheets == null) {
			return new ArrayList<>();
		}
		
		return shortWorkingTimeSheets.stream().filter(c -> c.getStartTime() != null && c.getEndTime() != null)
									.map(c -> new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(c.getNo()),
												c.getAttr() == ChildCareAtr.CHILD_CARE.value ? ChildCareAtr.CHILD_CARE : ChildCareAtr.CARE,
												createTimeWithDayAttr(c.getStartTime()), createTimeWithDayAttr(c.getEndTime())))
									.collect(Collectors.toList());
						
	}

	private TimeWithDayAttr createTimeWithDayAttr(Integer c) {
		return c == null ? TimeWithDayAttr.THE_PRESENT_DAY_0000 : new TimeWithDayAttr(c);
	}
	
	private AttendanceTime createAttendanceTime(Integer c) {
		return c == null ? AttendanceTime.ZERO : new AttendanceTime(c);
	}

	@Override
	public int size(String path) {
		return 2;
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public PropType typeOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return PropType.IDX_ENUM_LIST;
		}
		return super.typeOf(path);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (path.equals(TIME_ZONE)) {
			return (List<T>) this.shortWorkingTimeSheets;
		}
		return new ArrayList<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (path.equals(TIME_ZONE)) {
			this.shortWorkingTimeSheets = (List<ShortWorkTimeSheetDto>) value;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return new ShortWorkTimeSheetDto();
		}
		return null;
	}
}
