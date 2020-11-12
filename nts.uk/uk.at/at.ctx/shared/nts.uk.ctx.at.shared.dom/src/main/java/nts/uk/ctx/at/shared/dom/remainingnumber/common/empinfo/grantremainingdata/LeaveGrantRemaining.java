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
	protected boolean dummyAtr = false;

	public static LeaveGrantRemaining createFromJavaType(
			String leavID,
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
			boolean dummyAtr) {

		LeaveGrantRemaining domain = new LeaveGrantRemaining();
			domain.leaveID = leavID;
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

	@Override
	public LeaveGrantRemaining clone() {
		LeaveGrantRemaining cloned;
		try {
			cloned = (LeaveGrantRemaining)super.clone();
//			cloned.dummyAtr = dummyAtr;
		}
		catch (Exception e){
			throw new RuntimeException("LeaveGrantRemaining clone error.");
		}
		return cloned;
	}
}
