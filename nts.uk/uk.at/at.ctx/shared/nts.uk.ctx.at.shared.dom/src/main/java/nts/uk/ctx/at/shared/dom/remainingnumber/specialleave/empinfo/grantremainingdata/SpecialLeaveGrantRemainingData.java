package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;

@Getter
/** 特別休暇付与残数データ */
public class SpecialLeaveGrantRemainingData extends LeaveGrantRemainingData {

	/**
	 * 特別休暇コード
	 */
	protected int specialLeaveCode;

	public static SpecialLeaveGrantRemainingData createFromJavaType(
			String leavID,
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
			Double numberOverDays,
			Integer timeOver,
			double remainDays,
			Integer remainMinutes,
			double usedPercent,
			int specialLeaveCode) {

		SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();
		domain.leaveID = leavID;
		domain.employeeId = employeeId;
		domain.grantDate = grantDate;
		domain.deadline = deadline;
		domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);
		domain.details = new SpecialLeaveNumberInfo(
				grantDays, grantMinutes, usedDays, usedMinutes,
				stowageDays, numberOverDays,
				timeOver, remainDays, remainMinutes, usedPercent);

		domain.specialLeaveCode = specialLeaveCode;

		return domain;
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
		cloned = SpecialLeaveGrantRemainingData.of(super.clone(), this.specialLeaveCode);
		cloned.specialLeaveCode = specialLeaveCode;

		return cloned;
	}

	public static SpecialLeaveGrantRemainingData of(LeaveGrantRemainingData remain, int code) {
		SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();

		domain.leaveID = remain.getLeaveID();
		domain.employeeId = remain.getEmployeeId();
		domain.grantDate = remain.getGrantDate();
		domain.deadline = remain.getDeadline();
		domain.expirationStatus = remain.getExpirationStatus();
		domain.registerType = remain.getRegisterType();
		domain.details = remain.getDetails().clone();

		domain.specialLeaveCode = code;

		return domain;
	}

}
