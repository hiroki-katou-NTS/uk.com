package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.multimonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlAtdItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.multimonth.MultiMonthFucAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlAtdItemConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition.MulMonthAlarmCondPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition.MulMonthAlarmCondPubEx;

@Stateless
public class MultiMonthAlarmAcFinder implements MultiMonthFucAdapter {

	@Inject
	private MulMonthAlarmCondPub repo;
	
	@Override
	public List<MulMonCheckCondDomainEventDto> getListMultiMonCondByListEralID(List<String> listEralCheckID) {
		List<MulMonCheckCondDomainEventDto> data = repo.getListMulMonAlarmByListEralID(listEralCheckID)
				.stream().map(c -> convertToExport(c)).collect(Collectors.toList());
		return data;
	}
	
	private MulMonCheckCondDomainEventDto convertToExport(MulMonthAlarmCondPubEx export) {
		return new MulMonCheckCondDomainEventDto(
				export.getErrorAlarmCheckID(), 
				export.getNameAlarmMulMon(),
				export.isUseAtr(),
				export.getTypeCheckItem(), 
				export.isMessageBold(), 
				export.getMessageColor(),
				export.getDisplayMessage(),
				convertToErAlAtdItemCon(export.getErAlAtdItem()), 
				export.getContinuousMonths(),
				export.getTimes(),
				export.getCompareOperator());
	}
	
	public static ErAlAtdItemConAdapterDto convertToErAlAtdItemCon(ErAlAtdItemConditionPubExport export) {
		return new ErAlAtdItemConAdapterDto(export.getTargetNO(), export.getConditionAtr(), export.isUseAtr(),
				export.getUncountableAtdItem(), export.getCountableAddAtdItems(), export.getCountableSubAtdItems(),
				export.getConditionType(), export.getCompareOperator(), export.getSingleAtdItem(),
				export.getCompareStartValue(), export.getCompareEndValue(), export.getInputCheckCondition());
	}
}
