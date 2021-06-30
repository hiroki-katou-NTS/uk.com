package nts.uk.ctx.at.record.app.find.monthly.week;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ExcessOutsideWorkOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AnyItemByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.WeeklyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@NoArgsConstructor
/** 週別実績の勤怠時間 */
@EqualsAndHashCode(callSuper = false)
public class AttendanceTimeOfWeeklyDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 週NO */
	private int weekNo;
	
	private WeeklyAttendanceTimeDto attendanceItem;

	private AnyItemOfMonthlyDto anyItem;
	
	private ExcessOutsideWorkOfMonthlyDto excessOutsideWork;
	
	public static AttendanceTimeOfWeeklyDto from(AttendanceTimeOfWeekly domain, Map<Integer, OptionalItem> master) {
		AttendanceTimeOfWeeklyDto dto = new AttendanceTimeOfWeeklyDto();
		if(domain != null) {
			dto.setWeekNo(domain.getWeekNo());
			dto.setAnyItem(AnyItemOfMonthlyDto.from(domain.getAnyItem(), master));
			dto.setAttendanceItem(WeeklyAttendanceTimeDto.from(domain));
			dto.setExcessOutsideWork(ExcessOutsideWorkOfMonthlyDto.from(domain.getExcessOutside()));
			dto.exsistData();
		}
		return dto;
	}

	public Optional<AttendanceTimeOfWeekly> toDomain() {
		
		if (!attendanceItem.isHaveData()) {
			return Optional.empty();
		}
		
		AttendanceTimeOfWeekly domain = AttendanceTimeOfWeekly.of(
				this.attendanceItem.getEmployeeId(), 
				this.attendanceItem.getYm(), 
				EnumAdaptor.valueOf(this.attendanceItem.getClosureID(), ClosureId.class), 
				this.attendanceItem.getClosureDate().toDomain(), 
				weekNo, 
				this.attendanceItem.getDatePeriod().toDomain(), 
				this.attendanceItem.getMonthlyCalculation() == null ? new WeeklyCalculation(): this.attendanceItem.getMonthlyCalculation().toDomainWeekly(), 
				this.excessOutsideWork == null ? new ExcessOutsideByPeriod() : this.excessOutsideWork.toDomainPeriod(), 
				this.attendanceItem.getVerticalTotal() == null ? new VerticalTotalOfMonthly() : this.attendanceItem.getVerticalTotal().toDomain(), 
				this.attendanceItem.getTotalCount() == null ? new TotalCountByPeriod() : this.attendanceItem.getTotalCount().toDomain(), 
				this.anyItem == null ? new AnyItemByPeriod() : this.anyItem.toDomainPeriod(), 
				new AttendanceDaysMonth(this.attendanceItem.getAggregateDays()));
		domain.setVersion(this.attendanceItem.getVersion());
		
		return Optional.of(domain);
	}

	@Override
	public String employeeId() {
		return this.attendanceItem.getEmployeeId();
	}

	@Override
	public Object toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		return toDomain();
	}

	@Override
	public YearMonth yearMonth() {
		return this.attendanceItem.getYm();
	}

	@Override
	public int getClosureID() {
		return this.attendanceItem.getClosureID();
	}

	@Override
	public ClosureDateDto getClosureDate() {
		return this.attendanceItem.getClosureDate();
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case MONTHLY_ATTENDANCE_TIME_NAME:
			return Optional.ofNullable(this.attendanceItem);
		case MONTHLY_OPTIONAL_ITEM_NAME:
			return Optional.ofNullable(this.anyItem);
		case WEEKLY_ATTENDANCE_TIME_EXESS:
			return Optional.ofNullable(this.excessOutsideWork);
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case MONTHLY_ATTENDANCE_TIME_NAME:
			this.attendanceItem = (WeeklyAttendanceTimeDto) value;
			break;
		case MONTHLY_OPTIONAL_ITEM_NAME:
			this.anyItem = (AnyItemOfMonthlyDto) value;
			break;
		case WEEKLY_ATTENDANCE_TIME_EXESS:
			this.excessOutsideWork = (ExcessOutsideWorkOfMonthlyDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case MONTHLY_ATTENDANCE_TIME_NAME:
			return new WeeklyAttendanceTimeDto();
		case MONTHLY_OPTIONAL_ITEM_NAME:
			return new AnyItemOfMonthlyDto();
		case WEEKLY_ATTENDANCE_TIME_EXESS:
			return new ExcessOutsideWorkOfMonthlyDto();
		default:
			return null;
		}
	}
	
	@Override
	public boolean isRoot() { return true; }
	
	@Override
	public boolean isContainer() { return true; }

//	@Override
//	public String rootName() {
//		return WEEKLY_ATTENDANCE_TIME_NAME;
//	}
}
