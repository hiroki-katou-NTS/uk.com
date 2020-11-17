package nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 抽出結果
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class ExtractResultExport {

    /**
     * アラーム値メッセージ
     */
    private String alarmValueMessage;

    /**
     * アラーム値日付
     */
    private int startDate;

    /**
     * アラーム値日付
     */
    private Integer endDate;

    /**
     * アラーム項目名
     */
    private String alarmItemName;

    /**
     * チェック対象値
     */
    private String checkTargetValue;

    /**
     * コメント
     */
    private String comment;

    /**
     * 職場ID
     */
    private String workplaceId;

}
