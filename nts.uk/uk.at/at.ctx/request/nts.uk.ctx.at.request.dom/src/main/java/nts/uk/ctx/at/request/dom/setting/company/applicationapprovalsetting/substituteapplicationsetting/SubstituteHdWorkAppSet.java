package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.振休振出申請設定
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubstituteHdWorkAppSet extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 振休振出同時申請設定
     */
    private SubstituteSimultaneousAppSet simultaneousSetting;

    /**
     * 振休申請設定
     */
    private SubstituteHolidayAppSet substituteHolidaySetting;

    /**
     * 振出申請設定
     */
    private SubstituteWorkAppSet substituteWorkSetting;

}
