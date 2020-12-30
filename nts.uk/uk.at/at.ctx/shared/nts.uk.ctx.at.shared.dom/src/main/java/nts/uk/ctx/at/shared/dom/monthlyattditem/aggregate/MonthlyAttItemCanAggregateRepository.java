package nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate;

import java.util.List;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttItemId;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.月次の勤怠項目.Repository.集計可能な月次の勤怠項目Repository.集計可能な月次の勤怠項目Repository
 * 
 * @author LienPTK
 */
public interface MonthlyAttItemCanAggregateRepository {

	/**
	 * 可能リストを取得する(会社ID)
	 *
	 * @param cid 会社ID
	 * @return the monthly attendance item can aggregate
	 */
	public List<MonthlyAttItemId> getMonthlyAtdItemCanAggregate(String cid);

	/**
	 * 不可能リストを取得する(会社ID)
	 *
	 * @param cid 会社ID
	 * @return the monthly atd item can not aggregate
	 */
	public List<MonthlyAttItemId> getMonthlyAtdItemCanNotAggregate(String cid);

}
