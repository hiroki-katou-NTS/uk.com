package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author thanh_nx
 *
 *
 *         勤務予定のデータ
 */
@Getter
public class NRWebDailyDataExport {

	// 社員ID
	private String employeeId;

	// 年月日
	private GeneralDate date;

	// 勤怠項目と値
	private List<AttendanceItemAndValueExport> value;

	public NRWebDailyDataExport(String employeeId, GeneralDate date, List<AttendanceItemAndValueExport> value) {
		this.employeeId = employeeId;
		this.date = date;
		this.value = value;
	}

}
