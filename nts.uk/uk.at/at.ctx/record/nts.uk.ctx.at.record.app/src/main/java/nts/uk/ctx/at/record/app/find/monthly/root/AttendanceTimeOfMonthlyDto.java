package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ExcessOutsideWorkOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.MonthlyCalculationDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.OuenTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.TotalCountByPeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.VerticalTotalOfMonthlyDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.OuenTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** ??????????????????????????? */
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_ATTENDANCE_TIME_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class AttendanceTimeOfMonthlyDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
	protected long version;
	
	/** ??????ID */
	protected String companyId;

	/** ??????ID: ??????ID */
	protected String employeeId;

	/** ??????: ?????? */
	protected YearMonth ym;

	/** ??????ID: ??????ID */
	// @AttendanceItemValue
	// @AttendanceItemLayout(jpPropertyName = "??????ID", layout = "A")
	protected int closureID = 1;

	/** ?????????: ?????? */
	// @AttendanceItemLayout(jpPropertyName = "?????????", layout = "B")
	protected ClosureDateDto closureDate;

	/** ??????: ?????? */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_C)
	protected DatePeriodDto datePeriod;

	/** ????????????: ??????????????????????????? */
	@AttendanceItemLayout(jpPropertyName = CALC, layout = LAYOUT_D)
	protected MonthlyCalculationDto monthlyCalculation;

	/** ???????????????: ?????????????????????????????? */
	@AttendanceItemLayout(jpPropertyName = EXCESS, layout = LAYOUT_E)
	protected ExcessOutsideWorkOfMonthlyDto excessOutsideWork;

	/** ????????????: ?????????????????? */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = AGGREGATE + DAYS, layout = LAYOUT_F)
	protected double aggregateDays;

	/** ??????: ?????????????????? */
	@AttendanceItemLayout(jpPropertyName = VERTICAL_TOTAL, layout = LAYOUT_G)
	protected VerticalTotalOfMonthlyDto verticalTotal;

	/** ????????????: ???????????????????????? */
	@AttendanceItemLayout(jpPropertyName = COUNT + AGGREGATE, layout = LAYOUT_H)
	protected TotalCountByPeriodDto totalCount;
	
	@AttendanceItemLayout(jpPropertyName = OUEN, layout = LAYOUT_I)
	protected OuenTimeOfMonthlyDto ouen;

	@Override
	public String employeeId() {
		return this.employeeId;
	}
	
	public static AttendanceTimeOfMonthlyDto from(AttendanceTimeOfMonthly domain) {
		AttendanceTimeOfMonthlyDto dto = new AttendanceTimeOfMonthlyDto();
		if(domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
			dto.setClosureDate(domain.getClosureDate() == null ? null : ClosureDateDto.from(domain.getClosureDate()));
			dto.setDatePeriod(domain.getDatePeriod() == null ? null 
					: new DatePeriodDto(domain.getDatePeriod().start(), domain.getDatePeriod().end()));
//			dto.setAggregateTimes(aggregateTimes);
			dto.setMonthlyCalculation(MonthlyCalculationDto.from(domain.getMonthlyCalculation()));
			dto.setExcessOutsideWork(ExcessOutsideWorkOfMonthlyDto.from(domain.getExcessOutsideWork()));
			dto.setAggregateDays(domain.getAggregateDays() == null ? 0 : domain.getAggregateDays().v());
			dto.setVerticalTotal(VerticalTotalOfMonthlyDto.from(domain.getVerticalTotal()));
			dto.setTotalCount(TotalCountByPeriodDto.from(domain.getTotalCount()));
			dto.setVersion(domain.getVersion());
			dto.setOuen(OuenTimeOfMonthlyDto.from(domain.getOuenTime()));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public AttendanceTimeOfMonthly toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		if(!this.isHaveData()) {
			return null;
		}
		if(employeeId == null){
			employeeId = this.employeeId;
		} 
		if(ym == null){
			ym = this.ym;
		} else {
			if(datePeriod == null){
				datePeriod = new DatePeriodDto(GeneralDate.ymd(ym.year(), ym.month(), 1), 
						GeneralDate.ymd(ym.year(), ym.month(), ym.lastDateInMonth()));
			}
		}
		if(closureDate == null){
			closureDate = this.closureDate;
		}
		AttendanceTimeOfMonthly domain = AttendanceTimeOfMonthly.of(employeeId, ym, ConvertHelper.getEnum(closureID, ClosureId.class), 
																	closureDate == null ? null : closureDate.toDomain(), 
																	datePeriod == null ? null : new DatePeriod(datePeriod.getStart(), datePeriod.getEnd()), 
																	monthlyCalculation == null ? new MonthlyCalculation() : monthlyCalculation.toDomain(), 
																	excessOutsideWork == null ? new ExcessOutsideWorkOfMonthly() : excessOutsideWork.toDomain(), 
																	verticalTotal == null ? new VerticalTotalOfMonthly() : verticalTotal.toDomain(),
																	this.totalCount == null ? new TotalCountByPeriod() : this.totalCount.toDomain(),
																	new AttendanceDaysMonth(aggregateDays),
																	ouen == null ? OuenTimeOfMonthly.empty() : ouen.domain());
		domain.setVersion(this.version);
		
		return domain;
	}

	@Override
	public YearMonth yearMonth() {
		return this.ym;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if ((AGGREGATE + DAYS).equals(path)) {
			return Optional.of(ItemValue.builder().value(aggregateDays).valueType(ValueType.DAYS));
		}
		return super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case PERIOD:
			return new DatePeriodDto();
		case CALC:
			return new MonthlyCalculationDto();
		case EXCESS:
			return new ExcessOutsideWorkOfMonthlyDto();
		case VERTICAL_TOTAL:
			return new VerticalTotalOfMonthlyDto();
		case (COUNT + AGGREGATE):
			return new TotalCountByPeriodDto();
		case OUEN:
			return new OuenTimeOfMonthlyDto();
		default:
			break;
		}
		return super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case PERIOD:
			return Optional.ofNullable(datePeriod);
		case CALC:
			return Optional.ofNullable(monthlyCalculation);
		case EXCESS:
			return Optional.ofNullable(excessOutsideWork);
		case VERTICAL_TOTAL:
			return Optional.ofNullable(verticalTotal);
		case (COUNT + AGGREGATE):
			return Optional.ofNullable(totalCount);
		case OUEN:
			return Optional.ofNullable(ouen);
		default:
			break;
		}
		return super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		if ((AGGREGATE + DAYS).equals(path)) {
			return PropType.VALUE;
		}
		return super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if ((AGGREGATE + DAYS).equals(path)) {
			aggregateDays = value.valueOrDefault(0d);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case PERIOD:
			datePeriod = (DatePeriodDto) value; break;
		case CALC:
			monthlyCalculation = (MonthlyCalculationDto) value; break;
		case EXCESS:
			excessOutsideWork = (ExcessOutsideWorkOfMonthlyDto) value; break;
		case VERTICAL_TOTAL:
			verticalTotal = (VerticalTotalOfMonthlyDto) value; break;
		case (COUNT + AGGREGATE):
			totalCount = (TotalCountByPeriodDto) value; break;
		case OUEN:
			ouen = (OuenTimeOfMonthlyDto) value; break;
		default:
			break;
		}
	}

	@Override
	public boolean isRoot() {
		return true;
	}

	@Override
	public String rootName() {
		return MONTHLY_ATTENDANCE_TIME_NAME;
	}
}
