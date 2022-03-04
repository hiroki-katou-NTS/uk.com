package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceAmountMonth;

/**
 * @author sakuratani
 *
 *         項目値Imported
 */
@Getter
@AllArgsConstructor
public class ItemValueImported {

	//時間
	private AttendanceTimeMonthWithMinus time;

	//金額
	private AttendanceAmountMonth amount;

	//色
	private Optional<ColorCode> color;

}