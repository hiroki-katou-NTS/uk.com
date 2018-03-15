package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Getter
//domain name: 積立年休付与残数データ
public class ReserveLeaveGrantRemainingData extends AggregateRoot{
	
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

}
