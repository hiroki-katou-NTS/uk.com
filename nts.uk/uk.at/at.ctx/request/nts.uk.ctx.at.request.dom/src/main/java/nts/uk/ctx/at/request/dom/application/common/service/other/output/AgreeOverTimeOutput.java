package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import nts.arc.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;

/**
 * 申請用時間外労働時間パラメータ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreeOverTimeOutput {
	
	/**
	 * 当月の時間外時間
	 */
	private AgreementTimeImport detailCurrentMonth;
	
	/**
	 * 翌月の時間外時間
	 */
	private AgreementTimeImport detailNextMonth;
	
	/**
	 * 当月の年月
	 */
	private YearMonth currentMonth;
	
	/**
	 * 翌月の年月
	 */
	private YearMonth nextMonth;
}
