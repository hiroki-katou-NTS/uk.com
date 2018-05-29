package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlConditionsAttendanceItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.AttendanceItemConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlConditionsAttendanceItemPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErrorAlarmConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.ExtraResultMonthlyPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.ExtraResultMonthlyPubEx;
@Stateless
public class ExtraResultMonthlyPubImpl implements ExtraResultMonthlyPub  {

	@Inject
	private ExtraResultMonthlyRepository extraResultMonthlyRepo;
	
	@Override
	public List<ExtraResultMonthlyPubEx> getListExtraResultMonByListEralID(List<String> listEralCheckID) {
		List<ExtraResultMonthly> data = extraResultMonthlyRepo.getExtraResultMonthlyByListID(listEralCheckID);
		if(data.isEmpty())
			return Collections.emptyList();
		return data.stream().map(c->convertToExtraResultMonthly(c)).collect(Collectors.toList());
	}
	
	private ExtraResultMonthlyPubEx convertToExtraResultMonthly(ExtraResultMonthly extraResultMonthly) {
		return new ExtraResultMonthlyPubEx(
		extraResultMonthly.getErrorAlarmCheckID(),
		extraResultMonthly.getSortBy(),
		extraResultMonthly.getNameAlarmExtraCon().v(),
		extraResultMonthly.isUseAtr(),
		extraResultMonthly.getTypeCheckItem().value,
		extraResultMonthly.getHowDisplayMessage().isMessageBold(),
		!extraResultMonthly.getHowDisplayMessage().getMessageColor().isPresent()?null
				:extraResultMonthly.getHowDisplayMessage().getMessageColor().get(),
		!extraResultMonthly.getDisplayMessage().isPresent()?null:extraResultMonthly.getDisplayMessage().get().v(),
		!extraResultMonthly.getCheckConMonthly().isPresent()?null:convertToAttendanceItemCon(extraResultMonthly.getCheckConMonthly().get()	));
	}
	
	private AttendanceItemConditionPubExport convertToAttendanceItemCon(AttendanceItemCondition attendanceItemCon) {
		return new AttendanceItemConditionPubExport(
				attendanceItemCon.getOperatorBetweenGroups().value,
				convertToErAlConditionsAttd(attendanceItemCon.getGroup1()),
				attendanceItemCon.getGroup2() == null?null:convertToErAlConditionsAttd(attendanceItemCon.getGroup2()),
				attendanceItemCon.getGroup2UseAtr().booleanValue()
				);
	}
	private ErAlConditionsAttendanceItemPubExport convertToErAlConditionsAttd(ErAlConditionsAttendanceItem erAlConditionsAttd) {
		return new ErAlConditionsAttendanceItemPubExport(
				erAlConditionsAttd.getAtdItemConGroupId(),
				erAlConditionsAttd.getConditionOperator().value,
				erAlConditionsAttd.getLstErAlAtdItemCon().stream().map(c->ErrorAlarmConditionPubExport.convertItemDomainToDto(c)).collect(Collectors.toList())
			
				);
		
	}

}
