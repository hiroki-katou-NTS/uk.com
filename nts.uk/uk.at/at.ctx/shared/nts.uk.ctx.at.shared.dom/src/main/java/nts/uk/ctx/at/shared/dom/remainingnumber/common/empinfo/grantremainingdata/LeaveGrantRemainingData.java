package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.*;

/**
 * 休暇付与残数データ
 * @author masaaki_jinno
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class LeaveGrantRemainingData extends AggregateRoot {

	protected String annLeavID;
	
	protected String cid;
	/**
	 * 社員ID
	 */
	protected String employeeId;

	/**
	 * 付与日
	 */
	protected GeneralDate grantDate;

	/**
	 * 期限日
	 */
	protected GeneralDate deadline;

	/**
	 * 期限切れ状態
	 */
	@Setter
	protected LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	protected GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
	protected LeaveNumberInfo details;

	
}
