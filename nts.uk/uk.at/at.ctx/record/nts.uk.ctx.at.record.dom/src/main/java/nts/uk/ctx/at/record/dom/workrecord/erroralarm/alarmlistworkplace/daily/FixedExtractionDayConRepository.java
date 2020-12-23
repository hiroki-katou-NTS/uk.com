package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily;

import java.util.List;

public interface FixedExtractionDayConRepository {
    /**
     * getRange(List 職場のエラーアラームチェックID)
     *
     * @param ids List 職場のエラーアラームチェックID
     * @return List アラームリスト（職場）日別の固定抽出条件
     */
    List<FixedExtractionDayCon> getRange(List<String> ids);

    List<FixedExtractionDayCon> getBy(List<String> ids, boolean useAtr);

    void register(List<FixedExtractionDayCon> domain);

    void delete(List<String> ids);
}
