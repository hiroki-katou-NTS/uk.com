package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 半日勤務を設定する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.就業時間帯.半日勤務を設定する
 * @author anhnm
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class HalfDayWorkSet {

    /*
     * 勤務時間帯
     */
    private boolean workingTimes;
    
    /*
     * 残業時間帯
     */
    private boolean overTime;
    
    /*
     * 休憩時間帯
     */
    private boolean breakTime;
}
