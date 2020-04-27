package nts.uk.ctx.at.record.app.find.monthly.root.dto.wrapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.SpecialHolidayRemainDataDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayAndTimeDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.SpecialLeaveDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.SpecialLeaveUseNumberDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 特別休暇 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialHolidayRemainDto implements ItemConst {
	
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
}
