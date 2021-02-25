package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;

@NoArgsConstructor
public class OptionalItemValueDto implements ItemConst, AttendanceItemDataGate {

	private static final AnyItemTime ANY_ITEM_TIME = new AnyItemTime(0);

	private static final AnyItemTimes ANY_ITEM_TIMES = new AnyItemTimes(BigDecimal.ZERO);

	private static final AnyItemAmount ANY_ITEM_AMOUNT = new AnyItemAmount(0);

	private static final AnyTimeMonth ANY_TIME_MONTH = new AnyTimeMonth(0);

	private static final AnyTimesMonth ANY_TIMES_MONTH = new AnyTimesMonth(0.0);

	private static final AnyAmountMonth ANY_AMOUNT_MONTH = new AnyAmountMonth(0);

	private boolean autoInit = true;

	/** 任意項目: 回数, 時間, 金額 */
	/** TODO: set */
	@AttendanceItemValue(type = ValueType.UNKNOWN, getTypeWith = DEFAULT_GET_TYPE, setValueWith = DEFAULT_SET_VALUE)
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

	@Override
	public OptionalItemValueDto clone() {
		OptionalItemValueDto dto = new OptionalItemValueDto(value, no, itemAttr);
		dto.autoInit = autoInit;
		return dto;
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

	public void value(Object val) {
		if(val == null || val == ""){
			this.value = "0";
		} else {
			this.value = val.toString();
		}
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
		return ANY_AMOUNT_MONTH;
	}
	
	public AnyTimeMonth getMonthlyTimeOrDefault() {
		if (isTimeItem() && isHaveData()) {
			return new AnyTimeMonth(Integer.parseInt(value));
		}
		return ANY_TIME_MONTH;
	}

	public AnyTimesMonth getMonthlyTimesOrDefault() {
		if (isTimesItem() && isHaveData()) {
			return new AnyTimesMonth(Double.parseDouble(value));
		}
		return ANY_TIMES_MONTH;
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
	
	public ValueType getValueType(){
		if(isAmoutItem()){
			return ValueType.AMOUNT;
		}
		if(isTimesItem()){
			return ValueType.COUNT_WITH_DECIMAL;
		}
		return ValueType.TIME;
	}
	
	private void correctValue(AnyItemValue c, OptionalItemAtr attr) {

		internalCorrect(attr, () -> c.getAmount().orElseGet(() -> ANY_ITEM_AMOUNT).v(), 
				() -> c.getTimes().orElseGet(() -> ANY_ITEM_TIMES).v(), 
				() -> c.getTime().orElseGet(() -> ANY_ITEM_TIME).valueAsMinutes());
	}
	
	private void correctValue(AnyItemOfMonthly c, OptionalItemAtr attr) {

		internalCorrect(attr, () -> c.getAmount().orElse(ANY_AMOUNT_MONTH).v(), 
				() -> c.getTimes().orElse(ANY_TIMES_MONTH).v(), 
				() -> c.getTime().orElse(ANY_TIME_MONTH).valueAsMinutes());
	}
	
	private void internalCorrect(OptionalItemAtr attr, IntSupplier amountOrDef, 
			Supplier<BigDecimal> countOrDef, IntSupplier timeOrDef){
		Integer amount = amountOrDef.getAsInt();
		BigDecimal count = countOrDef.get();
		Integer time = timeOrDef.getAsInt();
		if(attr != null){
			this.itemAttr = attr;
			switch (itemAttr) {
			case AMOUNT:
				this.value = amount.toString();
				break;
			case NUMBER:
				this.value = count.toPlainString();
				break;
			case TIME:
				this.value = time.toString();
				break;
			default:
				break;
			}
		} else {
			double countD = count.doubleValue();
			if (countD > amount) {
				if (countD > time) {
					this.itemAttr = OptionalItemAtr.NUMBER;
					this.value = count.toPlainString();
				} else {
					this.itemAttr = OptionalItemAtr.TIME;
					this.value = time.toString();
				}
			} else {
				if (amount > time) {
					this.itemAttr = OptionalItemAtr.AMOUNT;
					this.value = amount.toString();
				} else {
					this.itemAttr = OptionalItemAtr.TIME;
					this.value = time.toString();
				}
			}
		}
		itemMapped();
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (VALUE.equals(path)) {
			return Optional.of(ItemValue.builder().value(this.value).valueType(getValueType()));
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (VALUE.equals(path)) {
			this.value(value.valueAsObjet());
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (VALUE.equals(path)) {
			return PropType.VALUE;
		}
		return PropType.OBJECT;
	}
}
