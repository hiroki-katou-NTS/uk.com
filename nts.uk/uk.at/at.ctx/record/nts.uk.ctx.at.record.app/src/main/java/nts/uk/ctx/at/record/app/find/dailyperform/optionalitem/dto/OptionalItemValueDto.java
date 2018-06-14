package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
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
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;

@NoArgsConstructor
public class OptionalItemValueDto implements ItemConst {

	private boolean autoInit = true;

	/** 任意項目: 回数, 時間, 金額 */
	@AttendanceItemValue
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = VALUE)
	private String value;

	@Getter
	private int no;

	private OptionalItemAtr itemAttr;

	public OptionalItemValueDto(String value, int itemNo, OptionalItemAtr itemAttr) {
		super();
		this.value = value;
		this.no = itemNo;
		this.itemAttr = itemAttr;
	}

	public static OptionalItemValueDto from(AnyItemValue c) {
		return from(c, null);
	}

	public static OptionalItemValueDto from(AnyItemValue c, OptionalItemAtr attr) {
		if (c != null) {
			OptionalItemValueDto dto = new OptionalItemValueDto();
			dto.no = c.getItemNo().v();
			dto.correctValue(c, attr);
			return dto;
		}
		return null;
	}

	public static OptionalItemValueDto from(AnyItemOfMonthly c) {
		return from(c, null);
	}

	public static OptionalItemValueDto from(AnyItemOfMonthly c, OptionalItemAtr attr) {
		if (c != null) {
			OptionalItemValueDto dto = new OptionalItemValueDto();
			dto.no = c.getAnyItemId();
			dto.correctValue(c, attr);
			return dto;
		}
		return null;
	}

	public AnyItemValue toDomain() {
		return new AnyItemValue(new AnyItemNo(this.no),
				Optional.of(new AnyItemTimes(getDailyTimesOrDefault())),
				Optional.of(new AnyItemAmount(getDailyAmountOrDefault())),
				Optional.of(new AnyItemTime(getDailyTimeOrDefault())));
	}

	public boolean isHaveData() {
		return !StringUtil.isNullOrEmpty(this.value, true);
	}

	public void itemMapped() {
		this.autoInit = false;
	}

	public boolean isNeedCorrect() {
		return this.autoInit || this.itemAttr == null;
	}

	public void correctItem(OptionalItemAtr attr) {
		this.itemAttr = attr;
		this.autoInit = false;
	}

	public Integer getDailyTime() {
		if (isTimeItem() && isHaveData()) {
			return Integer.parseInt(value);
		}
		return null;
	}

	public BigDecimal getDailyTimes() {
		if (isTimesItem() && isHaveData()) {
			return new BigDecimal(value);
		}
		return null;
	}

	public Integer getDailyAmount() {
		if (isAmoutItem() && isHaveData()) {
			return Integer.parseInt(value);
		}
		return null;
	}
	
	public int getDailyTimeOrDefault() {
		if (isTimeItem() && isHaveData()) {
			return Integer.parseInt(value);
		}
		return 0;
	}

	public BigDecimal getDailyTimesOrDefault() {
		if (isTimesItem() && isHaveData()) {
			return new BigDecimal(value);
		}
		return BigDecimal.ZERO;
	}

	public int getDailyAmountOrDefault() {
		if (isAmoutItem() && isHaveData()) {
			return Integer.parseInt(value);
		}
		return 0;
	}

	public AnyTimeMonth getMonthlyTime() {
		if (isTimeItem() && isHaveData()) {
			return new AnyTimeMonth(Integer.parseInt(value));
		}
		return null;
	}

	public AnyTimesMonth getMonthlyTimes() {
		if (isTimesItem() && isHaveData()) {
			return new AnyTimesMonth(Double.parseDouble(value));
		}
		return null;
	}

	public AnyAmountMonth getMonthlyAmountOrDefault() {
		if (isAmoutItem() && isHaveData()) {
			return new AnyAmountMonth(Integer.parseInt(value));
		}
		return new AnyAmountMonth(0);
	}
	
	public AnyTimeMonth getMonthlyTimeOrDefault() {
		if (isTimeItem() && isHaveData()) {
			return new AnyTimeMonth(Integer.parseInt(value));
		}
		return new AnyTimeMonth(0);
	}

	public AnyTimesMonth getMonthlyTimesOrDefault() {
		if (isTimesItem() && isHaveData()) {
			return new AnyTimesMonth(Double.parseDouble(value));
		}
		return new AnyTimesMonth(0.0);
	}

	public AnyAmountMonth getMonthlyAmount() {
		if (isAmoutItem() && isHaveData()) {
			return new AnyAmountMonth(Integer.parseInt(value));
		}
		return null;
	}

	public boolean isAmoutItem() {
		return itemAttr == OptionalItemAtr.AMOUNT;
	}

	public boolean isTimesItem() {
		return itemAttr == OptionalItemAtr.NUMBER;
	}

	public boolean isTimeItem() {
		return itemAttr == OptionalItemAtr.TIME;
	}
	
	private void correctValue(AnyItemValue c, OptionalItemAtr attr){
		if(attr != null){
			this.itemAttr = attr;
			switch (itemAttr) {
			case AMOUNT:
				this.value = String.valueOf(c.getAmount().orElse(new AnyItemAmount(0)).v());
				break;
			case NUMBER:
				this.value = c.getTimes().orElse(new AnyItemTimes(BigDecimal.ZERO)).v().toPlainString();
				break;
			case TIME:
				this.value = String.valueOf(c.getTime().orElse(new AnyItemTime(0)).valueAsMinutes());
				break;
			default:
				break;
			}
		} else {
			ItemAndV amount = new ItemAndV(OptionalItemAtr.AMOUNT, String.valueOf(c.getAmount().orElse(new AnyItemAmount(0)).v()));
			ItemAndV time = new ItemAndV(OptionalItemAtr.TIME, String.valueOf(c.getTime().orElse(new AnyItemTime(0)).v()));
			ItemAndV number = new ItemAndV(OptionalItemAtr.NUMBER, c.getTimes().orElse(new AnyItemTimes(BigDecimal.ZERO)).v().toPlainString());
			ItemAndV maxed = Collections.max(Arrays.asList(amount, time, number), (c1, c2) -> c1.value.compareTo(c2.value));
			this.itemAttr = maxed.attr;
			this.value = maxed.value;
		}
		itemMapped();
	}
	
	private void correctValue(AnyItemOfMonthly c, OptionalItemAtr attr){
		if(attr != null){
			this.itemAttr = attr;
			switch (itemAttr) {
			case AMOUNT:
				this.value = String.valueOf(c.getAmount().orElse(new AnyAmountMonth(0)).v());
				break;
			case NUMBER:
				this.value = c.getTimes().orElse(new AnyTimesMonth(0.0)).v().toPlainString();
				break;
			case TIME:
				this.value = String.valueOf(c.getTime().orElse(new AnyTimeMonth(0)).valueAsMinutes());
				break;
			default:
				break;
			}
		} else {
			ItemAndV amount = new ItemAndV(OptionalItemAtr.AMOUNT, String.valueOf(c.getAmount().orElse(new AnyAmountMonth(0)).v()));
			ItemAndV time = new ItemAndV(OptionalItemAtr.TIME, String.valueOf(c.getTime().orElse(new AnyTimeMonth(0)).v()));
			ItemAndV number = new ItemAndV(OptionalItemAtr.NUMBER, c.getTimes().orElse(new AnyTimesMonth(0.0)).v().toPlainString());
			ItemAndV maxed = Collections.max(Arrays.asList(amount, time, number), (c1, c2) -> c1.value.compareTo(c2.value));
			this.itemAttr = maxed.attr;
			this.value = maxed.value;
		}
		itemMapped();
	}

	private class ItemAndV {
		
		private OptionalItemAtr attr;
		
		private String value;

		public ItemAndV(OptionalItemAtr attr, String value) {
			super();
			this.attr = attr;
			this.value = value;
		}
	}
}
