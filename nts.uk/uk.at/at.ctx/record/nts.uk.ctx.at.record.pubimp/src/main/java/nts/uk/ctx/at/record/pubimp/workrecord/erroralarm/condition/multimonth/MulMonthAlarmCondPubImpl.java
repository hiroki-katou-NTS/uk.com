package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.multimonth;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
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
	
	private MulMonthAlarmCondPubEx convertToMulMonthAlarmCondPub(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		ErAlAtdItemConditionPubExport erAlAtdItem = new ErAlAtdItemConditionPubExport();
		int continuousMonths = 0;
		int times = 0;
		int compareOperator = 0;
		boolean isUsed = false;
		
		int typeCheckItem = mulMonthAlarmCheckCond.getTypeCheckItem().value;
		
		if (typeCheckItem == TypeCheckWorkRecordMultipleMonth.TIME.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.TIMES.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.AMOUNT.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.DAYS.value) {
			if (mulMonthAlarmCheckCond.getMulMonthCheckCond().isPresent()) {
				erAlAtdItem = ErrorAlarmConditionPubExport.convertItemDomainToDto(
						mulMonthAlarmCheckCond.getMulMonthCheckCond().get().getErAlAttendanceItemCondition());
				isUsed = mulMonthAlarmCheckCond.getMulMonthCheckCond().get().isUsedFlg();
			}
		}
		else if (typeCheckItem == TypeCheckWorkRecordMultipleMonth.AVERAGE_TIME.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.AVERAGE_TIMES.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.AVERAGE_AMOUNT.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.AVERAGE_DAYS.value) {
			if (mulMonthAlarmCheckCond.getMulMonthCheckCondAverage().isPresent()) {
				erAlAtdItem = ErrorAlarmConditionPubExport.convertItemDomainToDto(
						mulMonthAlarmCheckCond.getMulMonthCheckCondAverage().get().getErAlAttendanceItemCondition());
				isUsed = mulMonthAlarmCheckCond.getMulMonthCheckCondAverage().get().isUsedFlg();
			}
		}
		
		else if (typeCheckItem == TypeCheckWorkRecordMultipleMonth.CONTINUOUS_TIME.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.CONTINUOUS_TIMES.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.CONTINUOUS_AMOUNT.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.CONTINUOUS_DAYS.value) {
			if (mulMonthAlarmCheckCond.getMulMonthCheckCondContinue().isPresent()) {
				erAlAtdItem = ErrorAlarmConditionPubExport.convertItemDomainToDto(
						mulMonthAlarmCheckCond.getMulMonthCheckCondContinue().get().getErAlAttendanceItemCondition());
				continuousMonths = mulMonthAlarmCheckCond.getMulMonthCheckCondContinue().get().getContinuousMonths();
				isUsed = mulMonthAlarmCheckCond.getMulMonthCheckCondContinue().get().isUsedFlg();
			}
		}
		
		else if (typeCheckItem == TypeCheckWorkRecordMultipleMonth.NUMBER_TIME.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.NUMBER_TIMES.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.NUMBER_AMOUNT.value
				|| typeCheckItem == TypeCheckWorkRecordMultipleMonth.NUMBER_DAYS.value) {
			if (mulMonthAlarmCheckCond.getMulMonthCheckCondCosp().isPresent()) {
				erAlAtdItem = ErrorAlarmConditionPubExport.convertItemDomainToDto(
						mulMonthAlarmCheckCond.getMulMonthCheckCondCosp().get().getErAlAttendanceItemCondition());
				times = mulMonthAlarmCheckCond.getMulMonthCheckCondCosp().get().getTimes();
				compareOperator = mulMonthAlarmCheckCond.getMulMonthCheckCondCosp().get().getCompareOperator();
				isUsed = mulMonthAlarmCheckCond.getMulMonthCheckCondCosp().get().isUsedFlg();
			}
		}		
		return new MulMonthAlarmCondPubEx(mulMonthAlarmCheckCond.getErrorAlarmCheckID(),
				mulMonthAlarmCheckCond.getNameAlarmCon().v(), isUsed, mulMonthAlarmCheckCond.getTypeCheckItem().value,
				mulMonthAlarmCheckCond.getHowDisplayMessage().isMessageBold(),
				!mulMonthAlarmCheckCond.getHowDisplayMessage().getMessageColor().isPresent() ? null
						: mulMonthAlarmCheckCond.getHowDisplayMessage().getMessageColor().get(),
				!mulMonthAlarmCheckCond.getDisplayMessage().isPresent() ? null
						: mulMonthAlarmCheckCond.getDisplayMessage().get().v(),
				erAlAtdItem,
				continuousMonths, times, compareOperator);
	}
}
