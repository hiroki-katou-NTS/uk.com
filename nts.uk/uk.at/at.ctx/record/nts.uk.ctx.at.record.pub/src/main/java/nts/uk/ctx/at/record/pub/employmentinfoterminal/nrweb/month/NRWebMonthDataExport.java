package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month;

import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;

/**
 * @author thanh_nx
 *
 *         月別実績(Work)のデータ
 */
@Getter
public class NRWebMonthDataExport {

	// 年月
	private YearMonth ym;

	// 勤怠項目と値
	private List<AttItemValueMonthExport> value;

	public NRWebMonthDataExport(YearMonth ym, List<AttItemValueMonthExport> value) {
		super();
		this.ym = ym;
		this.value = value;
	}

}
