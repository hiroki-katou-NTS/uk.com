package nts.uk.ctx.at.shared.dom.adapter.workplace.affiliate;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface SharedAffWorkplaceHistoryItemAdapter {

    /**
     * 期間と職場一覧から所属職場履歴項目を取得する
     *
     * @param period      期間
     * @param workplaceId List<職場ID>
     * @return List<所属職場履歴項目>
     */
    List<AffWorkplaceHistoryItemImport> getListAffWkpHistItem(DatePeriod period, List<String> workplaceId);
}
