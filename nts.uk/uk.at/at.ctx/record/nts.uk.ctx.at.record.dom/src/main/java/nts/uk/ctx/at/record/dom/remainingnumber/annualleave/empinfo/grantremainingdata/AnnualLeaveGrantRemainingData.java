package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Getter
// domain name: 年休付与残数データ
public class AnnualLeaveGrantRemainingData extends AggregateRoot {

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

	public static AnnualLeaveGrantRemainingData createFromJavaType(String annLeavID, String cID, String employeeId, GeneralDate grantDate,
			GeneralDate deadline, int expirationStatus, int registerType, double grantDays, Integer grantMinutes,
			double usedDays, Integer usedMinutes, Double stowageDays, double remainDays, Integer remainMinutes,
			double usedPercent, Double prescribedDays, Double deductedDays, Double workingDays) {
		AnnualLeaveGrantRemainingData domain = new AnnualLeaveGrantRemainingData();
		domain.cid = cID;
		domain.annLeavID = annLeavID;
		domain.employeeId = employeeId;
		domain.grantDate = grantDate;
		domain.deadline = deadline;
		domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);
		
		domain.details = new AnnualLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
				remainDays, remainMinutes, usedPercent);
		
		if (prescribedDays != null && deductedDays != null && workingDays != null) {
			domain.annualLeaveConditionInfo = Optional
					.of(AnnualLeaveConditionInfo.createFromJavaType(prescribedDays, deductedDays, workingDays));
		} else {
			domain.annualLeaveConditionInfo = Optional.empty();
		}
		return domain;
	}

}
