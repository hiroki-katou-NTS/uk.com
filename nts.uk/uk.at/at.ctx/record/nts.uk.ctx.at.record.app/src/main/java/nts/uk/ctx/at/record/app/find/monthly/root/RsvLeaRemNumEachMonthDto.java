package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ReserveLeaveDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveGrant;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** ????????????????????????????????? */
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_RESERVE_LEAVING_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class RsvLeaRemNumEachMonthDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;

	/** ??????ID */
	private String companyId;

	/** ??????ID: ??????ID */
	private String employeeId;

	/** ??????: ?????? */
	private YearMonth ym;

	/** ??????ID: ??????ID */
	private int closureID = 1;

	/** ?????????: ?????? */
	private ClosureDateDto closureDate;

	/** ????????????: ?????? */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;

	/** ?????????????????? */
	@AttendanceItemValue(type = ValueType.ATTR)
	@AttendanceItemLayout(jpPropertyName = CLOSURE_STATE, layout = LAYOUT_B)
	private int closureStatus;

	/** ???????????? */
	@AttendanceItemLayout(jpPropertyName = RETENTION, layout = LAYOUT_C)
	private ReserveLeaveDto reserveLeave;

	/** ??????????????? */
	@AttendanceItemLayout(jpPropertyName = REAL + RETENTION, layout = LAYOUT_D)
	private ReserveLeaveDto realReserveLeave;

	/** ???????????????????????? */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = GRANT + INFO, layout = LAYOUT_E)
	private double reserveLeaveGrant;

	/** ???????????? */
	@AttendanceItemLayout(jpPropertyName = GRANT + ATTRIBUTE, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.FLAG)
	private boolean grantAtr;

	/** ???????????? */
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.DAYS)
	private double undigestedNumber;

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	public static RsvLeaRemNumEachMonthDto from(RsvLeaRemNumEachMonth domain) {
		RsvLeaRemNumEachMonthDto dto = new RsvLeaRemNumEachMonthDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
			dto.setClosureDate(domain.getClosureDate() == null ? null : ClosureDateDto.from(domain.getClosureDate()));
			dto.setDatePeriod(DatePeriodDto.from(domain.getClosurePeriod()));
			dto.setClosureStatus(domain.getClosureStatus().value);
			dto.setReserveLeave(ReserveLeaveDto.from(domain.getReserveLeave()));
			dto.setRealReserveLeave(ReserveLeaveDto.from(domain.getRealReserveLeave()));
			domain.getReserveLeaveGrant().ifPresent(g -> {
				dto.setReserveLeaveGrant(g.getGrantDays().v());
			});
			dto.setGrantAtr(domain.isGrantAtr());
			dto.setUndigestedNumber(domain.getUndigestedNumber().getUndigestedDays().v());
			dto.exsistData();
			dto.setUndigestedNumber(domain.getUndigestedNumber().getUndigestedDays().v());
		}
		return dto;
	}

	@Override
	public RsvLeaRemNumEachMonth toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		if(!this.isHaveData()) {
			return null;
		}
		if(employeeId == null){
			employeeId = this.employeeId;
		}
		if(ym == null){
			ym = this.ym;
		}else {
			if(datePeriod == null){
				datePeriod = new DatePeriodDto(GeneralDate.ymd(ym.year(), ym.month(), 1),
						GeneralDate.ymd(ym.year(), ym.month(), ym.lastDateInMonth()));
			}
		}
		if(closureDate == null){
			closureDate = this.closureDate;
		}

		return RsvLeaRemNumEachMonth.of(employeeId, ym, ConvertHelper.getEnum(closureID, ClosureId.class),
				closureDate == null ? null : closureDate.toDomain(), datePeriod == null ? null : datePeriod.toDomain(),
				closureStatus == ClosureStatus.PROCESSED.value ? ClosureStatus.PROCESSED : ClosureStatus.UNTREATED,
				reserveLeave == null ? new ReserveLeave() : reserveLeave.toDomain(),
				realReserveLeave == null ? new ReserveLeave() : realReserveLeave.toDomain(),
				Optional.of(ReserveLeaveGrant.of(new ReserveLeaveGrantDayNumber(reserveLeaveGrant))),
				grantAtr, undigestedNumber);
	}

	@Override
	public YearMonth yearMonth() {
		return this.ym;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case CLOSURE_STATE:
			return Optional.of(ItemValue.builder().value(closureStatus).valueType(ValueType.ATTR));
		case (GRANT + INFO):
			return Optional.of(ItemValue.builder().value(reserveLeaveGrant).valueType(ValueType.DAYS));
		case (GRANT + ATTRIBUTE):
			return Optional.of(ItemValue.builder().value(grantAtr).valueType(ValueType.FLAG));
		case NOT_DIGESTION:
			return Optional.of(ItemValue.builder().value(undigestedNumber).valueType(ValueType.DAYS));
		default:
			break;
		}
		return super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case PERIOD:
			return new DatePeriodDto();
		case RETENTION:
		case (REAL + RETENTION):
			return new ReserveLeaveDto();
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
		case RETENTION:
			return Optional.ofNullable(reserveLeave);
		case (REAL + RETENTION):
			return Optional.ofNullable(realReserveLeave);
		default:
			break;
		}
		return super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case CLOSURE_STATE:
		case (GRANT + INFO):
		case (GRANT + ATTRIBUTE):
		case NOT_DIGESTION:
			return PropType.VALUE;
		case PERIOD:
		case RETENTION:
		case (REAL + RETENTION):
			return PropType.OBJECT;
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
		case (GRANT + INFO):
			(reserveLeaveGrant) = value.valueOrDefault(0d); break;
		case (GRANT + ATTRIBUTE):
			(grantAtr) = value.valueOrDefault(false); break;
		case NOT_DIGESTION:
			(undigestedNumber) = value.valueOrDefault(0d); break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case PERIOD:
			datePeriod = (DatePeriodDto) value; break;
		case RETENTION:
			reserveLeave = (ReserveLeaveDto) value; break;
		case (REAL + RETENTION):
			realReserveLeave = (ReserveLeaveDto) value; break;
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
		return MONTHLY_RESERVE_LEAVING_REMAIN_NAME;
	}

	
}
