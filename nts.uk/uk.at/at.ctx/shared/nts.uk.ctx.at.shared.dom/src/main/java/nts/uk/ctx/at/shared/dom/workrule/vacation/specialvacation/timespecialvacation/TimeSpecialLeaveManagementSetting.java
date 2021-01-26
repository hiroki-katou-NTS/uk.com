package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

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
    private TimeDigestiveUnit timeDigestiveUnit;

    // 管理区分
    private ManageDistinct manageType;
}
