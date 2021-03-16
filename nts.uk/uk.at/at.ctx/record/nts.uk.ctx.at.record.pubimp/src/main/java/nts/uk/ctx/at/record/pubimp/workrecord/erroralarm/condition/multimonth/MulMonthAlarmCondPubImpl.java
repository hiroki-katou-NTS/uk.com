package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.multimonth;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonAlarmCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlAtdItemConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErrorAlarmConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition.MulMonthAlarmCondPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition.MulMonthAlarmCondPubEx;
@Stateless
public class MulMonthAlarmCondPubImpl implements MulMonthAlarmCondPub  {

	@Inject
	private MulMonAlarmCheckCondRepository mulMonAlarmCheckCondRepository;
	
	@Override
	public List<MulMonthAlarmCondPubEx> getListMulMonAlarmByListEralID(List<String> listEralCheckID) {
		List<MulMonthAlarmCheckCond> data = mulMonAlarmCheckCondRepository.getMulMonAlarmsByListID(listEralCheckID);
		if(data.isEmpty())
			return Collections.emptyList();
		return data.stream().map(c->convertToMulMonthAlarmCondPub(c)).collect(Collectors.toList());
	}
	
	private MulMonthAlarmCondPubEx convertToMulMonthAlarmCondPub(MulMonthAlarmCheckCond data) {
		ErAlAtdItemConditionPubExport erAlAtdItem = new ErAlAtdItemConditionPubExport();
		erAlAtdItem = ErrorAlarmConditionPubExport.convertItemDomainToDto(
				data.getErAlAttendanceItemCondition());
		
		MulMonthAlarmCondPubEx export = new MulMonthAlarmCondPubEx(data.getCid(),
				data.getEralCheckId(),
				data.getCondNo(),
				data.getNameAlarmCon().v(),
				data.isUseAtr(),
				data.getTypeCheckItem().value,
				data.getDisplayMessage().isPresent() ? data.getDisplayMessage().get().v() : "",
				erAlAtdItem, 
				data.getContinuousMonths().isPresent() ? data.getContinuousMonths().get() : null, 
				data.getNumbers().isPresent() ? data.getNumbers().get() : null, 
				data.getCompaOperator().isPresent() ? data.getCompaOperator().get().value : null);
		
		
		return export;
	}
}
