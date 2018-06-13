package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckConRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.SpecHolidayCheckConPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.SpecHolidayCheckConPubEx;

@Stateless
public class SpecHolidayCheckConPubImpl implements SpecHolidayCheckConPub {
	
	@Inject
	private SpecHolidayCheckConRepository repo;
	
	@Override
	public SpecHolidayCheckConPubEx getSpecHolidayCheckConById(String errorAlarmCheckID) {
		Optional<SpecHolidayCheckCon> data = repo.getSpecHolidayCheckConById(errorAlarmCheckID);
		if(data.isPresent()) {
			return fromDomain(data.get());
		}
		return null;
	}
	
	private SpecHolidayCheckConPubEx fromDomain(SpecHolidayCheckCon domain) {
		return new SpecHolidayCheckConPubEx(
				domain.getErrorAlarmCheckID(),
				domain.getCompareOperator(),
				domain.getNumberDayDiffHoliday1().v().intValue(),
				!domain.getNumberDayDiffHoliday2().isPresent()?null:domain.getNumberDayDiffHoliday2().get().v().intValue()
				);
	}

}
