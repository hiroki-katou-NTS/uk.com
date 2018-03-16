package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.record.dom.remainingnumber.base.SpecialVacationCD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 特別休暇付与残数データ
public class SpecialLeaveGrantRemainingData extends AggregateRoot {
	// 社員ID
	private String employeeId;
	// 特別休暇コード
	private SpecialVacationCD specialLeaveCode;
	// 付与日
	private GeneralDate grantDate;
	// 期限日
	private GeneralDate expiredDate;
	// 期限切れ状態
	private LeaveExpirationStatus expirationStatus;
	// 登録種別
	private GrantRemainRegisterType registerType;
	// 明細
	private SpecialLeaveNumberInfo details;

}
