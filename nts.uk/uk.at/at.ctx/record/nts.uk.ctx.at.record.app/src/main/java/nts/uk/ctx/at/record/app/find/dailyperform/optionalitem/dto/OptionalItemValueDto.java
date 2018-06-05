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
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
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
	
			String value = isAmount ? c.getAmount().isPresent() ? String.valueOf(c.getAmount().get().v() ) : "" : 
						isTime ? c.getTime().isPresent() ? String.valueOf(c.getTime().get().valueAsMinutes()) : "" : 
							c.getTimes().isPresent() ? c.getTimes().get().v().toString() : "";
			OptionalItemValueDto dto = new OptionalItemValueDto(value, c.getItemNo().v(), isTime, isTimes, isAmount);
			dto.itemMapped();
			return dto;
	
	public static OptionalItemValueDto from(AnyItemValue c) {
		if(c != null) {
			boolean isTimes = c.getTimes().isPresent();
			boolean isAmount = c.getAmount().isPresent();
			boolean isTime = c.getTime().isPresent();
			String value = isAmount ? c.getAmount().get().v().toString()
					: isTime ? String.valueOf(c.getTime().get().valueAsMinutes())
							: String.valueOf(c.getTimes().get().v());
		}
		return null;
	}
	
		if(c != null) {
			boolean isTimes = c.getTimes().isPresent();
			boolean isAmount = c.getAmount().isPresent();
			boolean isTime = c.getTime().isPresent();
			String value = isTimes ? c.getTimes().get().v().toString()
					: isAmount ? String.valueOf(c.getAmount().get().v())
							: String.valueOf(c.getTime().get().valueAsMinutes());
			OptionalItemValueDto dto = new OptionalItemValueDto(value, c.getAnyItemId(), isTime, isTimes, isAmount);
			dto.itemMapped();
			return dto;
		}
		return null;
	}

	public AnyItemValue toDomain() {
		boolean isHaveData = isHaveData();
		return new AnyItemValue(new AnyItemNo(this.itemNo), 
						this.isTimesItem && isHaveData ? Optional.of(new AnyItemTimes(new BigDecimal(this.value))) : Optional.empty(),
						this.isAmountItem && isHaveData ? Optional.of(new AnyItemAmount(Integer.parseInt(this.value))) : Optional.empty(),
						this.isTimeItem && isHaveData? Optional.of(new AnyItemTime(Integer.valueOf(this.value))) : Optional.empty());
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
	
	public Integer getDailyTimes(){
		if(isTimesItem && isHaveData()){
			return Integer.parseInt(value);
		}
		return null;
	}
	
	public BigDecimal getDailyAmount(){
		if(isAmountItem && isHaveData()){
			return new BigDecimal(value);
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
