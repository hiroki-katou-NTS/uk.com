package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave.SpecialLeaveDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave.SpecialLeaveUnDigestionDataDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUnDigestion;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 特別休暇残数月別データ */
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_SPECIAL_HOLIDAY_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class SpecialHolidayRemainDataDto extends MonthlyItemCommon {

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

	/** 特別休暇コード */
	@AttendanceItemLayout(jpPropertyName = SPECIAL_HOLIDAY + CODE, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.CODE)
	private int no;

	/** 実特別休暇 */
	@AttendanceItemLayout(jpPropertyName = REAL + SPECIAL_HOLIDAY, layout = LAYOUT_D)
	private SpecialLeaveDto actualSpecial;

	/** 特別休暇 */
	@AttendanceItemLayout(jpPropertyName = SPECIAL_HOLIDAY, layout = LAYOUT_E)
	private SpecialLeaveDto specialLeave;

	/** 付与区分 */
	@AttendanceItemLayout(jpPropertyName = GRANT + ATTRIBUTE, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.FLAG)
	private boolean grantAtr;

	/** 特別休暇付与情報: 付与日数 */
	@AttendanceItemLayout(jpPropertyName = GRANT + DAYS, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double grantDays;

	/** 未消化数 */
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_H)
	private SpecialLeaveUnDigestionDataDto unDigestionData;

	public static SpecialHolidayRemainDataDto from(SpecialHolidayRemainData domain) {
		SpecialHolidayRemainDataDto dto = new SpecialHolidayRemainDataDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getSid());
			dto.setYm(domain.getYm());
			dto.setClosureID(domain.getClosureId());
			dto.setClosureDate(domain.getClosureDate() == null ? null : ClosureDateDto.from(domain.getClosureDate()));
			dto.setDatePeriod(DatePeriodDto.from(domain.getClosurePeriod()));
			dto.setClosureStatus(domain.getClosureStatus().value);
			dto.setNo(domain.getSpecialHolidayCd());
			dto.setActualSpecial(SpecialLeaveDto.from(domain.getActualSpecial()));
			dto.setSpecialLeave(SpecialLeaveDto.from(domain.getSpecialLeave()));
			dto.setGrantAtr(domain.isGrantAtr());
			dto.setGrantDays(domain.getGrantDays().isPresent() ? domain.getGrantDays().get().v() : null);
			dto.setUnDigestionData(SpecialLeaveUnDigestionDataDto.from(domain.getUnDegestionNumber()));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public SpecialHolidayRemainData toDomain(String employeeId, YearMonth ym, int closureID,
			ClosureDateDto closureDate) {
		return new SpecialHolidayRemainData(employeeId, ym, closureID,
				closureDate == null ? null : closureDate.toDomain(), datePeriod == null ? null : datePeriod.toDomain(),
				closureStatus == ClosureStatus.PROCESSED.value ? ClosureStatus.PROCESSED : ClosureStatus.UNTREATED, no,
				actualSpecial == null ? new SpecialLeave() : actualSpecial.toDomain(), 
				specialLeave == null ? new SpecialLeave() : specialLeave.toDomain(),
				Optional.ofNullable(grantDays == null ? null : new LeaveGrantDayNumber(grantDays)), grantAtr,
				unDigestionData == null ? new SpecialLeaveUnDigestion() : unDigestionData.toDomain());
	}

	@Override
	public YearMonth yearMonth() {
		return ym;
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if ((REAL + SPECIAL_HOLIDAY).equals(path) || SPECIAL_HOLIDAY.equals(path)) {
			return new SpecialLeaveDto();
		} else if (NOT_DIGESTION.equals(path)) {
			return new SpecialLeaveUnDigestionDataDto();
		}
		return super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		if ((REAL + SPECIAL_HOLIDAY).equals(path)) {
			return Optional.ofNullable(this.actualSpecial);
		} else if (SPECIAL_HOLIDAY.equals(path)) {
			return Optional.ofNullable(this.specialLeave);
		} else if (NOT_DIGESTION.equals(path)) {
			return Optional.ofNullable(this.unDigestionData);
		}
		return super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if ((REAL + SPECIAL_HOLIDAY).equals(path)) {
			this.actualSpecial = (SpecialLeaveDto) value;
		} else if (SPECIAL_HOLIDAY.equals(path)) {
			this.specialLeave = (SpecialLeaveDto) value;
		} else if (NOT_DIGESTION.equals(path)) {
			this.unDigestionData = (SpecialLeaveUnDigestionDataDto) value;
		}
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case GRANT + ATTRIBUTE:
			return Optional.of(ItemValue.builder().value(grantAtr).valueType(ValueType.FLAG));
		case GRANT + DAYS:
			return Optional.of(ItemValue.builder().value(grantDays).valueType(ValueType.DAYS));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case GRANT + ATTRIBUTE:
			grantAtr = value.valueOrDefault(false);
			break;
		case GRANT + DAYS:
			grantDays = value.valueOrDefault(0.0);
			break;
		default:
			break;
		}
	}

	@Override
	public PropType typeOf(String path) {

		switch (path) {
		case GRANT + ATTRIBUTE:
		case GRANT + DAYS:
			return PropType.VALUE;
		case REAL + SPECIAL_HOLIDAY:
		case SPECIAL_HOLIDAY:
		case NOT_DIGESTION:
			return PropType.OBJECT;
		default:
			return super.typeOf(path);
		}
	}

	@Override
	public String employeeId() {
		return employeeId;
	}

}
