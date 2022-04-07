package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;

import java.util.List;

/**
 * アラーム抽出情報結果
 * @author viet.tx
 */
@AllArgsConstructor
@Getter
public class AlarmExtractInfoResult {
    /** アラームチェック条件No */
    private String alarmCheckConditionNo;

    /** アラームチェック条件コード */
    private AlarmCheckConditionCode alarmCheckConditionCode;

    /** アラームリストのカテゴリ */
    private AlarmCategory alarmCategory;

    /** チェック種類 */
    private AlarmListCheckType alarmListCheckType;

    /** 抽出結果 */
    @Setter
    private List<ExtractResultDetail> extractionResultDetails;

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof AlarmExtractInfoResult)) return false;
        AlarmExtractInfoResult other = (AlarmExtractInfoResult) obj;

        if(this.alarmCategory != null && other.alarmCategory == null
            || this.alarmCategory == null && other.alarmCategory != null) return false;
        if(this.alarmCheckConditionCode != null && other.alarmCheckConditionCode == null
                || this.alarmCheckConditionCode == null && other.alarmCheckConditionCode != null) return false;
        if(this.alarmCheckConditionNo != null && other.alarmCheckConditionNo == null
                || this.alarmCheckConditionNo == null && other.alarmCheckConditionNo != null) return false;

        if(this.alarmCategory != null && !this.alarmCategory.equals(other.alarmCategory)) return false;
        if(this.alarmListCheckType != other.alarmListCheckType) return false;
        if(this.alarmCheckConditionCode != null && !this.alarmCheckConditionCode.equals(other.alarmCheckConditionCode)) return false;
        if(this.alarmCheckConditionNo != null && !this.alarmCheckConditionNo.equals(other.alarmCheckConditionNo)) return false;

        return true;
    }
}
