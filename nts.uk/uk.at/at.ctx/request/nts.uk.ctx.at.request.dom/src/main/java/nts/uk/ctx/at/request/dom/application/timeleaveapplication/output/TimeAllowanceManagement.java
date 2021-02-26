package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 時間代休管理
 */
@AllArgsConstructor
@Getter
public class TimeAllowanceManagement {

    // 時間代休消化単位
    private TimeDigestiveUnit timeBaseRestingUnit;

    // 時間代休管理区分
    private boolean timeBaseManagementClass;
    

}
