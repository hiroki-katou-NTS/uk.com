package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval;

import java.util.List;

public interface FixedExtractionAppapvConRepository {
    /**
     * get(職場のエラーアラームチェックID)
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @return List アラームリスト（職場）申請承認の固定抽出条件
     */
    List<FixedExtractionAppapvCon> get(String errorAlarmWorkplaceId);
}
