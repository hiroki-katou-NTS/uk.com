package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckConValueRemainingNumber;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckOperatorType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CheckConValueRemainingNumberEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonPubEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CompareRangeEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CompareSingleValueEx;

@Stateless
public class CheckRemainNumberMonPubImpl implements CheckRemainNumberMonPub {

	@Inject
	private CheckRemainNumberMonRepository repo;

	@Override
	public CheckRemainNumberMonPubEx getByEralCheckID(String errorAlarmID) {
		Optional<CheckRemainNumberMon> data = repo.getByEralCheckID(errorAlarmID);
		if (data.isPresent()) {
			return fromDomain(data.get());
		}
		return null;
	}

	private CheckRemainNumberMonPubEx fromDomain(CheckRemainNumberMon domain) {
		CheckRemainNumberMonPubEx checkRemainNumberMonPubEx = new CheckRemainNumberMonPubEx();
		if(domain.getCheckOperatorType() == CheckOperatorType.SINGLE_VALUE) {	
			CompareSingleValue<CheckConValueRemainingNumber> compareSingleValue = (CompareSingleValue<CheckConValueRemainingNumber>) domain.getCheckCondition();
			checkRemainNumberMonPubEx = new CheckRemainNumberMonPubEx(
					domain.getErrorAlarmCheckID(),
					domain.getCheckVacation().value,
					domain.getCheckOperatorType().value,
					null,
					new CompareSingleValueEx(
							compareSingleValue.getCompareOpertor().value,
							new CheckConValueRemainingNumberEx(
									compareSingleValue.getValue().getDaysValue(),
									compareSingleValue.getValue().getTimeValue()==null?null:(!compareSingleValue.getValue().getTimeValue().isPresent()?null:compareSingleValue.getValue().getTimeValue().get())
									)
							),
					!domain.getListAttdID().isPresent()?Collections.emptyList():domain.getListAttdID().get()
					);
		}else {
			CompareRange<CheckConValueRemainingNumber> compareRange = (CompareRange<CheckConValueRemainingNumber>) domain.getCheckCondition();
			checkRemainNumberMonPubEx = new CheckRemainNumberMonPubEx(
					domain.getErrorAlarmCheckID(),
					domain.getCheckVacation().value,
					domain.getCheckOperatorType().value,
					new CompareRangeEx(
							compareRange.getCompareOperator().value,
							new CheckConValueRemainingNumberEx(
									compareRange.getStartValue().getDaysValue(),
									compareRange.getStartValue().getTimeValue()==null?null:(!compareRange.getStartValue().getTimeValue().isPresent()?null:compareRange.getStartValue().getTimeValue().get())
									),
							new CheckConValueRemainingNumberEx(
									compareRange.getEndValue().getDaysValue(),
									compareRange.getEndValue().getTimeValue()==null?null:(!compareRange.getEndValue().getTimeValue().isPresent()?null:compareRange.getEndValue().getTimeValue().get())
									)
							),
					null,
					!domain.getListAttdID().isPresent()?Collections.emptyList():domain.getListAttdID().get()
					);
		}
		
		
		return checkRemainNumberMonPubEx;
	}

}
