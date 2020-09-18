package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 画面表示情報
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDisplayInfo {

    /** 画面表示情報*/
    private UpperLimitBeforeRaising upperContents;

    /** 時間外時間*/
    private Overtime overtime;

    /** 申請時点の時間外時間（法定休出を含む）*/
    private OvertimeIncludingHoliday overtimeIncludingHoliday;

}
