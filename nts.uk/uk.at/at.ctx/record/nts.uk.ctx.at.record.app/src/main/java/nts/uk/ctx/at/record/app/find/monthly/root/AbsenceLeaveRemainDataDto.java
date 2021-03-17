package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.RemainDataDaysMonth;

/** 振休月別残数データ */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_ABSENCE_LEAVE_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class AbsenceLeaveRemainDataDto extends MonthlyItemCommon {
	
	/***/
	private static final long serialVersionUID = 1L;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月: 年月 */
	private YearMonth ym;

	/** 締めID: 締めID */
	// @AttendanceItemValue
	// @AttendanceItemLayout(jpPropertyName = "締めID", layout = "A")
	private int closureID = 1;

	/** 締め日: 日付 */
	// @AttendanceItemLayout(jpPropertyName = "締め日", layout = "B")
	private ClosureDateDto closureDate;

	/** 締め期間: 期間 */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;

	/** 締め処理状態 */
	@AttendanceItemLayout(jpPropertyName = CLOSURE_STATE, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int closureStatus;
	
	/**	発生日数 */
	@AttendanceItemLayout(jpPropertyName = OCCURRENCE, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double occurredDay;
	
	/**	使用日数 */
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_D)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double usedDays;
	
	/**	残日数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_E)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double remainingDays;
	
	/**	繰越日数 */
	@AttendanceItemLayout(jpPropertyName = CARRY_FORWARD, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double carryforwardDays;
	
	/**	未消化日数 */
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double unUsedDays;
	
	@Override
	public String employeeId() {
		return employeeId;
	}
	@Override
	public AbsenceLeaveRemainData toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		return new AbsenceLeaveRemainData(employeeId, ym, closureID, 
				closureDate == null ? 1 : closureDate.getClosureDay(), 
				closureDate == null ? false : closureDate.getLastDayOfMonth(), 
				closureStatus == ClosureStatus.PROCESSED.value ? ClosureStatus.PROCESSED : ClosureStatus.UNTREATED,
				datePeriod == null ? null : datePeriod.getStart(), 
				datePeriod == null ? null : datePeriod.getEnd(), 
				occurredDay == null ? new RemainDataDaysMonth(0.0) : new RemainDataDaysMonth(occurredDay), 
				usedDays == null ? new RemainDataDaysMonth(0.0) : new RemainDataDaysMonth(usedDays),
				remainingDays == null ? new AttendanceDaysMonthToTal(0.0) : new AttendanceDaysMonthToTal(remainingDays), 
				carryforwardDays == null ? new AttendanceDaysMonthToTal(0.0) : new AttendanceDaysMonthToTal(carryforwardDays),
				unUsedDays == null ? new RemainDataDaysMonth(0.0) : new RemainDataDaysMonth(unUsedDays));
	}
	@Override
	public YearMonth yearMonth() {
		return ym;
	}
	
	public static AbsenceLeaveRemainDataDto from(AbsenceLeaveRemainData domain){
		AbsenceLeaveRemainDataDto dto = new AbsenceLeaveRemainDataDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getSId());
			dto.setYm(domain.getYm());
			dto.setClosureID(domain.getClosureId());
			dto.setClosureDate(new ClosureDateDto(domain.getClosureDay(), domain.isLastDayIs()));
			dto.setDatePeriod(new DatePeriodDto(domain.getStartDate(), domain.getEndDate()));
			dto.setClosureStatus(domain.getClosureStatus().value);
			dto.setOccurredDay(domain.getOccurredDay().v());
			dto.setUsedDays(domain.getUsedDays().v());
			dto.setRemainingDays(domain.getRemainingDays().v());
			dto.setCarryforwardDays(domain.getCarryforwardDays().v());
			dto.setUnUsedDays(domain.getUnUsedDays().v());
			dto.exsistData();
		}
		return dto;
	}
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case CLOSURE_STATE:
			return Optional.of(ItemValue.builder().value(closureStatus).valueType(ValueType.ATTR));
		case OCCURRENCE:
			return Optional.of(ItemValue.builder().value(occurredDay).valueType(ValueType.DAYS));
		case USAGE:
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.DAYS));
		case REMAIN:
			return Optional.of(ItemValue.builder().value(remainingDays).valueType(ValueType.DAYS));
		case CARRY_FORWARD:
			return Optional.of(ItemValue.builder().value(carryforwardDays).valueType(ValueType.DAYS));
		case NOT_DIGESTION:
			return Optional.of(ItemValue.builder().value(unUsedDays).valueType(ValueType.DAYS));
		default:
			break;
		}
		return super.valueOf(path);
	}
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (PERIOD.equals(path)) {
			return new DatePeriodDto();
		}
		return super.newInstanceOf(path);
	}
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (PERIOD.equals(path)) {
			return Optional.ofNullable(datePeriod);
		}
		return super.get(path);
	}
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case CLOSURE_STATE:
		case OCCURRENCE:
		case USAGE:
		case REMAIN:
		case CARRY_FORWARD:
		case NOT_DIGESTION:
			return PropType.VALUE;
		default:
			break;
		}
		return super.typeOf(path);
	}
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case CLOSURE_STATE:
			closureStatus = value.valueOrDefault(0); break;
		case OCCURRENCE:
			occurredDay = value.valueOrDefault(null); break;
		case USAGE:
			usedDays = value.valueOrDefault(null); break;
		case REMAIN:
			remainingDays = value.valueOrDefault(null); break;
		case CARRY_FORWARD:
			carryforwardDays = value.valueOrDefault(null); break;
		case NOT_DIGESTION:
			unUsedDays = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (PERIOD.equals(path)) {
			datePeriod = (DatePeriodDto) value;
		}
	}
	@Override
	public boolean isRoot() {
		return true;
	}
	@Override
	public String rootName() {
		return MONTHLY_ABSENCE_LEAVE_REMAIN_NAME;
	}
	
	
}
