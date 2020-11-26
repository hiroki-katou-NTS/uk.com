package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".基幹.社員.職場.所属職場履歴.アルゴリズム.Query.期間と職場一覧から所属職場履歴項目を取得する
 */
public interface AffWorkplaceHistoryItemPub {

	List<AffWorkplaceHistoryItemExport> getListAffWkpHistItem(DatePeriod basedate, List<String> workplaceId);

}
