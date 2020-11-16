package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily;

import java.util.List;

public interface FixedExtractionDayConRepository {
    /**
     * getRange(List 職場のエラーアラームチェックID)
     *
     * @param errorAlarmWorkplaceId List 職場のエラーアラームチェックID
     * @return List アラームリスト（職場）日別の固定抽出条件
     */
    List<FixedExtractionDayCon> getRange(List<String> errorAlarmWorkplaceId);
}
