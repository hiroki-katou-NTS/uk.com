package nts.uk.ctx.at.shared.ac.dailyprocess.createdailyoneday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.dailyprocess.createdailyoneday.SupportDataWorkImport;
import nts.uk.ctx.at.shared.dom.adapter.dailyprocess.createdailyoneday.SupportWorkAdapter;
import nts.uk.ctx.at.shared.dom.adapter.dailyprocess.createdailyoneday.SupportWorkData;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@Stateless
public class SupportWorkAdapterImpl implements SupportWorkAdapter{
	
	@Inject
	private SupportWorkData support;
	
	@Override
	public SupportDataWorkImport correctionAfterChangeAttendance(IntegrationOfDaily integrationOfDaily) {
		SupportDataWorkImport workImport = support.correctionAfterChangeAttendance(integrationOfDaily);
		return workImport;
	}

}
