package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.daily;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author thanh_nx
 *
 *
 *         日別勤怠(Work)のデータ
 */
@Getter
public class RCNRWebDailyData {

	// 社員ID
	private String employeeId;

	// 年月日
	private GeneralDate date;

	// 勤怠項目と値
	private List<RCAttendanceItemAndValue> value;

	public RCNRWebDailyData(String employeeId, GeneralDate date, List<RCAttendanceItemAndValue> value) {
		this.employeeId = employeeId;
		this.date = date;
		this.value = value;
	}

}
