package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import lombok.*;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersisAlarmExtractResultDto {
    /** アラームリストパターンコード */
    private String alarmPatternCode;

    /** アラームリストパターン名称 */
    private String alarmPatternName;

    /** 会社ID */
    private String companyID;

    /** 自動実行コード  (手動実行の場合自動実行コード＝　’Z’) */
    private String autoRunCode;

    private String employeeID;

    private String alarmCheckConditionNo;

    /** アラームチェック条件コード */
    private String alarmCheckConditionCode;

    /** アラームリストのカテゴリ */
    private int alarmCategory;

    /** チェック種類 */
    private int alarmListCheckType;

    private String startDate;

    private String endDate;

    /**アラーム項目	 */
//    private String alarmName;
    /**	アラーム内容 */
    private String alarmContent;
    /**	発生日時 */
    private GeneralDateTime runTime;
    /**職場ID	 */
    private String wpID;
    /**	コメント */
    private String message;
    /**  チェック対象値   */
    private String checkValue;
}
