package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;

/**
 * 休暇付与残数
 * @author masaaki_jinno
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveGrantRemaining extends LeaveGrantRemainingData {
	
	/** 休暇不足ダミーフラグ */
	private boolean dummyAtr = false;

	public static LeaveGrantRemaining createFromJavaType(
			String annLeavID, 
			String cID, 
			String employeeId,
			GeneralDate grantDate, 
			GeneralDate deadline, 
			int expirationStatus, 
			int registerType, 
			double grantDays,
			Integer grantMinutes, 
			double usedDays, 
			Integer usedMinutes, 
			Double stowageDays, 
			double remainDays,
			Integer remainMinutes, 
			double usedPercent, 
			Double prescribedDays, 
			Double deductedDays, 
			Double workingDays) {
		
		LeaveGrantRemaining domain = new LeaveGrantRemaining();
			domain.annLeavID = annLeavID;
			domain.cid = cID;
			domain.employeeId = employeeId;
			domain.grantDate = grantDate;
			domain.deadline = deadline;
			domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
			domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

			// 明細
			domain.details = new LeaveNumberInfo(
					grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
					remainDays, remainMinutes, usedPercent);

			return domain;
	}
}
