package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param;

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
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveNumberInfo;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
// 特休付与残数データ
public class SpecialLeaveGrantRemainingData extends LeaveGrantRemainingData {

//	private String annLeavID;
//	
//	private String cid;
//	/**
//	 * 社員ID
//	 */
//	private String employeeId;
//
//	/**
//	 * 付与日
//	 */
//	private GeneralDate grantDate;
//
//	/**
//	 * 期限日
//	 */
//	private GeneralDate deadline;
//
//	/**
//	 * 期限切れ状態
//	 */
//	@Setter
//	private LeaveExpirationStatus expirationStatus;
//
//	/**
//	 * 登録種別
//	 */
//	private GrantRemainRegisterType registerType;
//
//	/**
//	 * 明細
//	 */
//	private SpecialLeaveNumberInfo details;

	
	public static SpecialLeaveGrantRemainingData createFromJavaType(String annLeavID, String cID, String employeeId,
			GeneralDate grantDate, GeneralDate deadline, int expirationStatus, int registerType, double grantDays,
			Integer grantMinutes, double usedDays, Integer usedMinutes, Double stowageDays, double remainDays,
			Integer remainMinutes, double usedPercent, Double prescribedDays, Double deductedDays, Double workingDays) {
		
			SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();
			domain.cid = cID;
			domain.annLeavID = annLeavID;
			domain.employeeId = employeeId;
			domain.grantDate = grantDate;
			domain.deadline = deadline;
			domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
			domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

			domain.details = new SpecialLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
					remainDays, remainMinutes, usedPercent);

			if (prescribedDays != null && deductedDays != null && workingDays != null) {
				domain.annualLeaveConditionInfo = Optional
						.of(SpecialLeaveConditionInfo.createFromJavaType(prescribedDays, deductedDays, workingDays));
			} else {
				domain.annualLeaveConditionInfo = Optional.empty();
			}
			return domain;
	}
	
	public void updateData(GeneralDate grantDate, GeneralDate deadline, int expirationStatus, int registerType,
			double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes, Double stowageDays,
			double remainDays, Integer remainMinutes, double usedPercent) {
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		this.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);
		
		this.details = new SpecialLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
				remainDays, remainMinutes, usedPercent);
		
	}

	/**
	 * 特休付与残数履歴データを特休付与残数データに変換
	 * @param history 特休付与残数履歴データ
	 * @return 特休付与残数データ
	 */
	// 2019.3.3 ADD shuichi_ishida
	public static SpecialLeaveGrantRemainingData createFromHistory(SpecialLeaveRemainingHistory history) {
		
		SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();
		domain.cid = history.getCid();
		domain.annLeavID = IdentifierUtil.randomUniqueId();
		domain.employeeId = history.getEmployeeId();
		domain.grantDate = history.getGrantDate();
		domain.deadline = history.getDeadline();
		domain.expirationStatus = EnumAdaptor.valueOf(history.getExpirationStatus().value, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(history.getRegisterType().value, GrantRemainRegisterType.class);
		domain.details = history.getDetails();
		domain.annualLeaveConditionInfo = history.getSpecialLeaveConditionInfo();
		return domain;
	}

//	/**
//	 * 特休を指定日数消化する
//	 * @param usedDays 特休使用日数
//	 * @param isForcibly 強制的に消化するか
//	 * @return 特休使用残
//	 */
//	// 2018.4.23 add shuichi_ishida
//	public double digest(double usedDays, boolean isForcibly){
//		
//		// 「特休使用日数」を所得
//		if (usedDays <= 0.0) return 0.0;
//		double remainingDays = usedDays;
//		
//		// 特休残数が足りているかチェック
//		boolean isSubtractRemain = false;
//		val remainingNumber = this.details.getRemainingNumber();
//		if (remainingNumber.getDays().v().doubleValue() >= remainingDays) isSubtractRemain = true;
//		// 「強制的に消化する」をチェック
//		else if (isForcibly) isSubtractRemain = true;
//		
//		if (isSubtractRemain){
//			
//			// 特休残数から減算
//			double newRemain = remainingNumber.getDays().v() - remainingDays;
//			this.details.setRemainingNumber(SpecialLeaveRemainingNumber.createFromJavaType(newRemain, null));
//			
//			// 特休使用数に加算
//			double newUsed = this.details.getUsedNumber().getDays().v() + remainingDays;
//			Double stowageDays = null;
//			if (this.details.getUsedNumber().getStowageDays().isPresent()){
//				stowageDays = this.details.getUsedNumber().getStowageDays().get().v();
//			}
//			this.details.setUsedNumber(SpecialLeaveUsedNumber.createFromJavaType(newUsed, null, stowageDays));
//			
//			// 特休使用残を0にする
//			remainingDays = 0.0;
//		}
//		else {
//			
//			// 特休使用残から減算
//			remainingDays -= remainingNumber.getDays().v();
//			
//			// 特休使用数に加算
//			double newUsed = this.details.getUsedNumber().getDays().v() + remainingNumber.getDays().v();
//			Double stowageDays = null;
//			if (this.details.getUsedNumber().getStowageDays().isPresent()){
//				stowageDays = this.details.getUsedNumber().getStowageDays().get().v();
//			}
//			this.details.setUsedNumber(SpecialLeaveUsedNumber.createFromJavaType(newUsed, null, stowageDays));
//			
//			// 特休残数を0にする
//			this.details.setRemainingNumber(SpecialLeaveRemainingNumber.createFromJavaType(0.0, null));
//		}
//		
//		// 特休使用残を返す
//		return remainingDays;
//	}
	
	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate,
			BigDecimal grantDays, BigDecimal usedDays, BigDecimal remainDays, String grantDateItemName, String deadlineDateItemName) {
		boolean isNull = validate(grantDate, deadlineDate, grantDays, usedDays, remainDays);
		if(isNull == false) return isNull;
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
	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate, BigDecimal grantDays, BigDecimal usedDays, BigDecimal remainDays) {
		if (grantDate == null && deadlineDate == null && grantDays == null && usedDays == null && remainDays == null)
			return false;
		return true;
	}

	/**
	 * コンストラクタ
	 * @param employeeId // 社員ID
	 * @param grantDate	// 付与日
	 * @param deadline	// 期限日
	 * @param expirationStatus //　期限切れ状態
	 * @param grantRemainRegisterType // 登録種別
	 * @param annualLeaveNumberInfo //　明細
	 */
	public SpecialLeaveGrantRemainingData(
			String employeeId, GeneralDate grantDate, GeneralDate deadline,
			LeaveExpirationStatus expirationStatus,
			GrantRemainRegisterType grantRemainRegisterType,
			SpecialLeaveNumberInfo spacialLeaveNumberInfo) {
		this.employeeId = employeeId;
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expirationStatus = expirationStatus;
		this.registerType = grantRemainRegisterType;
		this.details = spacialLeaveNumberInfo;
	}
}
