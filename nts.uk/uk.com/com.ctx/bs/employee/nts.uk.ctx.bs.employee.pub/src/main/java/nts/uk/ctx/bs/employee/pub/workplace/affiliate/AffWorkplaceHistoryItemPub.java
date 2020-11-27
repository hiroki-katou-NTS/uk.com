package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 期間と職場一覧から所属職場履歴項目を取得する
 */
public interface AffWorkplaceHistoryItemPub {

	List<AffWorkplaceHistoryItemExport> getListAffWkpHistItem(DatePeriod basedate, List<String> workplaceId);

}
