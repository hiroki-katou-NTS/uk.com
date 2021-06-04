package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmCheckConditionCode;

@AllArgsConstructor
@Getter
public class AlarmExtractInfoKey {
    private String alarmCheckConditionNo;

    /** アラームチェック条件コード */
    private AlarmCheckConditionCode alarmCheckConditionCode;

    /** アラームリストのカテゴリ */
    private AlarmCategory alarmCategory;

    /** チェック種類 */
    private AlarmListCheckType alarmListCheckType;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alarmCheckConditionNo != null ? alarmCheckConditionNo.hashCode() : 0);
        hash += (alarmCheckConditionCode != null ? alarmCheckConditionCode.v().hashCode() : 0);
        hash += (alarmCategory != null ? alarmCategory.hashCode() : 0);
        hash += (alarmListCheckType != null ? alarmListCheckType.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AlarmExtractInfoKey)) {
            return false;
        }
        AlarmExtractInfoKey other = (AlarmExtractInfoKey) object;
        if ((this.alarmCheckConditionNo == null && other.alarmCheckConditionNo != null)
                || (this.alarmCheckConditionNo != null && !this.alarmCheckConditionNo.equals(other.alarmCheckConditionNo))) {
            return false;
        }
        if ((this.alarmCheckConditionCode == null && other.alarmCheckConditionCode != null)
                || (this.alarmCheckConditionCode != null && !this.alarmCheckConditionCode.v().equals(other.alarmCheckConditionCode.v()))) {
            return false;
        }
        if ((this.alarmCategory == null && other.alarmCategory != null)
                || (this.alarmCategory != null && this.alarmCategory.value != other.alarmCategory.value)) {
            return false;
        }
        if ((this.alarmListCheckType == null && other.alarmListCheckType != null)
                || (this.alarmListCheckType != null && this.alarmListCheckType.value != other.alarmListCheckType.value)) {
            return false;
        }
        return true;
    }
}
