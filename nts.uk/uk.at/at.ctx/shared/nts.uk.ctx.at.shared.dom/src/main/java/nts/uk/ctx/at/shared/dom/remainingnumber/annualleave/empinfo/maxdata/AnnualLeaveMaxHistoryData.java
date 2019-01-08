package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author HungTT - 年休上限履歴データ
 *
 */

@Getter
public class AnnualLeaveMaxHistoryData extends AggregateRoot {

	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 半日年休上限
	 */
	private Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax;

	/**
	 * 時間年休上限
	 */
	private Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax;

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

	public AnnualLeaveMaxHistoryData(String employeeId, String companyId, HalfdayAnnualLeaveMax halfdayAnnualLeaveMax,
			TimeAnnualLeaveMax timeAnnualLeaveMax, YearMonth yearMonth, int closureId, ClosureDate closureDate) {
		super();
		this.employeeId = employeeId;
		this.companyId = companyId;
		this.halfdayAnnualLeaveMax = Optional.ofNullable(halfdayAnnualLeaveMax);
		this.timeAnnualLeaveMax = Optional.ofNullable(timeAnnualLeaveMax);
		this.yearMonth = yearMonth;
		this.closureId = EnumAdaptor.valueOf(closureId, ClosureId.class);
		this.closureDate = closureDate;
	}

	public AnnualLeaveMaxHistoryData(AnnualLeaveMaxData maxData, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		super();
		this.employeeId = maxData.getEmployeeId();
		this.companyId = maxData.getCompanyId();
		this.halfdayAnnualLeaveMax = maxData.getHalfdayAnnualLeaveMax();
		this.timeAnnualLeaveMax = maxData.getTimeAnnualLeaveMax();
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
	}

}
