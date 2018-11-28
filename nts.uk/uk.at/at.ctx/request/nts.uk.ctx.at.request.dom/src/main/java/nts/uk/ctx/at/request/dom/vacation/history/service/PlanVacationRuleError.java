package nts.uk.ctx.at.request.dom.vacation.history.service;

import lombok.AllArgsConstructor;

/**
 * 計画年休の上限チェックの結果
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum PlanVacationRuleError {
	/**	上限日数超過 */
	OVERLIMIT(0),
	/**	期間外の利用 */
	OUTSIDEPERIOD(1);
	
	public Integer value;
}
