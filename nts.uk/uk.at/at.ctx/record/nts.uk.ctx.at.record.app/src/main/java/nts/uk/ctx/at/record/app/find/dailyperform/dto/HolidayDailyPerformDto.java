package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TransferHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.YearlyReservedOfDaily;

/** 日別実績の休暇 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 年休: 日別実績の年休 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ANNUNAL_LEAVE)
	private HolidayUseTimeDto annualLeave;

	/** 特別休暇: 日別実績の特別休暇 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = SPECIAL)
	private HolidayUseTimeDto specialHoliday;

	/** 超過有休: 日別実績の超過有休 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = EXCESS)
	private HolidayUseTimeDto excessSalaries;

	/** 代休: 日別実績の代休 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = COMPENSATORY)
	private HolidayUseTimeDto compensatoryLeave;

	/** 積立年休: 日別実績の積立年休 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = RETENTION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer retentionYearly;

	/** 時間消化休暇: 日別実績の時間消化休暇 */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = TIME_DIGESTION)
	private TimeDigestionVacationDailyPerformDto timeDigestionVacation;

	/** 欠勤: 日別実績の欠勤 */
	@AttendanceItemLayout(layout = LAYOUT_G, jpPropertyName = ABSENCE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer absence;

	/** 振休 */
	@AttendanceItemLayout(layout = LAYOUT_G, jpPropertyName = TRANSFER_HOLIDAY)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer transferHoliday;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case ANNUNAL_LEAVE:
		case (SPECIAL):
		case EXCESS:
		case COMPENSATORY:
			return new HolidayUseTimeDto();
		case (TIME_DIGESTION):
			return new TimeDigestionVacationDailyPerformDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (ANNUNAL_LEAVE):
			return Optional.ofNullable(annualLeave);
		case (SPECIAL):
			return Optional.ofNullable(specialHoliday);
		case (EXCESS):
			return Optional.ofNullable(excessSalaries);
		case (COMPENSATORY):
			return Optional.ofNullable(compensatoryLeave);
		case (TIME_DIGESTION):
			return Optional.ofNullable(timeDigestionVacation);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (ANNUNAL_LEAVE):
			annualLeave = (HolidayUseTimeDto) value;
			break;
		case (SPECIAL):
			specialHoliday = (HolidayUseTimeDto) value;
			break;
		case (EXCESS):
			excessSalaries = (HolidayUseTimeDto) value;
			break;
		case (COMPENSATORY):
			compensatoryLeave = (HolidayUseTimeDto) value;
			break;
		case (TIME_DIGESTION):
			timeDigestionVacation = (TimeDigestionVacationDailyPerformDto) value;
		default:
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case RETENTION:
			return Optional.of(ItemValue.builder().value(retentionYearly).valueType(ValueType.TIME));
		case ABSENCE:
			return Optional.of(ItemValue.builder().value(absence).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case RETENTION:
		case ABSENCE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case RETENTION:
			retentionYearly = value.valueOrDefault(null);
			break;
		case ABSENCE:
			absence = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public HolidayDailyPerformDto clone() {
		return new HolidayDailyPerformDto(annualLeave == null ? null : annualLeave.clone(), 
											specialHoliday == null ? null : specialHoliday.clone(), 
											excessSalaries == null ? null : excessSalaries.clone(), 
											compensatoryLeave == null ? null : compensatoryLeave.clone(), 
											retentionYearly, 
											timeDigestionVacation == null ? null : timeDigestionVacation.clone(), 
											absence, transferHoliday);
	}
	
	public static HolidayDailyPerformDto from(HolidayOfDaily domain) {
		return domain == null ? null : 
			new HolidayDailyPerformDto(HolidayUseTimeDto.from(domain.getAnnual()), 
					HolidayUseTimeDto.from(domain.getSpecialHoliday()), 
					HolidayUseTimeDto.from(domain.getOverSalary()), 
					HolidayUseTimeDto.from(domain.getSubstitute()), 
					domain.getYearlyReserved() == null ? null : fromTime(domain.getYearlyReserved().getUseTime()), 
					TimeDigestionVacationDailyPerformDto.from(domain.getTimeDigest()), 
					domain.getAbsence() == null ? null : fromTime(domain.getAbsence().getUseTime()),
					domain.getTransferHoliday() == null ? null : fromTime(domain.getTransferHoliday().getUseTime()));
	}

	public HolidayOfDaily toDomain() {
		return new HolidayOfDaily(new AbsenceOfDaily(absence == null ? AttendanceTime.ZERO : new AttendanceTime(absence)),
				timeDigestionVacation == null ? TimeDigestionVacationDailyPerformDto.defaultDomain() : timeDigestionVacation.toDomain(),
				new YearlyReservedOfDaily(retentionYearly == null ? AttendanceTime.ZERO : new AttendanceTime(retentionYearly)),
				compensatoryLeave == null ? new SubstituteHolidayOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO) 
						: compensatoryLeave.toSubstituteHoliday(),
				excessSalaries == null ? new OverSalaryOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO) : excessSalaries.toOverSalary(), 
				specialHoliday == null ? new SpecialHolidayOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO) : specialHoliday.toSpecialHoliday(),
				annualLeave == null ? new AnnualOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO) : annualLeave.toAnnualOfDaily(),
				new TransferHolidayOfDaily(new AttendanceTime(transferHoliday == null ? 0 : transferHoliday)));
	}
	
	public static HolidayOfDaily defaulDomain() {
		return new HolidayOfDaily(new AbsenceOfDaily(AttendanceTime.ZERO),
									TimeDigestionVacationDailyPerformDto.defaultDomain(),
									new YearlyReservedOfDaily(AttendanceTime.ZERO),
									new SubstituteHolidayOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO) ,
									new OverSalaryOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO), 
									new SpecialHolidayOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO),
									new AnnualOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO),
									new TransferHolidayOfDaily(AttendanceTime.ZERO));
	}
	
	private static Integer fromTime(AttendanceTime time) {
		return time == null ? null : time.valueAsMinutes();
	}
}
