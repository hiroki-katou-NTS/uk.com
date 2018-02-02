package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 各処理の期間
 */
@Getter
@Setter
@AllArgsConstructor
public class EachProcessPeriod extends DomainObject {
	/* スケジュール作成の期間 */
	private DatePeriod scheduleCreationPeriod;
	
	/* 日別作成の期間 */
	private DatePeriod dailyCreationPeriod;
	
	/* 日別計算の期間 */
	private DatePeriod dailyCalcPeriod;
}
