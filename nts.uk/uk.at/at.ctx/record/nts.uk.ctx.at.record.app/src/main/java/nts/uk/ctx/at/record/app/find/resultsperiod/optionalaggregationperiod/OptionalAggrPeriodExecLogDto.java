package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class OptionalAggrPeriodExecLogDto {

	private String id;
	private String code;
	private String name;
	private GeneralDateTime executionDt;
	private String executorCode;
	private String executorName;
	private GeneralDate aggregationStart;
	private GeneralDate aggregationEnd;
	private String status;
	private int targetNum;
	private int errorNum;
	
	
}
