/**
 * 11:17:33 AM Mar 28, 2018
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition;

import java.util.List;
import java.util.Optional;

/**
 * @author hungnm
 *
 */
public interface MonthlyCorrectConditionRepository {
	
	Optional<MonthlyCorrectExtractCondition> findMonthlyConditionByCode(String errCode);

	List<MonthlyCorrectExtractCondition> findAllMonthlyConditionByCompanyId();
	
	List<MonthlyCorrectExtractCondition> findUseMonthlyConditionByCompanyId();

	Optional<TimeItemCheckMonthly> findTimeItemCheckMonthlyById(String checkId, String errorAlarmCode);

	MonthlyCorrectExtractCondition updateMonthlyCorrectExtractCondition(MonthlyCorrectExtractCondition domain);

	void updateTimeItemCheckMonthly(TimeItemCheckMonthly domain);

	void deleteMonthlyCorrectExtractCondition(String errorCd);
	
	MonthlyCorrectExtractCondition createMonthlyCorrectExtractCondition(MonthlyCorrectExtractCondition domain);

	void createTimeItemCheckMonthly(TimeItemCheckMonthly domain);
	
	void removeTimeItemCheckMonthly(String errorAlarmCheckID);
}
