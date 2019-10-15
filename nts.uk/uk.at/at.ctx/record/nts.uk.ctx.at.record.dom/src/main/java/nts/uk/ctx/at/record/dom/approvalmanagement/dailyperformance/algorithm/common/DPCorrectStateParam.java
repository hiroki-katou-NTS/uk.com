package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author ThanhNX
 * 日別実績の確認の状態
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DPCorrectStateParam {

	/**
	 * 対象年月
	 */
	private YearMonth yearMonth;

	/**
	 * 対象期間
	 */
	private DatePeriod datePeriod;

	/**
	 * 対象社員
	 */
	private List<String> lstEmployeeId;

	/**
	 * 対象締め
	 */
	private ClosureId closureId;

}
