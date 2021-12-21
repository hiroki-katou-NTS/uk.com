package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.休暇.特別休暇.時間特別休暇
 * 時間特別休暇の管理設定
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeSpecialLeaveManagementSetting extends AggregateRoot {
    // 会社ID
    private String companyId;

    // 時間休暇消化単位
    private TimeVacationDigestUnit timeVacationDigestUnit;
    
    /**
     * [3]時間休暇が管理するか
     * @param require
     */
    public boolean isManageTimeVacation(TimeVacationDigestUnit.Require require) {
    	return this.timeVacationDigestUnit.isVacationTimeManage(require);
    }
    
    /**
     * [4]利用する休暇時間の消化単位をチェックする
     * @param require
     * @param time 休暇使用時間
     */
    public boolean checkVacationTimeUnitUsed(TimeVacationDigestUnit.Require require, AttendanceTime time) {
		return this.timeVacationDigestUnit.checkDigestUnit(require, time);
	}
}
