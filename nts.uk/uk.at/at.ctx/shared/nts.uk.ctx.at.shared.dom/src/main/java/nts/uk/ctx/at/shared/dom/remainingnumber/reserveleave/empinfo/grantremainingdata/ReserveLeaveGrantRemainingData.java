package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// domain name: 積立年休付与残数データ
public class ReserveLeaveGrantRemainingData extends AggregateRoot {
	
	/**
	 * 積立年休付与残数データID
	 */
	private String rsvLeaID;

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
	@Setter
	private LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	private GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
	private ReserveLeaveNumberInfo details;

	public static ReserveLeaveGrantRemainingData createFromJavaType(String id, String employeeId, GeneralDate grantDate,
			GeneralDate deadline, int expirationStatus, int registerType, double grantDays, double usedDays,
			Double overLimitDays, double remainDays) {
		
		ReserveLeaveGrantRemainingData domain = new ReserveLeaveGrantRemainingData();
		domain.rsvLeaID = id;
		domain.employeeId = employeeId;
		domain.grantDate = grantDate;
		domain.deadline = deadline;
		domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);
		domain.details = new ReserveLeaveNumberInfo(grantDays, usedDays, overLimitDays, remainDays);
		return domain;
	}

	/**
	 * 積立年休を指定日数消化する
	 * @param usedDays 積立年休使用日数
	 * @param isForcibly 強制的に消化するか
	 * @return 積立年休使用残
	 */
	// 2018.7.15 add shuichi_ishida
	public double digest(double usedDays, boolean isForcibly){
		
		// 「積立年休使用日数」を所得
		if (usedDays <= 0.0) return 0.0;
		double remainingDays = usedDays;
		
		// 積立年休残数が足りているかチェック
		boolean isSubtractRemain = false;
		double remainingNumber = this.details.getRemainingNumber().v();
		if (remainingNumber >= remainingDays) isSubtractRemain = true;
		// 「強制的に消化する」をチェック
		else if (isForcibly) isSubtractRemain = true;
		
		if (isSubtractRemain){
			
			// 積立年休残数から減算
			double newRemain = remainingNumber - remainingDays;
			this.details.setRemainingNumber(new ReserveLeaveRemainingDayNumber(newRemain));
			
			// 積立年休使用数に加算
			this.details.addDaysToUsedNumber(remainingDays);
			
			// 積立年休使用残を0にする
			remainingDays = 0.0;
		}
		else {
			
			// 積立年休使用残から減算
			remainingDays -= remainingNumber;
			
			// 積立年休使用数に加算
			this.details.addDaysToUsedNumber(remainingNumber);
			
			// 積立年休残数を0にする
			this.details.setRemainingNumber(new ReserveLeaveRemainingDayNumber(0.0));
		}
		
		// 積立年休使用残を返す
		return remainingDays;
	}
	
	public static void validate(ReserveLeaveGrantRemainingData domain) {
		if ((domain.getDetails().getGrantNumber() != null) || (domain.getDetails().getUsedNumber().getDays() != null)
				|| (domain.getDetails().getRemainingNumber() != null)) {
			if (domain.getGrantDate() == null || domain.getDeadline() == null) {
				if (domain.getGrantDate() == null) {
					throw new BusinessException("Msg_925", "付与日");
				}
				if (domain.getDeadline() == null) {
					throw new BusinessException("Msg_925", "期限日");
				}
			}
		}
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (domain.getGrantDate().compareTo(domain.getDeadline()) > 0) {
			throw new BusinessException("Msg_1023");
		}
	}
	
	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate,
			BigDecimal grantDays, BigDecimal usedDays, BigDecimal remainDays , String grantDateItemName, String  deadlineDateItemName) {
		if (grantDate == null && deadlineDate == null && grantDays == null && usedDays == null && remainDays == null)
			return false;

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
		return true;
	}
}
