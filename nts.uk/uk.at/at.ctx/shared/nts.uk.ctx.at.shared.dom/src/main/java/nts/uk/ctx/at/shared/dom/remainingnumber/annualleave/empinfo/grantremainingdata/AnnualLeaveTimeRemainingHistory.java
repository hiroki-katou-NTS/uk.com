package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;

/**
 * 
 * @author HungTT - 年休付与時点残数履歴データ
 *
 */
@Getter
public class AnnualLeaveTimeRemainingHistory extends AggregateRoot {

	private String annLeavID;

	private String cid;
	/**
	 * 社員ID
	 */
	private String employeeId;

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
	private AnnualLeaveNumberInfo details;

	/**
	 * 年休付与条件情報
	 */
	private Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo;

	// 付与処理日
	private GeneralDate grantProcessDate;

	public AnnualLeaveTimeRemainingHistory(String annLeavID, String cID, String employeeId, GeneralDate grantDate,
			GeneralDate deadline, int expirationStatus, int registerType, double grantDays, Integer grantMinutes,
			double usedDays, Integer usedMinutes, Double stowageDays, double remainDays, Integer remainMinutes,
			double usedPercent, Double prescribedDays, Double deductedDays, Double workingDays,
			GeneralDate grantProcessDate) {
		this.cid = cID;
		this.annLeavID = annLeavID;
		this.employeeId = employeeId;
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		this.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

		this.details = new AnnualLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
				remainDays, remainMinutes, usedPercent);

		if (prescribedDays != null && deductedDays != null && workingDays != null) {
			this.annualLeaveConditionInfo = Optional
					.of(AnnualLeaveConditionInfo.createFromJavaType(prescribedDays, deductedDays, workingDays));
		} else {
			this.annualLeaveConditionInfo = Optional.empty();
		}
		this.grantProcessDate = grantProcessDate;
	}

	public AnnualLeaveTimeRemainingHistory(AnnualLeaveGrantRemainingData data, GeneralDate grantProcessDate) {
		this.cid = data.getCid();
		this.annLeavID = data.getAnnLeavID();
		this.employeeId = data.getEmployeeId();
		this.grantDate = data.getGrantDate();
		this.deadline = data.getDeadline();
		this.expirationStatus = data.getExpirationStatus();
		this.registerType = data.getRegisterType();
		this.details = data.getDetails();
		this.annualLeaveConditionInfo = data.getAnnualLeaveConditionInfo();
		this.grantProcessDate = grantProcessDate;
	}

}
