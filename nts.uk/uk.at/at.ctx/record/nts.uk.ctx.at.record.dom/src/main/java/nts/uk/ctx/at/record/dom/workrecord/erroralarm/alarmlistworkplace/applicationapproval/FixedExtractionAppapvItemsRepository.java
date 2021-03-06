package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import java.util.List;

public interface FixedExtractionAppapvItemsRepository {
    /**
     * getAll ()
     *
     * @return List アラームリスト（職場）申請承認の固定抽出項目
     */
    List<FixedExtractionAppapvItems> getAll();

    List<FixedExtractionAppapvItems> getBy(List<CheckItemAppapv> nos);
}
