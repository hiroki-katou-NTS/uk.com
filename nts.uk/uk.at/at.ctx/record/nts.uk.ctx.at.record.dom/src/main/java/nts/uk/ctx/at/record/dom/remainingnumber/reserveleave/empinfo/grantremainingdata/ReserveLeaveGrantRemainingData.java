package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Getter
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

}
