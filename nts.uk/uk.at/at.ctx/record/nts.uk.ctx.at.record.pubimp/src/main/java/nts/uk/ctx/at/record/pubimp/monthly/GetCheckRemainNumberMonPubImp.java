package nts.uk.ctx.at.record.pubimp.monthly;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonRepository;
import nts.uk.ctx.at.record.pub.monthly.GetCheckRemainNumberMonPub;

public class GetCheckRemainNumberMonPubImp implements GetCheckRemainNumberMonPub {

	@Inject
	CheckRemainNumberMonRepository checkRemainNumberMonRepository;
	
	@Override
	public Optional<CheckRemainNumberMon> getByEralCheckID(String errorAlarmID) {
		
		return checkRemainNumberMonRepository.getByEralCheckID(errorAlarmID);
	}

}
