package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DailyCorrectionLogParameter.DailyCorrectedItem;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DailyCorrectionLogParameter.DailyCorrectionTarget;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
@Stateless
public class DailyCorrectionLogCommandHandler extends CommandHandler<DailyCorrectionLogCommand> {

//	@Inject
//	private DailyRecordToAttendanceItemConverter dailyRecordItemConverter;
	@Override
	protected void handle(CommandHandlerContext<DailyCorrectionLogCommand> context) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.DAILY);

		List<DailyCorrectionTarget> targets = new ArrayList<>();
		DailyCorrectedItem item = new DailyCorrectedItem("AA", 111, "888", "999", 1);
		DailyCorrectionTarget tartget = new DailyCorrectionTarget(AppContexts.user().employeeId(), GeneralDate.today(), Arrays.asList(item));
		targets.add(tartget);
		val correctionLogParameter = new DailyCorrectionLogParameter(targets);
		
		DataCorrectionContext.setParameter(correctionLogParameter);

	}

	@Override
	protected void postHandle(CommandHandlerContext<DailyCorrectionLogCommand> context) {
		super.postHandle(context);
		DataCorrectionContext.transactionFinishing();
	}
	
	private Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> convertToItemValue(List<IntegrationOfDaily> domains){
		Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> result = new HashMap<>();
//		for (IntegrationOfDaily daily : domains) {
//			List<ItemValue> values = dailyRecordItemConverter.setData(daily).convert(Arrays.asList(1, 2));
//			 Map<Integer, ItemValue> map = values.stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x));
//			 result.put(Pair.of(daily.getWorkInformation().getEmployeeId(), daily.getWorkInformation().getYmd()), map);
//		}
		return result;
	}
}
