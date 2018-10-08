package nts.uk.screen.at.app.dailymodify.command;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DPAttendanceItemRC;
import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthDailyParam;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class InsertAllData {

	@Inject
	private DailyRecordWorkCommandHandler hander;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handlerInsertAll(List<DailyRecordWorkCommand> commandNew, List<IntegrationOfDaily> domainDailyNew,
			List<DailyRecordWorkCommand> commandOld, List<DailyItemValue> dailyItems,
			List<IntegrationOfMonthly> lstMonthDomain, boolean isUpdate, UpdateMonthDailyParam month, Map<Integer, DPAttendanceItemRC> itemAtr) {
		hander.handlerInsertAll(commandNew, domainDailyNew, commandOld, dailyItems, lstMonthDomain, isUpdate, month, itemAtr);
	}
}
