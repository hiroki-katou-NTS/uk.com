package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_CALCULATION_ATTR_NAME)
public class CalcAttrOfDailyPerformanceDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_CALCULATION_ATTR_NAME; }
	/***/
	private static final long serialVersionUID = 1L;
	
	/** ??????ID: ??????ID */
	private String employeeId;

	/** ?????????: ????????? */
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	/** ???????????????????????????: ???????????????????????????????????????????????? */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = FLEX)
	private AutoCalculationSettingDto flexExcessTime;

	/** ??????: ??????????????????????????? */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = RAISING_SALARY)
	private AutoCalRaisingSalarySettingDto rasingSalarySetting;

	/** ????????????: ????????????????????????????????? */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY_WORK)
	private AutoCalHolidaySettingDto holidayTimeSetting;

	/** ????????????: ????????????????????????????????? */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = OVERTIME)
	private AutoCalOfOverTimeDto overtimeSetting;

	/** ????????????: ????????????????????????????????? */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = LATE + LEAVE_EARLY)
	private AutoCalOfLeaveEarlySettingDto leaveEarlySetting;

	/** ????????????: ????????????????????????????????? */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = DIVERGENCE)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int divergenceTime;
	
	public static CalcAttrOfDailyPerformanceDto getDto(CalAttrOfDailyPerformance domain) {
		CalcAttrOfDailyPerformanceDto result = new CalcAttrOfDailyPerformanceDto();
		if (domain != null) {
			result.setEmployeeId(domain.getEmployeeId());
			result.setYmd(domain.getYmd());
			result.setDivergenceTime(getDivergence(domain.getCalcategory().getDivergenceTime()));
			result.setFlexExcessTime(newAutoCalcSetting(domain.getCalcategory().getFlexExcessTime().getFlexOtTime()));
			result.setHolidayTimeSetting(newAutoCalcHolidaySetting(domain.getCalcategory().getHolidayTimeSetting()));
			result.setLeaveEarlySetting(newAutoCalcLeaveSetting(domain.getCalcategory().getLeaveEarlySetting()));
			result.setOvertimeSetting(getOverTimeSetting(domain.getCalcategory().getOvertimeSetting()));
			result.setRasingSalarySetting(newAutoCalcSalarySetting(domain.getCalcategory().getRasingSalarySetting()));
			result.exsistData();
		}
		return result;
	}
	public static CalcAttrOfDailyPerformanceDto getDto(String employeeID,GeneralDate ymd, CalAttrOfDailyAttd domain) {
		CalcAttrOfDailyPerformanceDto result = new CalcAttrOfDailyPerformanceDto();
		if (domain != null) {
			result.setEmployeeId(employeeID);
			result.setYmd(ymd);
			result.setDivergenceTime(getDivergence(domain.getDivergenceTime()));
			result.setFlexExcessTime(newAutoCalcSetting(domain.getFlexExcessTime().getFlexOtTime()));
			result.setHolidayTimeSetting(newAutoCalcHolidaySetting(domain.getHolidayTimeSetting()));
			result.setLeaveEarlySetting(newAutoCalcLeaveSetting(domain.getLeaveEarlySetting()));
			result.setOvertimeSetting(getOverTimeSetting(domain.getOvertimeSetting()));
			result.setRasingSalarySetting(newAutoCalcSalarySetting(domain.getRasingSalarySetting()));
			result.exsistData();
		}
		return result;
	}
	
	@Override
	public CalcAttrOfDailyPerformanceDto clone() {
		CalcAttrOfDailyPerformanceDto result = new CalcAttrOfDailyPerformanceDto();
		result.setEmployeeId(employeeId());
		result.setYmd(workingDate());
		result.setDivergenceTime(divergenceTime);
		result.setFlexExcessTime(flexExcessTime == null ? null : flexExcessTime.clone());
		result.setHolidayTimeSetting(holidayTimeSetting == null ? null : holidayTimeSetting.clone());
		result.setLeaveEarlySetting(leaveEarlySetting == null ? null : leaveEarlySetting.clone());
		result.setOvertimeSetting(overtimeSetting == null ? null : overtimeSetting.clone());
		result.setRasingSalarySetting(rasingSalarySetting == null ? null : rasingSalarySetting.clone());
		if (this.isHaveData()) {
			result.exsistData();
		}
		return result;
	}

	private static int getDivergence(AutoCalcSetOfDivergenceTime domain) {
		return domain == null || domain.getDivergenceTime() == null ? 0 : domain.getDivergenceTime().value;
	}

	private static AutoCalRaisingSalarySettingDto newAutoCalcSalarySetting(AutoCalRaisingSalarySetting domain) {
		return domain == null ? null : new AutoCalRaisingSalarySettingDto(
						domain.isRaisingSalaryCalcAtr() ? 1 : 0,
						domain.isSpecificRaisingSalaryCalcAtr() ? 1 : 0);
	}

	private static AutoCalOfLeaveEarlySettingDto newAutoCalcLeaveSetting(AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting) {
		return autoCalcOfLeaveEarlySetting == null ? null : new AutoCalOfLeaveEarlySettingDto(
						autoCalcOfLeaveEarlySetting.isLeaveEarly() ? 1 : 0,
						autoCalcOfLeaveEarlySetting.isLate() ? 1 : 0 
						);
	}

	private static AutoCalHolidaySettingDto newAutoCalcHolidaySetting(AutoCalRestTimeSetting domain) {
		return domain == null ? null : new AutoCalHolidaySettingDto(
						newAutoCalcSetting(domain.getRestTime()),
						newAutoCalcSetting(domain.getLateNightTime()));
	}

	private static AutoCalOfOverTimeDto getOverTimeSetting(AutoCalOvertimeSetting domain) {
		return domain == null ? null : new AutoCalOfOverTimeDto(
						newAutoCalcSetting(domain.getEarlyOtTime()),
						newAutoCalcSetting(domain.getEarlyMidOtTime()),
						newAutoCalcSetting(domain.getNormalOtTime()),
						newAutoCalcSetting(domain.getNormalMidOtTime()),
						newAutoCalcSetting(domain.getLegalOtTime()),
						newAutoCalcSetting(domain.getLegalMidOtTime()));
	}

	private static AutoCalculationSettingDto newAutoCalcSetting(AutoCalSetting domain) {
		return domain == null ? null : new AutoCalculationSettingDto(
					domain.getCalAtr() == null ?  0 : domain.getCalAtr().value, 
					domain.getUpLimitORtSet() == null ? 0 : domain.getUpLimitORtSet().value);
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public CalAttrOfDailyAttd toDomain(String employeeId, GeneralDate date) {

		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		CalAttrOfDailyPerformance domain =  new CalAttrOfDailyPerformance(
				employeeId,  date, 
				new AutoCalFlexOvertimeSetting(newAutoCalcSetting(this.flexExcessTime)),
				createAutoCalcRaisingSalarySetting(),
				createAutoCalcHolidaySetting(),
				createAutoOverTimeSetting(),
				createAutoCalcLeaveSetting(),
				new AutoCalcSetOfDivergenceTime(this.divergenceTime == DivergenceTimeAttr.USE.value 
					? DivergenceTimeAttr.USE : DivergenceTimeAttr.NOT_USE));
		return domain.getCalcategory();
	}

	private AutoCalRaisingSalarySetting createAutoCalcRaisingSalarySetting() {
		return this.rasingSalarySetting == null ? AutoCalRaisingSalarySetting.defaultValue() : new AutoCalRaisingSalarySetting(
						this.rasingSalarySetting.getSpecificSalaryCalSetting() == 1 ? true: false,
						this.rasingSalarySetting.getSalaryCalSetting() == 1 ? true : false);
	}

	private AutoCalRestTimeSetting createAutoCalcHolidaySetting() {
		return this.holidayTimeSetting == null ? AutoCalRestTimeSetting.defaultValue() : new AutoCalRestTimeSetting(
				newAutoCalcSetting(this.holidayTimeSetting.getHolidayWorkTime()),
				newAutoCalcSetting(this.holidayTimeSetting.getLateNightTime()));
	}

	private AutoCalcOfLeaveEarlySetting createAutoCalcLeaveSetting() {
		return this.leaveEarlySetting == null ? AutoCalcOfLeaveEarlySetting.defaultValue() 
												: new AutoCalcOfLeaveEarlySetting(
															this.leaveEarlySetting.getLeaveLate() == 1 ? true : false,
															this.leaveEarlySetting.getLeaveEarly() == 1 ? true : false);
	}

	private AutoCalOvertimeSetting createAutoOverTimeSetting() {
		return this.overtimeSetting == null ? null : new AutoCalOvertimeSetting(
				newAutoCalcSetting(this.overtimeSetting.getEarlyOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getEarlyMidnightOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getNormalOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getNormalMidnightOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getLegalOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getLegalMidnightOverTime()));
	}
	
	private AutoCalSetting newAutoCalcSetting(AutoCalculationSettingDto dto) {
		return dto == null ? AutoCalSetting.defaultValue() : 
									new AutoCalSetting(convertToTimeLimitUpper(dto.getUpperLimitSetting()),
														convertToCalcAtrOT(dto.getCalculationAttr()));
	}
	
	private TimeLimitUpperLimitSetting convertToTimeLimitUpper(int value){
		switch (value) {
			case 2:
				return TimeLimitUpperLimitSetting.INDICATEDYIMEUPPERLIMIT;
			case 1:
				return TimeLimitUpperLimitSetting.LIMITNUMBERAPPLICATION;
			case 0:
				return TimeLimitUpperLimitSetting.NOUPPERLIMIT;
		}
		return null;
	}
	
	private AutoCalAtrOvertime convertToCalcAtrOT(int value){
		switch (value) {
			case 2:
				return AutoCalAtrOvertime.CALCULATEMBOSS;
			case 1:
				return AutoCalAtrOvertime.TIMERECORDER;
			case 0:
				return AutoCalAtrOvertime.APPLYMANUALLYENTER;
		}
		return null;
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case FLEX:
			return Optional.ofNullable(flexExcessTime);
		case RAISING_SALARY:
			return Optional.ofNullable(rasingSalarySetting);
		case HOLIDAY_WORK:
			return Optional.ofNullable(holidayTimeSetting);
		case OVERTIME:
			return Optional.ofNullable(overtimeSetting);
		case (LATE + LEAVE_EARLY):
			return Optional.ofNullable(leaveEarlySetting);
		default:
			return Optional.empty();
		}
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case FLEX:
			flexExcessTime = (AutoCalculationSettingDto) value;
			break;
		case RAISING_SALARY:
			rasingSalarySetting = (AutoCalRaisingSalarySettingDto) value;
			break;
		case HOLIDAY_WORK:
			holidayTimeSetting =  (AutoCalHolidaySettingDto) value;
			break;
		case OVERTIME:
			overtimeSetting = (AutoCalOfOverTimeDto) value;
			break;
		case (LATE + LEAVE_EARLY):
			leaveEarlySetting =  (AutoCalOfLeaveEarlySettingDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case FLEX:
			return new AutoCalculationSettingDto();
		case RAISING_SALARY:
			return new AutoCalRaisingSalarySettingDto();
		case HOLIDAY_WORK:
			return new AutoCalHolidaySettingDto();
		case OVERTIME:
			return new AutoCalOfOverTimeDto();
		case (LATE + LEAVE_EARLY):
			return new AutoCalOfLeaveEarlySettingDto();
		default:
			return null;
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		
		if (path.equals(DIVERGENCE)) {
			return Optional.of(ItemValue.builder().value(divergenceTime).valueType(ValueType.ATTR));
		}
		
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (path.equals(DIVERGENCE)) {
			this.divergenceTime = value.valueOrDefault(0);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(DIVERGENCE)) {
			return PropType.VALUE;
		}
		return super.typeOf(path);
	}
	
}
