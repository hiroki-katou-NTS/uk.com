package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.SpecHolidayCheckConPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.SpecHolidayCheckConPubEx;

@Stateless
public class SpecHolidayCheckConAcFinder implements SpecHolidayCheckConFunAdapter {

	@Inject
	private SpecHolidayCheckConPub repo;
	
	@Override
	public SpecHolidayCheckConFunImport getSpecHolidayCheckConById(String errorAlarmCheckID) {
		SpecHolidayCheckConPubEx data = repo.getSpecHolidayCheckConById(errorAlarmCheckID);
		if(data == null)
			return null;
		return convertToExport(data);
	}
	
	private SpecHolidayCheckConFunImport convertToExport(SpecHolidayCheckConPubEx export) {
		return new SpecHolidayCheckConFunImport(
				export.getErrorAlarmCheckID(),
				export.getCompareOperator(),
				export.getNumberDayDiffHoliday1(),
				export.getNumberDayDiffHoliday2()
				);
	}

}
