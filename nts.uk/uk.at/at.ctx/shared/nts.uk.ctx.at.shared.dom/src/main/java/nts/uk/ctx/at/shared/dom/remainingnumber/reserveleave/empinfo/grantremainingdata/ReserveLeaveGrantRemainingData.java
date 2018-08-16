package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
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
}
