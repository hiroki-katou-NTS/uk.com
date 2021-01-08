package nts.uk.ctx.at.shared.pub.monthlyattendanceitem.aggregate;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.Export.集計可能な月次の勤怠項目を取得する
 * 
 * @author LienPTK
 *
 */
public interface MonthlyAttItemCanAggregatePub {

	/**
	 * Gets the monthly atd can be aggregate.
	 *
	 * @param cid the cid
	 * @return the monthly atd can be aggregate
	 */
	public List<Integer> getMonthlyAtdCanBeAggregate(String cid);
}
