package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 子看護介護管理
 */
@AllArgsConstructor
@Getter
public class ChildNursingManagement {

    // 時間介護消化単位
    private TimeDigestiveUnit timeDigestiveUnit;

    // 時間介護管理区分
    private boolean timeManagementClass;

    // 時間子看護消化単位
    private TimeDigestiveUnit timeChildDigestiveUnit;

    // 時間子看護管理区分
    private boolean timeChildManagementClass;
    

}
