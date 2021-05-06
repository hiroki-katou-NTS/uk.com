package nts.uk.ctx.at.shared.dom.alarmList.extractionResult.toppage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtractResultDetail {
    /**アラーム値日付	 */
    private ExtractionAlarmPeriodDate periodDate;
    /**アラーム項目	 */
    private String alarmName;
    /**	アラーム内容 */
    private String alarmContent;
    /**	発生日時 */
    private GeneralDateTime runTime;
    /**職場ID	 */
    private Optional<String> wpID;
    /**	コメント */
    private Optional<String> message;
    /**  チェック対象値   */
    private Optional<String> checkValue;
}
