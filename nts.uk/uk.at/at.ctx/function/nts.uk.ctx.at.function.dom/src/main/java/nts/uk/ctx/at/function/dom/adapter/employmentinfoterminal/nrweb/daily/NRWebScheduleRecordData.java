package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.daily;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.AttendanceItemAndValue;

/**
 * @author thanh_nx
 *
 *
 *         勤務予定のデータ
 * 
 *         日別勤怠(Work)のデータ
 */
@Getter
public class NRWebScheduleRecordData {

	// 社員ID
	private String employeeId;

	// 年月日
	private GeneralDate date;

	// 勤怠項目と値
	private List<AttendanceItemAndValue> value;

	public NRWebScheduleRecordData(String employeeId, GeneralDate date, List<AttendanceItemAndValue> value) {
		this.employeeId = employeeId;
		this.date = date;
		this.value = value;
	}

}
