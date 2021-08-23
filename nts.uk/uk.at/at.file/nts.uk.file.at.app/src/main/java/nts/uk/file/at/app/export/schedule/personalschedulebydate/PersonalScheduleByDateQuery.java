package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonalScheduleByDateQuery {

    /* 対象組織 */
    private int orgUnit;
    private String orgId;

    /* 年月日 */
    private GeneralDate baseDate;
//    private GeneralDate periodStart;
//    private GeneralDate periodEnd;

    /* 並び順社員リスト */
    private List<String> sortedEmployeeIds;

    // 実績表示できるか
    private boolean isDisplayActual;

    /*グラフスタート時刻  D2_2_2*/
    private int graphStartTime;

    /*グラフ休暇表示 D2_3_1 */
    private boolean graphVacationDisplay;

    /*実績グラフ表示 D2_4_1 */
    private boolean achievementGraphDisplay;

    /*勤務表示 D3_2_1 */
    private boolean workDisplay;

    /*２回勤務表示 D3_3_1 */
    private boolean doubleWorkDisplay;

    /*合計時間表示 D4_2_1 */
    private boolean TotalTimeDisplay;

    /*合計金額表示 D4_3_1 */
    private boolean totalAmountDisplay;

    /*応援予定 D5_2_1 */
    private boolean scheduledToSupport;

    /*応援者の職場名表示 D5_2_4 */
    private boolean wkpNameDisplayOfSupporters;
}
