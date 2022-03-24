package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author anhnm
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpeHolidayRemainInfo {

    // 管理する
    private boolean manage;
    
    // 特別休暇コード
    private int specialHolidayCode;
}
