package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.dailyprocess.createdailyoneday.SupportDataWorkImport;
import nts.uk.ctx.at.shared.dom.adapter.dailyprocess.createdailyoneday.SupportWorkData;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
@Stateless
public class SupportDataImpl implements SupportWorkData{
	@Inject
	private CorrectionAfterChangeAttendance attendance; 
	
	@Override
	public SupportDataWorkImport correctionAfterChangeAttendance(IntegrationOfDaily integrationOfDaily) {
		SupportDataWork dataWork = attendance.correctionAfterChangeAttendance(integrationOfDaily);
		SupportDataWorkImport workImport = new SupportDataWorkImport(dataWork.getIntegrationOfDaily(), dataWork.getErrorMessageId());
		return workImport;
	}

}
