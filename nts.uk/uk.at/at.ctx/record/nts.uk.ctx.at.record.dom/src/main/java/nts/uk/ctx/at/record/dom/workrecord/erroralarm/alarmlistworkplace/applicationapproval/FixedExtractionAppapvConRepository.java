package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import java.util.List;

public interface FixedExtractionAppapvConRepository {
    /**
     * get(職場のエラーアラームチェックID)
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @return List アラームリスト（職場）申請承認の固定抽出条件
     */
    List<FixedExtractionAppapvCon> get(String errorAlarmWorkplaceId);

    List<FixedExtractionAppapvCon> getByIds(List<String> ids);

    List<FixedExtractionAppapvCon> getBy(List<String> ids, boolean useAtr);

    void register(List<FixedExtractionAppapvCon> domain);

    void delete(List<String> ids);
}
