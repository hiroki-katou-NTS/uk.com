package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 *
 * @author HungTT - 積立年休付与残数履歴データ
 *
 */

@Getter
public class ReserveLeaveGrantRemainHistoryData extends AggregateRoot {


	/**
	 * 社員ID
	 */
	private String employeeId;

	// 年月
	private YearMonth yearMonth;

	// 締めID
	private ClosureId closureId;

	// 締め日
	private ClosureDate closureDate;

	/**
	 * 付与日
	 */
	private GeneralDate grantDate;

	/**
	 * 期限日
	 */
	private GeneralDate deadline;

	/**
	 * 期限切れ状態
	 */
	private LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	private GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
//	private ReserveLeaveNumberInfo details;
	private LeaveNumberInfo details;


	public ReserveLeaveGrantRemainHistoryData(String employeeId, YearMonth yearMonth, Integer closureId, ClosureDate closureDate, GeneralDate grantDate, GeneralDate deadline,
			int expirationStatus, int registerType, double grantDays, double usedDays, Double overLimitDays,
			double remainDays) {
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = EnumAdaptor.valueOf(closureId, ClosureId.class);
		this.closureDate = closureDate;
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		this.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);
		//this.details = new ReserveLeaveNumberInfo(grantDays, usedDays, overLimitDays, remainDays);
		this.details = new ReserveLeaveNumberInfo(grantDays, usedDays, overLimitDays, remainDays);

	}

	public ReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainingData data, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		super();
		this.employeeId = data.getEmployeeId();
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.grantDate = data.getGrantDate();
		this.deadline = data.getDeadline();
		this.expirationStatus = data.getExpirationStatus();
		this.registerType = data.getRegisterType();
		this.details = data.getDetails();
	}

}
