package nts.uk.ctx.at.function.dom.alarmworkplace.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * data export excel : アラームリスト抽出結果（職場別）
 */
@Getter
@AllArgsConstructor
public class AlarmListExtractResultWorkplaceData {

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
    private int category;
    /**
     * 開始日
     */
    private GeneralDateTime startTime;
    /**
     * コメント
     */
    private String comment;
    /**
     * 職場ID
     */
    private String workplaceId;

    /**
     * 職場コード
     */
    private String workplaceCode;

    /**
     * 職場名
     */
    private String workplaceName;

}
