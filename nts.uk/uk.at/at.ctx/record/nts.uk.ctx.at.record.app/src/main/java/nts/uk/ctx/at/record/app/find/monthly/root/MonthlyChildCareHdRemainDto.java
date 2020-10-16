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
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.MonChildHdMinutes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.MonChildHdNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.MonChildHdRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/** 子の看護月別残数データ */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_CHILD_CARE_HD_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyChildCareHdRemainDto extends MonthlyItemCommon {

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
	
	private ClosureStatus closureStatus = ClosureStatus.UNTREATED;

	/** 締め期間: 期間 */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;
	
	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	private Double usedDays;
	
	/** 使用日数付与前 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS + BEFORE, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	private Double usedDaysBefore;
	
	/** 使用日数付与後 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS + AFTER, layout = LAYOUT_D)
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	private Double usedDaysAfter;

	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME, layout = LAYOUT_E)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer usedMinutes;
	
	/** 使用時間付与前 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME + BEFORE, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer usedMinutesBefore;
	
	/** 使用時間付与後 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME + AFTER, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer usedMinutesAfter;
	
	@Override
	public String employeeId() {
		return employeeId;
	}
	@Override
	public MonChildHdRemain toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		
		return new MonChildHdRemain(employeeId, ym, ConvertHelper.getEnum(closureID, ClosureId.class), 
				new Day(closureDate == null ? 1 : closureDate.getClosureDay()), 
				closureDate == null ? 0 : closureDate.getLastDayOfMonth() ? 1 : 0, 
				closureStatus, datePeriod == null ? null : datePeriod.getStart(), datePeriod == null ? null : datePeriod.getEnd(),
				toNumber(usedDays), toNumber(usedDaysBefore), toNumber(usedDaysAfter), 
				toMinutes(usedMinutes), toMinutes(usedMinutesBefore), toMinutes(usedMinutesAfter));
	}
	
	private MonChildHdNumber toNumber(Double number){
		return new MonChildHdNumber(number == null ? 0.0 : number);
	}
	
	private MonChildHdMinutes toMinutes(Integer minutes){
		return new MonChildHdMinutes(minutes == null ? 0 : minutes);
	}
	
	@Override
	public YearMonth yearMonth() {
		return ym;
	}
	
	public static MonthlyChildCareHdRemainDto from(MonChildHdRemain domain){
		MonthlyChildCareHdRemainDto dto = new MonthlyChildCareHdRemainDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId().value);
			dto.setClosureStatus(domain.getClosureStatus());
			dto.setClosureDate(new ClosureDateDto(domain.getClosureDay().v(), domain.getIsLastDay() == 1 ? true : false));
			dto.setDatePeriod(new DatePeriodDto(domain.getStartDate(), domain.getEndDate()));
			dto.setUsedDays(domain.getUsedDays().v());
			dto.setUsedDaysAfter(domain.getUsedDaysAfter().v());
			dto.setUsedDaysBefore(domain.getUsedDaysBefore().v());
			dto.setUsedMinutes(domain.getUsedMinutes().v());
			dto.setUsedMinutesAfter(domain.getUsedMinutesAfter().v());
			dto.setUsedMinutesBefore(domain.getUsedMinutesBefore().v());
			dto.exsistData();
		}
		return dto;
	}@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (USAGE + DAYS):
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.COUNT_WITH_DECIMAL));
		case (USAGE + DAYS + BEFORE):
			return Optional.of(ItemValue.builder().value(usedDaysBefore).valueType(ValueType.COUNT_WITH_DECIMAL));
		case (USAGE + DAYS + AFTER):
			return Optional.of(ItemValue.builder().value(usedDaysAfter).valueType(ValueType.COUNT_WITH_DECIMAL));
		case (USAGE + TIME):
			return Optional.of(ItemValue.builder().value(usedMinutes).valueType(ValueType.TIME));
		case (USAGE + TIME + BEFORE):
			return Optional.of(ItemValue.builder().value(usedMinutesBefore).valueType(ValueType.TIME));
		case (USAGE + TIME + AFTER):
			return Optional.of(ItemValue.builder().value(usedMinutesAfter).valueType(ValueType.TIME));
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
		case (USAGE + DAYS):
		case (USAGE + DAYS + BEFORE):
		case (USAGE + DAYS + AFTER):
		case (USAGE + TIME):
		case (USAGE + TIME + BEFORE):
		case (USAGE + TIME + AFTER):
			return PropType.VALUE;
		default:
			break;
		}
		return super.typeOf(path);
	}
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (USAGE + DAYS):
			usedDays = value.valueOrDefault(null); break;
		case (USAGE + DAYS + BEFORE):
			usedDaysBefore = value.valueOrDefault(null); break;
		case (USAGE + DAYS + AFTER):
			usedDaysAfter = value.valueOrDefault(null); break;
		case (USAGE + TIME):
			usedMinutes = value.valueOrDefault(null); break;
		case (USAGE + TIME + BEFORE):
			usedMinutesBefore = value.valueOrDefault(null); break;
		case (USAGE + TIME + AFTER):
			usedMinutesAfter = value.valueOrDefault(null); break;
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
		return MONTHLY_CHILD_CARE_HD_REMAIN_NAME;
	}
}
