package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.ExtractResult;

import java.util.List;
import java.util.Optional;

/**
 * アラームリスト抽出結果（職場別）
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class AlarmListExtractResultWorkplaceDto {

    /**
     * アラーム値メッセージ
     */
    private String alarmValueMessage;
    /**
     * アラーム値日付
     */
    private String alarmValueDate;
    /**
     * アラーム項目名
     */
    private String alarmItemName;
    /**
     * カテゴリ名
     */
    private String categoryName;
    /**
     * チェック対象値
     */
    private String checkTargetValue;
    /**
     * 区分
     */
    private String category;
    /**
     * 開始日
     */
    private GeneralDateTime startTime;
    /**
     * コメント
     */
    private Optional<String> comment;
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

}
