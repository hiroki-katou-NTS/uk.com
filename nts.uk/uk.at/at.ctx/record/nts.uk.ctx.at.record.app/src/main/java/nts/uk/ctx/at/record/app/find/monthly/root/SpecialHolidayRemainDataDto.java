package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.wrapper.SpecialHolidayRemainDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveGrantUseDay;

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
	@AttendanceItemLayout(jpPropertyName = FAKED, layout = LAYOUT_A, 
			listMaxLength = 20, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<SpecialHolidayRemainDto> specialHoliday;
	
	@Override
	public String employeeId() {
		return employeeId;
	}
	
	public static SpecialHolidayRemainDataDto from(List<SpecialHolidayRemainData> domain){
		SpecialHolidayRemainDataDto dto = new SpecialHolidayRemainDataDto();
		if (domain != null && !domain.isEmpty()) {
			dto.setEmployeeId(domain.get(0).getSid());
			dto.setYm(domain.get(0).getYm());
			dto.setClosureID(domain.get(0).getClosureId());
			dto.setClosureDate(domain.get(0).getClosureDate() == null ? null : ClosureDateDto.from(domain.get(0).getClosureDate()));
			dto.setSpecialHoliday(ConvertHelper.mapTo(domain, c -> SpecialHolidayRemainDto.from(c)));
			dto.exsistData();
		}
		return dto;
	}
	
	@Override
	public List<SpecialHolidayRemainData> toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		return ConvertHelper.mapTo(specialHoliday, c -> new SpecialHolidayRemainData(
				employeeId,
				ym,
				closureID, 
				c.getDatePeriod() == null ? null : c.getDatePeriod().toDomain(), 
				c.getClosureStatus() == ClosureStatus.PROCESSED.value ? ClosureStatus.PROCESSED : ClosureStatus.UNTREATED,
				closureDate == null ? null : closureDate.toDomain(),
				c.getNo(), 
				c.getActualSpecial() == null ? null : c.getActualSpecial().toActualDomain(), 
				c.getSpecialLeave() == null ? null : c.getSpecialLeave().toDomain(),
				c.isGrantAtr(),
				Optional.ofNullable(c.getGrantDays() == null ? null : new SpecialLeaveGrantUseDay(c.getGrantDays()))));
	}
	@Override
	public YearMonth yearMonth() {
		return ym;
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (FAKED.equals(path)) {
			return new SpecialHolidayRemainDto();
		}
		return super.newInstanceOf(path);
	}

	@Override
	public int size(String path) {
		if (FAKED.equals(path)) {
			return 20;
		}
		return super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (FAKED.equals(path)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (FAKED.equals(path)) {
			return (List<T>) specialHoliday;
		}
		return super.gets(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (FAKED.equals(path)) {
			specialHoliday = (List<SpecialHolidayRemainDto>) value;
		}
	}

	@Override
	public boolean isRoot() {
		return true;
	}

	@Override
	public String rootName() {
		return MONTHLY_SPECIAL_HOLIDAY_REMAIN_NAME;
	}

	
}
