package nts.uk.ctx.at.function.dom.adapter.supportworkdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SupportWorkDataImport {
    /**
     * 日別実績の応援作業別勤怠時間
     */
    private List<OuenWorkTimeOfDailyImport> ouenWorkTimeOfDailyList;

    /**
     * 日別実績の応援作業別勤怠時間帯
     */
    private List<OuenWorkTimeSheetOfDailyImport> ouenWorkTimeSheetOfDailyList;
    /**
     * 所属情報
     */
    private List<AffiliationInforOfDailyPerforImport> affiliationInforList;
}
