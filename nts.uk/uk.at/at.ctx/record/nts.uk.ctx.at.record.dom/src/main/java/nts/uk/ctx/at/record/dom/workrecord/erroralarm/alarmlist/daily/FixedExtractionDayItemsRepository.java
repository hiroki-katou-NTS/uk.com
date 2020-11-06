package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily;

import java.util.List;

public interface FixedExtractionDayItemsRepository {
    /**
     * get ()
     *
     * @param fixedCheckDayItems List No
     * @return List アラームリスト（職場）日別の固定抽出項目
     */
    List<FixedExtractionDayItems> get(List<FixedCheckDayItems> fixedCheckDayItems);
}
