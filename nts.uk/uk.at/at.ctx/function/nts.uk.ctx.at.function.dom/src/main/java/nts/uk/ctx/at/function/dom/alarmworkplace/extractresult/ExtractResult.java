package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionName;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.抽出結果
 * 抽出結果
 *
 * @author Le Huu Dat
 */
@Getter
public class ExtractResult {

    /**
     * アラーム値メッセージ
     */
    private AlarmValueMessage alarmValueMessage;

    /**
     * アラーム値日付
     */
    private AlarmValueDate alarmValueDate;

    /**
     * アラーム項目名
     */
    private AlarmCheckConditionName alarmItemName;

    /**
     * チェック対象値
     */
    private String checkTargetValue;

    /**
     * コメント
     */
    private Optional<MessageDisplay> comment;

    /**
     * 職場ID
     */
    private Optional<String> workplaceId;

    /**
     * 職場コード
     */
    private Optional<String> workplaceCode;

    /**
     * 職場名
     */
    private Optional<String> workplaceName;

    /**
     * 階層コード
     */
    private Optional<String> hierarchyCode;

    public ExtractResult(String alarmValueMessage, String startDate, String endDate, String alarmItemName,
                         String checkTargetValue, String comment, String workplaceId) {
        this.alarmValueMessage = new AlarmValueMessage(alarmValueMessage);
        this.alarmValueDate = new AlarmValueDate(startDate, Optional.ofNullable(endDate));
        this.alarmItemName = new AlarmCheckConditionName(alarmItemName);
        this.checkTargetValue = checkTargetValue;
        this.comment = comment == null ? Optional.empty() : Optional.of(new MessageDisplay(comment));
        this.workplaceId = Optional.ofNullable(workplaceId);
        this.workplaceCode = Optional.empty();
        this.workplaceName = Optional.empty();
        this.hierarchyCode = Optional.empty();
    }

    public void setWorkplaceInfo(String workplaceCode, String workplaceName, String hierarchyCode) {
        this.workplaceCode = Optional.ofNullable(workplaceCode);
        this.workplaceName = Optional.ofNullable(workplaceName);
        this.hierarchyCode = Optional.ofNullable(hierarchyCode);
    }

    public ExtractResult(String alarmValueMessage, String startDate, String endDate, String alarmItemName,
                         String checkTargetValue, String comment, String workplaceId,
                         String workplaceCode, String workplaceName, String hierarchyCode) {
        this.alarmValueMessage = new AlarmValueMessage(alarmValueMessage);
        this.alarmValueDate = new AlarmValueDate(startDate, Optional.ofNullable(endDate));
        this.alarmItemName = new AlarmCheckConditionName(alarmItemName);
        this.checkTargetValue = checkTargetValue;
        this.comment = comment == null ? Optional.empty() : Optional.of(new MessageDisplay(comment));
        this.workplaceId = Optional.ofNullable(workplaceId);
        this.workplaceCode = Optional.ofNullable(workplaceCode);
        this.workplaceName = Optional.ofNullable(workplaceName);
        this.hierarchyCode = Optional.ofNullable(hierarchyCode);
    }
}
