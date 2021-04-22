package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 休暇付与残数データ　
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveGrantRemainingDataExport extends AggregateRoot {

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
	protected LeaveExpirationStatusExport expirationStatus;

	/**
	 * 登録種別
	 */
	protected GrantRemainRegisterTypeExport registerType;

	/**
	 * 明細
	 */
	protected LeaveNumberInfoExport details;

	/**
	 * コンストラクタ
	 * @param c
	 */
	public LeaveGrantRemainingDataExport(LeaveGrantRemainingDataExport c) {
		employeeId = c.getEmployeeId();
		grantDate = c.getGrantDate();
		deadline = c.getDeadline();
		expirationStatus = c.getExpirationStatus();
		registerType = c.getRegisterType();
		details = c.getDetails().clone();
	}

	/**
	 *　クローン
	 */
	public LeaveGrantRemainingDataExport clone(){
		return new LeaveGrantRemainingDataExport(this);
	}


}
