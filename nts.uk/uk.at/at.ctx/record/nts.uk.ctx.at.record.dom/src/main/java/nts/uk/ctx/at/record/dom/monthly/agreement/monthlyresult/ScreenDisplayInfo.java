package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 画面表示情報
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class ScreenDisplayInfo {

    /** 画面表示情報*/
    private UpperLimitBeforeRaising upperContents;

    /** 時間外時間*/
    private Overtime overtime;

    /** 申請時点の時間外時間（法定休出を含む）*/
    private OvertimeIncludingHoliday overtimeIncludingHoliday;

}
