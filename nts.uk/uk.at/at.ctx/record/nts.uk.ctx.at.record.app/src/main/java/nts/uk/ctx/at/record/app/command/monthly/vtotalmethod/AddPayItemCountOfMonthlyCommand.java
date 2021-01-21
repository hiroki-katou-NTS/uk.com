package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

import java.util.List;

import lombok.Data;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 
 * @author HoangNDH
 *
 */
@Data
public class AddPayItemCountOfMonthlyCommand {
	/** 給与出勤日数 */
	private List<String> payAttendanceDays;
	/** 給与欠勤日数 */
	private List<String> payAbsenceDays;
	
	public PayItemCountOfMonthly toDomain(String companyId) {
		val domain = new PayItemCountOfMonthly(companyId);
		for (val attendanceDays: payAttendanceDays) {
			domain.getPayAttendanceDays().add(new WorkTypeCode(attendanceDays));
		}
		for (val absenceDays: payAbsenceDays) {
			domain.getPayAbsenceDays().add(new WorkTypeCode(absenceDays));
		}
		return domain;
	}
}
