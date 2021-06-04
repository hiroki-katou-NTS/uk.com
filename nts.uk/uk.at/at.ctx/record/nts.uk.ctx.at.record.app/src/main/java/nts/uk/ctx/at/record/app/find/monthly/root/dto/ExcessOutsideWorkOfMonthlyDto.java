package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideItemByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.SuperHD60HConTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の時間外超過 */
public class ExcessOutsideWorkOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 月割増合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = MONTHLY_PREMIUM, layout = LAYOUT_A)
	private int monthlyTotalPremiumTime;
	
	/** 時間: 時間外超過 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B, listMaxLength = 50, indexField = DEFAULT_INDEX_FIELD_NAME, defaultIdx = "LIST_FAKE_NO")
	private List<ExcessOutsideWorkDto> time;
	
	/** 週割増合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WEEKLY_PREMIUM, layout = LAYOUT_C)
	private int weeklyTotalPremiumTime;
	
	/** 変形繰越時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = IRREGULAR + CARRY_FORWARD, layout = LAYOUT_D)
	private int deformationCarryforwardTime;
	
	/** 付与時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SUPER_60 + GRANT, layout = LAYOUT_E)
	private int superHD60GrantTime;
	
	/** 精算時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SUPER_60 + CALC, layout = LAYOUT_F)
	private int superHD60PayoffTime;
	
	/** 換算時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SUPER_60 + TRANSFER, layout = LAYOUT_G)
	private int superHD60ConversionTime;
	
	public ExcessOutsideWorkOfMonthly toDomain() {
		return ExcessOutsideWorkOfMonthly.of(
						new AttendanceTimeMonth(weeklyTotalPremiumTime), 
						new AttendanceTimeMonth(monthlyTotalPremiumTime), 
						new AttendanceTimeMonthWithMinus(deformationCarryforwardTime), 
						ConvertHelper.mapTo(time, c -> c.toDomain()),
						SuperHD60HConTime.of(new AttendanceTimeMonth(superHD60GrantTime), 
											new AttendanceTimeMonth(superHD60PayoffTime), 
											new AttendanceTimeMonth(superHD60ConversionTime)));
	}
	
	public static ExcessOutsideWorkOfMonthlyDto from(ExcessOutsideWorkOfMonthly domain) {
		ExcessOutsideWorkOfMonthlyDto dto = new ExcessOutsideWorkOfMonthlyDto();
		if(domain != null) {
			dto.setDeformationCarryforwardTime(domain.getDeformationCarryforwardTime() == null 
					? 0 : domain.getDeformationCarryforwardTime().valueAsMinutes());
			dto.setMonthlyTotalPremiumTime(domain.getMonthlyTotalPremiumTime() == null 
					? 0 : domain.getMonthlyTotalPremiumTime().valueAsMinutes());
			dto.setWeeklyTotalPremiumTime(domain.getWeeklyTotalPremiumTime() == null 
					? 0 : domain.getWeeklyTotalPremiumTime().valueAsMinutes());
			dto.setTime(ExcessOutsideWorkDto.from(domain.getTime()));
			dto.superHD60PayoffTime = domain.getSuperHD60Time().getPayoffTime().valueAsMinutes();
			dto.superHD60GrantTime = domain.getSuperHD60Time().getGrantTime().valueAsMinutes();
			dto.superHD60ConversionTime = domain.getSuperHD60Time().getConversionTime().valueAsMinutes();
		}
		return dto;
	}
	
	public ExcessOutsideByPeriod toDomainPeriod() {
		val excess = new ArrayList<ExcessOutsideItemByPeriod>();
		
		if (this.time != null) {
			this.time.stream().filter(c -> c.getExcessNo() == 1)
						.forEach(c -> {
							excess.add(ExcessOutsideItemByPeriod.of(c.getBreakdownNo(), new AttendanceTimeMonth(c.getBreakdown())));
						});
		}
		
		return ExcessOutsideByPeriod.of(excess);
	}
	
	public static ExcessOutsideWorkOfMonthlyDto from(ExcessOutsideByPeriod domain) {
		ExcessOutsideWorkOfMonthlyDto dto = new ExcessOutsideWorkOfMonthlyDto();
		if(domain != null) {
			val excess = domain.getExcessOutsideItems().entrySet().stream()
					.map(c -> new ExcessOutsideWorkDto(1, c.getValue().getBreakdownNo(), c.getValue().getExcessTime().v()))
					.collect(Collectors.toList());
			dto.setTime(excess);
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case MONTHLY_PREMIUM:
			return Optional.of(ItemValue.builder().value(monthlyTotalPremiumTime).valueType(ValueType.TIME));
		case WEEKLY_PREMIUM:
			return Optional.of(ItemValue.builder().value(weeklyTotalPremiumTime).valueType(ValueType.TIME));
		case (IRREGULAR + CARRY_FORWARD):
			return Optional.of(ItemValue.builder().value(deformationCarryforwardTime).valueType(ValueType.TIME));
		case (SUPER_60 + GRANT):
			return Optional.of(ItemValue.builder().value(superHD60GrantTime).valueType(ValueType.TIME));
		case (SUPER_60 + CALC):
			return Optional.of(ItemValue.builder().value(superHD60PayoffTime).valueType(ValueType.TIME));
		case (SUPER_60 + TRANSFER):
			return Optional.of(ItemValue.builder().value(superHD60ConversionTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (TIME.equals(path)) {
			return new ExcessOutsideWorkDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case MONTHLY_PREMIUM:
		case WEEKLY_PREMIUM:
		case (IRREGULAR + CARRY_FORWARD):
		case (SUPER_60 + GRANT):
		case (SUPER_60 + CALC):
		case (SUPER_60 + TRANSFER):
			return PropType.VALUE;
		case TIME:
			return PropType.IDX_LIST;
		default:
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (TIME.equals(path)){
			return (List<T>) time;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case MONTHLY_PREMIUM:
			monthlyTotalPremiumTime = value.valueOrDefault(0);
			break;
		case WEEKLY_PREMIUM:
			weeklyTotalPremiumTime = value.valueOrDefault(0);
			break;
		case (IRREGULAR + CARRY_FORWARD):
			deformationCarryforwardTime = value.valueOrDefault(0);
			break;
		case (SUPER_60 + GRANT):
			superHD60GrantTime = value.valueOrDefault(0);
			break;
		case (SUPER_60 + CALC):
			superHD60PayoffTime = value.valueOrDefault(0);
			break;
		case (SUPER_60 + TRANSFER):
			superHD60ConversionTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (TIME.equals(path)){
			time = (List<ExcessOutsideWorkDto>) value;
		}
	}

	@Override
	public int size(String path) {
		if (TIME.equals(path)){
			return 50;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	
}
