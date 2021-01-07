package nts.uk.ctx.at.record.app.find.monthly.root.dto.wrapper;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.SpecialLeaveDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;

/** 特別休暇 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialHolidayRemainDto implements ItemConst, AttendanceItemDataGate {
	
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
	
	/** 特別休暇*/
	@AttendanceItemLayout(jpPropertyName = SPECIAL_HOLIDAY, layout = LAYOUT_E)
	private SpecialLeaveDto specialLeave;
	
	/** 付与区分*/
	@AttendanceItemLayout(jpPropertyName = GRANT + ATTRIBUTE, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.FLAG)
	private boolean grantAtr;
	
	/** 特別休暇付与情報: 付与日数 */
	@AttendanceItemLayout(jpPropertyName = GRANT + DAYS, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.DAYS)
	private Double grantDays;
	
	public static SpecialHolidayRemainDto from(SpecialHolidayRemainData domain){
		SpecialHolidayRemainDto dto = new SpecialHolidayRemainDto();
		
		dto.setDatePeriod(DatePeriodDto.from(domain.getClosurePeriod()));
		dto.setClosureStatus(domain.getClosureStatus().value);
		dto.setNo(domain.getSpecialHolidayCd());
		dto.setActualSpecial(SpecialLeaveDto.from(domain.getActualSpecial()));
		dto.setSpecialLeave(SpecialLeaveDto.from(domain.getSpecialLeave()));
		dto.setGrantAtr(domain.isGrantAtr());
		dto.setGrantDays(domain.getGrantDays().isPresent() ? domain.getGrantDays().get().v() : null);
		
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case CLOSURE_STATE:
			return Optional.of(ItemValue.builder().value(closureStatus).valueType(ValueType.ATTR));
		case (SPECIAL_HOLIDAY + CODE):
			return Optional.of(ItemValue.builder().value(no).valueType(ValueType.CODE));
		case (GRANT + ATTRIBUTE):
			return Optional.of(ItemValue.builder().value(grantAtr).valueType(ValueType.FLAG));
		case (GRANT + DAYS):
			return Optional.of(ItemValue.builder().value(grantDays).valueType(ValueType.DAYS));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case PERIOD:
			return new DatePeriodDto();
		case (REAL + SPECIAL_HOLIDAY):
		case SPECIAL_HOLIDAY:
			return new SpecialLeaveDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case PERIOD:
			return Optional.ofNullable(datePeriod);
		case (REAL + SPECIAL_HOLIDAY):
			return Optional.ofNullable(actualSpecial);
		case SPECIAL_HOLIDAY:
			return Optional.ofNullable(specialLeave);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case CLOSURE_STATE:
		case (SPECIAL_HOLIDAY + CODE):
		case (GRANT + ATTRIBUTE):
		case (GRANT + DAYS):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case CLOSURE_STATE:
			closureStatus = value.valueOrDefault(0); break;
		case (SPECIAL_HOLIDAY + CODE):
			no = value.valueOrDefault(0); break;
		case (GRANT + ATTRIBUTE):
			grantAtr = value.valueOrDefault(false); break;
		case (GRANT + DAYS):
			grantDays = value.valueOrDefault(null); break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case PERIOD:
			datePeriod = (DatePeriodDto) value; break;
		case (REAL + SPECIAL_HOLIDAY):
			actualSpecial = (SpecialLeaveDto) value; break;
		case SPECIAL_HOLIDAY:
			specialLeave = (SpecialLeaveDto) value; break;
		default:
			break;
		}
	}

	
}
