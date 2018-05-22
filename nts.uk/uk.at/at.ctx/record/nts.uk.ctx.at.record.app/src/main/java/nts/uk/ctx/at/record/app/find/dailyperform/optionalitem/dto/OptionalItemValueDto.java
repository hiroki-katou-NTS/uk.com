package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalItemValueDto {

	/** 任意項目: 回数, 時間, 金額 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(layout = "A", jpPropertyName = "値")
	private int value;

	private int itemNo;

	private boolean isTimeItem;

	private boolean isTimesItem;

	private boolean isAmountItem;

	public static OptionalItemValueDto from(AnyItemValue c, OptionalItemAtr attr) {
		if (c != null) {
			boolean isTimes = attr == OptionalItemAtr.NUMBER;
			boolean isAmount = attr == OptionalItemAtr.AMOUNT;
			boolean isTime = attr == OptionalItemAtr.TIME;
			int value = isAmount ? c.getAmount().get().v() : 
						isTime ? c.getTime().get().valueAsMinutes() : 
							     c.getTimes().get().v();
			return new OptionalItemValueDto(value, c.getItemNo().v(), isTime, isTimes, isAmount);
		}
		return null;
	}

	public AnyItemValue toDomain() {
		return new AnyItemValue(new AnyItemNo(this.itemNo),
								Optional.of(new AnyItemTimes(this.isTimesItem ? this.value : 0)),
								Optional.of(new AnyItemAmount(this.isAmountItem ? this.value : 0)),
								Optional.of(new AnyItemTime(this.isTimeItem ? this.value : 0)));
	}
}
