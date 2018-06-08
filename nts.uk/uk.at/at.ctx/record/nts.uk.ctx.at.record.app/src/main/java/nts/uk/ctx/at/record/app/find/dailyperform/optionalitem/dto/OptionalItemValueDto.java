package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;

@NoArgsConstructor
public class OptionalItemValueDto {
	
	private boolean autoInit = true;

	/** 任意項目: 回数, 時間, 金額 */
	@AttendanceItemValue
	@AttendanceItemLayout(layout = "A", jpPropertyName = "値")
	private String value;

	@Getter
	private int itemNo;

	private boolean isTimeItem;

	private boolean isTimesItem;

	private boolean isAmountItem;

	public OptionalItemValueDto(String value, int itemNo, boolean isTimeItem, boolean isTimesItem,
			boolean isAmountItem) {
		super();
		this.value = value;
		this.itemNo = itemNo;
		this.isTimeItem = isTimeItem;
		this.isTimesItem = isTimesItem;
		this.isAmountItem = isAmountItem;
	}
	
	public static OptionalItemValueDto from(AnyItemValue c) {
		if (c != null) {
			boolean isTimes = c.getTimes().isPresent();
			boolean isAmount = c.getAmount().isPresent();
			boolean isTime = c.getTime().isPresent();
			String value = isAmount ? c.getAmount().isPresent() ? String.valueOf(c.getAmount().get().v() ) : "" : 
						isTime ? c.getTime().isPresent() ? String.valueOf(c.getTime().get().valueAsMinutes()) : "" : 
							c.getTimes().isPresent() ? c.getTimes().get().v().toString() : "";
			OptionalItemValueDto dto = new OptionalItemValueDto(value, c.getItemNo().v(), isTime, isTimes, isAmount);
//			dto.itemMapped();
			return dto;
		}
		return null;
	}
	
	public static OptionalItemValueDto from(AnyItemValue c, OptionalItemAtr attr) {
		if (c != null) {
			boolean isTimes = attr == OptionalItemAtr.NUMBER;
			boolean isAmount = attr == OptionalItemAtr.AMOUNT;
			boolean isTime = attr == OptionalItemAtr.TIME;
			String value = isAmount ? c.getAmount().isPresent() ? String.valueOf(c.getAmount().get().v() ) : "" : 
						isTime ? c.getTime().isPresent() ? String.valueOf(c.getTime().get().valueAsMinutes()) : "" : 
							c.getTimes().isPresent() ? c.getTimes().get().v().toString() : "";
			OptionalItemValueDto dto = new OptionalItemValueDto(value, c.getItemNo().v(), isTime, isTimes, isAmount);
			dto.itemMapped();
			return dto;
		}
		return null;
	}
	
	public static OptionalItemValueDto from(AnyItemOfMonthly c, OptionalItemAtr attr) {
		if(c != null) {
			boolean isTimes = attr == OptionalItemAtr.NUMBER;
			boolean isAmount = attr == OptionalItemAtr.AMOUNT;
			boolean isTime = attr == OptionalItemAtr.TIME;
			String value = isAmount ? String.valueOf(c.getAmount().get().v() ): 
				isTime ? String.valueOf(c.getTime().get().valueAsMinutes()) : 
					     c.getTimes().get().v().toString();
			OptionalItemValueDto dto = new OptionalItemValueDto(value, c.getAnyItemId(), isTime, isTimes, isAmount);
			dto.itemMapped();
			return dto;
		}
		return null;
	}

	public AnyItemValue toDomain() {
		boolean isHaveData = isHaveData();
		return new AnyItemValue(new AnyItemNo(this.itemNo), 
						Optional.of(new AnyItemTimes(this.isTimesItem && isHaveData ? new BigDecimal(this.value) : BigDecimal.ZERO)),
						Optional.of(new AnyItemAmount(this.isAmountItem && isHaveData ? Integer.parseInt(this.value) : 0)),
						Optional.of(new AnyItemTime(this.isTimeItem && isHaveData? Integer.valueOf(this.value) : 0)));
	}

	public boolean isHaveData() {
		return !StringUtil.isNullOrEmpty(this.value, true);
	}
	
	public void itemMapped() {
		this.autoInit = false;
	}

	public boolean isNeedCorrect() {
		return autoInit;
	}
	
	public void correctItem(OptionalItemAtr attr) {
		this.isTimesItem = attr == OptionalItemAtr.NUMBER;
		this.isAmountItem = attr == OptionalItemAtr.AMOUNT;
		this.isTimeItem = attr == OptionalItemAtr.TIME;
		this.autoInit = false;
	}
	
	public Integer getDailyTime(){
		if(isTimeItem && isHaveData()){
			return Integer.parseInt(value);
		}
		return null;
	}
	
	public BigDecimal getDailyTimes(){
		if(isTimesItem && isHaveData()){
			return new BigDecimal(value);
		}
		return null;
	}
	
	public Integer getDailyAmount(){
		if(isAmountItem && isHaveData()){
			return Integer.parseInt(value);
		}
		return null;
	}
	
	public AnyTimeMonth getMonthlyTime(){
		if(isTimeItem && isHaveData()){
			return new AnyTimeMonth(Integer.parseInt(value));
		}
		return null;
	}
	
	public AnyTimesMonth getMonthlyTimes(){
		if(isTimesItem && isHaveData()){
			return new AnyTimesMonth(Double.parseDouble(value));
		}
		return null;
	}
	
	public AnyAmountMonth getMonthlyAmount(){
		if(isAmountItem && isHaveData()){
			return new AnyAmountMonth(Integer.parseInt(value));
		}
		return null;
	}
}
