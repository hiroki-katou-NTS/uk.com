package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;

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

		return new SpecialLeaveGrantRemainingData(leavID, employeeId, grantDate, deadline,
				EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class),
				EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class),
				new SpecialLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays, numberOverDays,
						timeOver, remainDays, remainMinutes, usedPercent),
				specialLeaveCode);
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
		return new SpecialLeaveGrantRemainingData(remain.getLeaveID(), remain.getEmployeeId(), remain.getGrantDate(),
				remain.getDeadline(), remain.getExpirationStatus(), remain.getRegisterType(),
				remain.getDetails().clone(), code);
	}
	
	
	/**
	 * コンストラクタ
	 * @param employeeId
	 * @param grantDate
	 * @param deadline
	 * @param expirationStatus
	 * @param grantRemainRegisterType
	 * @param leaveNumberInfo
	 * @param specialLeaveCode
	 */
	public SpecialLeaveGrantRemainingData(String employeeId, GeneralDate grantDate, GeneralDate deadline,
			LeaveExpirationStatus expirationStatus, GrantRemainRegisterType grantRemainRegisterType,
			LeaveNumberInfo leaveNumberInfo, int specialLeaveCode) {

		super(IdentifierUtil.randomUniqueId(), employeeId, grantDate, deadline, expirationStatus,
				grantRemainRegisterType,leaveNumberInfo);
		this.specialLeaveCode = specialLeaveCode;
	}
	/**
	 * コンストラクタ
	 * @param leaveID
	 * @param employeeId
	 * @param grantDate
	 * @param deadline
	 * @param expirationStatus
	 * @param grantRemainRegisterType
	 * @param leaveNumberInfo
	 * @param specialLeaveCode
	 */
	public SpecialLeaveGrantRemainingData(String leaveID, String employeeId, GeneralDate grantDate,
			GeneralDate deadline, LeaveExpirationStatus expirationStatus,
			GrantRemainRegisterType grantRemainRegisterType, LeaveNumberInfo leaveNumberInfo, int specialLeaveCode) {

		super(leaveID, employeeId, grantDate, deadline, expirationStatus, grantRemainRegisterType, leaveNumberInfo);
		this.specialLeaveCode = specialLeaveCode;
	}
	
	/**
	 * コンストラクタ
	 * @param data
	 */
	public SpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data) {
		super(data.getLeaveID(), data.getEmployeeId(), data.getGrantDate(), data.getDeadline(),
				data.getExpirationStatus(), data.getRegisterType(), data.getDetails().clone());
		this.specialLeaveCode = data.getSpecialLeaveCode();
	}

}
