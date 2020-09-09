package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;

/**
 * refactor4 refactor 4
 * 振休振出同時申請設定
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubstituteSimultaneousAppSet {
    /**
     * 同時申請必須
     */
    private boolean simultaneousApplyRequired;

    /**
     * 振休先取許可
     */
//    private ApplyPermission allowanceForAbsence;
}
