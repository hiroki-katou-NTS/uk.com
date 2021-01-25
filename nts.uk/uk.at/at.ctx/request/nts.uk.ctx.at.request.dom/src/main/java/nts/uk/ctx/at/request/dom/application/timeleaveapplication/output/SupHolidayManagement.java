package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 60H超休管理
 */
@AllArgsConstructor
@Getter
public class SupHolidayManagement {

    // 60H超休消化単位
    private TimeDigestiveUnit super60HDigestion;

    // 60H超休管理区分
    private boolean overrest60HManagement;
    

}
