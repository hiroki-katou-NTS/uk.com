package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveNumberInfo;

@Getter
//@NoArgsConstructor
//@AllArgsConstructor
// 特休付与残数
public class SpecialLeaveGrantRemainingData extends LeaveGrantRemaining {

	/**
	 * 特別休暇コード
	 */
	private int specialLeaveCode;

	public static SpecialLeaveGrantRemainingData createFromJavaType(
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
			boolean dummyAtr,
			int specialLeaveCode) {

//		boolean check = validate(grantDate, deadline, grantDays, grantMinutes, numberOverdays,
//				remainDays , grantDateItemName , deadlineDateItemName);
//		if (check) {
			SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();
			domain.leaveID = leavID;
			domain.cid = cID;
			domain.employeeId = employeeId;
			domain.grantDate = grantDate;
			domain.deadline = deadline;
			domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
			domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);
			domain.details = new SpecialLeaveNumberInfo(
					grantDays, grantMinutes, usedDays, usedMinutes,
					stowageDays, remainDays, remainMinutes, usedPercent);

			domain.dummyAtr = dummyAtr;
			domain.specialLeaveCode = specialLeaveCode;

			return domain;
//		}
//		return null;
	}

	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate,
			BigDecimal dayNumberOfGrant, BigDecimal dayNumberOfUse, BigDecimal numberOverdays,
			BigDecimal dayNumberOfRemain , String grantDateItemName ,String deadlineDateItemName) {
		boolean isNull = validate(grantDate, deadlineDate, dayNumberOfGrant, dayNumberOfUse, numberOverdays, dayNumberOfRemain);
		if (dayNumberOfGrant != null || dayNumberOfUse != null || dayNumberOfRemain != null) {
			if (deadlineDate == null || grantDate == null) {
				if (grantDate == null) {
					throw new BusinessException("Msg_925", grantDateItemName == null ? "付与日" : grantDateItemName);
				}
				if (deadlineDate == null) {
					throw new BusinessException("Msg_925", deadlineDateItemName == null ? "期限日" : deadlineDateItemName);
				}
			}
		}
		if (grantDate == null && deadlineDate != null) {
			throw new BusinessException("Msg_925", grantDateItemName == null ? "付与日" : grantDateItemName);
		}
		if (deadlineDate == null && grantDate != null) {
			throw new BusinessException("Msg_925", deadlineDateItemName == null ? "期限日" : deadlineDateItemName);
		}
		if (grantDate != null && deadlineDate != null) {
			// 付与日＞使用期限の場合はエラー #Msg_1023
			if (grantDate.compareTo(deadlineDate) > 0) {
				throw new BusinessException("Msg_1023");
			}
		}
		return isNull;
	}

	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate,
			BigDecimal dayNumberOfGrant, BigDecimal dayNumberOfUse, BigDecimal numberOverdays,
			BigDecimal dayNumberOfRemain) {
		if (grantDate == null && deadlineDate == null && dayNumberOfGrant == null && dayNumberOfUse == null
				&& numberOverdays == null && dayNumberOfRemain == null)
			return false;
		return true;
	}

	@Override
	public SpecialLeaveGrantRemainingData clone() {
		SpecialLeaveGrantRemainingData cloned;
		try {
			cloned = (SpecialLeaveGrantRemainingData)super.clone();
			cloned.specialLeaveCode = specialLeaveCode;
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveGrantRemainingData clone error.");
		}
		return cloned;
	}

}
