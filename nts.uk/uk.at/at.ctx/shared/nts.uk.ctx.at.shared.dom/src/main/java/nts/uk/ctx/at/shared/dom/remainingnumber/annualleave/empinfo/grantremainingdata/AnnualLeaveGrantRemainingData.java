package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.math.BigDecimal;
import java.util.Optional;

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
// domain name CS00037: 年休付与残数データ
public class AnnualLeaveGrantRemainingData extends LeaveGrantRemainingData {

	/**
	 * 年休付与条件情報
	 */
	protected Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo = Optional.empty();

	public static AnnualLeaveGrantRemainingData createFromJavaType(String annLeavID, String employeeId,
			GeneralDate grantDate, GeneralDate deadline, int expirationStatus, int registerType, double grantDays,
			Integer grantMinutes, double usedDays, Integer usedMinutes, Double stowageDays, double remainDays,
			Integer remainMinutes, double usedPercent, Double prescribedDays, Double deductedDays, Double workingDays) {

		
		Optional<AnnualLeaveConditionInfo> conditionInfo = Optional.empty();
		if (prescribedDays != null && deductedDays != null && workingDays != null) {
			conditionInfo = Optional
					.of(AnnualLeaveConditionInfo.createFromJavaType(prescribedDays, deductedDays, workingDays));
		}
		
		return new AnnualLeaveGrantRemainingData(
				annLeavID,
				employeeId,
				grantDate,
				deadline,
				EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class),
				EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class),
				new AnnualLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
						remainDays, remainMinutes, usedPercent),
				conditionInfo
				);
	}

	public void updateData(GeneralDate grantDate, GeneralDate deadline, int expirationStatus, int registerType,
			double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes, Double stowageDays,
			double remainDays, Integer remainMinutes, double usedPercent) {
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		this.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

		this.details = new AnnualLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
				remainDays, remainMinutes, usedPercent);

	}

	/**
	 * 年休付与残数履歴データを年休付与残数データに変換
	 * 
	 * @param history
	 *            年休付与残数履歴データ
	 * @return 年休付与残数データ
	 */
	// 2019.3.3 ADD shuichi_ishida
	public static AnnualLeaveGrantRemainingData createFromHistory(AnnualLeaveRemainingHistory history) {
		return new AnnualLeaveGrantRemainingData(history);
	}

	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate, BigDecimal grantDays,
			BigDecimal usedDays, BigDecimal remainDays, String grantDateItemName, String deadlineDateItemName) {
		boolean isNull = validate(grantDate, deadlineDate, grantDays, usedDays, remainDays);
		if (isNull == false)
			return isNull;
		if (grantDays != null || usedDays != null || remainDays != null) {
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

	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate, BigDecimal grantDays,
			BigDecimal usedDays, BigDecimal remainDays) {
		if (grantDate == null && deadlineDate == null && grantDays == null && usedDays == null && remainDays == null)
			return false;
		return true;
	}


	/**
	 * 
	 * @param employeeId  社員ID
	 * @param grantDate 付与日
	 * @param deadline 期限日
	 * @param expirationStatus 期限切れ状態
	 * @param grantRemainRegisterType  登録種別
	 * @param annualLeaveNumberInfo 明細
	 */
	public AnnualLeaveGrantRemainingData(String employeeId, GeneralDate grantDate, GeneralDate deadline,
			LeaveExpirationStatus expirationStatus, GrantRemainRegisterType grantRemainRegisterType,
			LeaveNumberInfo annualLeaveNumberInfo) {
		super(IdentifierUtil.randomUniqueId(), employeeId, grantDate, deadline, expirationStatus,
				grantRemainRegisterType, annualLeaveNumberInfo);
		this.annualLeaveConditionInfo = Optional.empty();
	}

	/**
	 * 
	 * @param leaveID ID
	 * @param employeeId  社員ID
	 * @param grantDate 付与日
	 * @param deadline 期限日
	 * @param expirationStatus 期限切れ状態
	 * @param grantRemainRegisterType  登録種別
	 * @param annualLeaveNumberInfo 明細
	 * @param annualLeaveConditionInfo 年休付与条件情報
	 */
	public AnnualLeaveGrantRemainingData(String leaveID, String employeeId, GeneralDate grantDate, GeneralDate deadline,
			LeaveExpirationStatus expirationStatus, GrantRemainRegisterType grantRemainRegisterType,
			LeaveNumberInfo annualLeaveNumberInfo, Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo) {
		super(leaveID, employeeId, grantDate, deadline, expirationStatus, grantRemainRegisterType,
				annualLeaveNumberInfo);
		this.annualLeaveConditionInfo = annualLeaveConditionInfo;
	}
	
	
	/**
	 * 
	 * @param remaingData 休暇付与残数データ
	 * @return
	 */
	public static AnnualLeaveGrantRemainingData of(LeaveGrantRemainingData remaingData){
		return new AnnualLeaveGrantRemainingData(
				remaingData.getLeaveID(),
				remaingData.getEmployeeId(),
				remaingData.getGrantDate(),
				remaingData.getDeadline(),
				remaingData.getExpirationStatus(),
				remaingData.getRegisterType(),
				remaingData.getDetails(),
				Optional.empty());
	}
	

	/**
	 * 
	 * @param employeeId  社員ID
	 * @param grantDate 付与日
	 * @param deadline 期限日
	 * @param expirationStatus 期限切れ状態
	 * @param grantRemainRegisterType  登録種別
	 * @param annualLeaveNumberInfo 明細
	 * @param annualLeaveConditionInfo 年休付与条件情報
	 */
	public AnnualLeaveGrantRemainingData(String employeeId, GeneralDate grantDate, GeneralDate deadline,
			LeaveExpirationStatus expirationStatus, GrantRemainRegisterType grantRemainRegisterType,
			AnnualLeaveNumberInfo annualLeaveNumberInfo, Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo) {

		super(IdentifierUtil.randomUniqueId(), employeeId, grantDate, deadline, expirationStatus,
				grantRemainRegisterType, annualLeaveNumberInfo);
		this.annualLeaveConditionInfo = annualLeaveConditionInfo;
	}

	/**
	 * 年休付与残数データ
	 * @param data
	 */
	public AnnualLeaveGrantRemainingData(AnnualLeaveGrantRemainingData data) {
		super(data.getLeaveID(), data.getEmployeeId(), data.getGrantDate(), data.getDeadline(),
				data.getExpirationStatus(), data.getRegisterType(), data.getDetails().clone());
		this.annualLeaveConditionInfo = data.getAnnualLeaveConditionInfo();
	}
}
