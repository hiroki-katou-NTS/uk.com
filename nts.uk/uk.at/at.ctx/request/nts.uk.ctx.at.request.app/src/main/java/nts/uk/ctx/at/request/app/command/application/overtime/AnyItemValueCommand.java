package nts.uk.ctx.at.request.app.command.application.overtime;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

public class AnyItemValueCommand {
	/** 任意項目NO: 任意項目NO */
	public Integer itemNo;

	/** 回数: 日次任意回数 */
	public Integer times;

	/** 金額: 日次任意金額 */
	public Integer amount;

	/** 時間: 日次任意時間 */
	public Integer time;
	
	public AnyItemValue toDomain() {
		return new AnyItemValue(
				new AnyItemNo(itemNo),
				times != null ? Optional.of(new AnyItemTimes(new BigDecimal(times))) : Optional.empty(),
				amount != null ? Optional.of(new AnyItemAmount(amount)) : Optional.empty(),
				time != null ? Optional.of(new AnyItemTime(time)) : Optional.empty());
	}
}
