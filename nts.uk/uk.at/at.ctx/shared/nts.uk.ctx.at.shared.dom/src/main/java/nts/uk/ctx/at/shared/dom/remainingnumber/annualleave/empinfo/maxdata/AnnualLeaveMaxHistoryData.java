package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 *
 * @author HungTT - 年休上限履歴データ
 *
 */

@Getter
public class AnnualLeaveMaxHistoryData extends AnnualLeaveMaxData {

	/**
	 * 年月
	 */
	private YearMonth yearMonth;

	/**
	 * 締めID
	 */
	private ClosureId closureId;

	/**
	 * 締め日
	 */
	private ClosureDate closureDate;

	public AnnualLeaveMaxHistoryData(String employeeId, String companyId, Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax,
			Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		super(employeeId, halfdayAnnualLeaveMax, timeAnnualLeaveMax);

		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
	}

	public AnnualLeaveMaxHistoryData(AnnualLeaveMaxData maxData, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		super(maxData.getEmployeeId(), maxData.getHalfdayAnnualLeaveMax(), maxData.getTimeAnnualLeaveMax());

		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
	}

}
