package nts.uk.ctx.at.record.app.command.dailyperform.checkdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RCDailyCorrectionResult {

	private List<IntegrationOfDaily> lstDailyDomain;
	
	private List<IntegrationOfMonthly> lstMonthDomain;
	
	private List<DailyRecordWorkCommand> commandNew;
	
	private List<DailyRecordWorkCommand> commandOld;
	
	private List<DailyItemValue> dailyItems;
	
	private boolean update;
}
