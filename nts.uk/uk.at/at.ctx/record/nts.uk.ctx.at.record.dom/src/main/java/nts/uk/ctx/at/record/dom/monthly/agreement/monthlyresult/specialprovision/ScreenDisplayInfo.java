package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 画面表示情報
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDisplayInfo extends ValueObject {

    /** 時間外時間*/
    private Overtime overtime;

    /** 時間外時間（法定休出を含む）*/
    private OvertimeIncludingHoliday overtimeIncludingHoliday;

    /** 超過月数 */
    private int exceededMonth;

    /** 上限マスタ内容*/
    private UpperLimitBeforeRaising upperContents;

    /**
     * [C-0] 画面表示情報 (時間外時間,時間外時間（法定休出を含む）,超過月数,上限マスタ内容)
     */
    public static ScreenDisplayInfo create(Overtime overtime, OvertimeIncludingHoliday overtimeIncludingHoliday,
                                           int exceededMonth,UpperLimitBeforeRaising upperContents) {

        return new ScreenDisplayInfo(overtime, overtimeIncludingHoliday,exceededMonth,upperContents);
    }

}
