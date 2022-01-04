package nts.uk.ctx.at.record.dom.daily.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;

import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.日別時間帯別実績.Export
 * 応援勤務データ
 */

@AllArgsConstructor
@Getter
public class SupportWorkData {
    /**
     * 日別実績の応援作業別勤怠時間
     */
    private List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList;

    /**
     * 日別実績の応援作業別勤怠時間帯
     */
    private List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList;
    /**
     * 所属情報
     */
    private List<AffiliationInforOfDailyPerfor> affiliationInforList;
}
