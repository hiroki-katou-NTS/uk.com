package nts.uk.ctx.at.record.pub.daily.supportwork;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.日別時間帯別実績.Export
 * 応援勤務データ
 */

@AllArgsConstructor
@Getter
public class SupportWorkDataExport {
    /**
     * 日別実績の応援作業別勤怠時間
     */
    private List<OuenWorkTimeOfDailyExport> ouenWorkTimeOfDailyList;

    /**
     * 日別実績の応援作業別勤怠時間帯
     */
    private List<OuenWorkTimeSheetOfDailyExport> ouenWorkTimeSheetOfDailyList;
    /**
     * 所属情報
     */
    private List<AffiliationInforOfDailyPerforExport> affiliationInforList;
}
