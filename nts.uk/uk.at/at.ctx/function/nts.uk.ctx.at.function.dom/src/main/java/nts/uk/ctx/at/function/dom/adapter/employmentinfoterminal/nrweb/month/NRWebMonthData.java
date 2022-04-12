package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month;

import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;

/**
 * @author thanh_nx
 *
 *         月別実績(Work)のデータ
 */
@Getter
public class NRWebMonthData {

	// 年月
	private YearMonth ym;

	// 勤怠項目と値
	private List<AttendanceItemValueMonth> value;

	public NRWebMonthData(YearMonth ym, List<AttendanceItemValueMonth> value) {
		super();
		this.ym = ym;
		this.value = value;
	}

}
