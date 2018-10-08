package nts.uk.screen.at.app.dailymodify.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class InsertAllData {

	@Inject
	private DailyRecordWorkCommandHandler hander;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void handlerInsertAll(List<DailyRecordWorkCommand> commandNew, List<IntegrationOfDaily> domainDailyNew,
			List<DailyRecordWorkCommand> commandOld, List<DailyItemValue> dailyItems,
			List<IntegrationOfMonthly> lstMonthDomain, boolean isUpdate) {
		hander.handlerInsertAll(commandNew, domainDailyNew, commandOld, dailyItems, lstMonthDomain, isUpdate);
	}
}
