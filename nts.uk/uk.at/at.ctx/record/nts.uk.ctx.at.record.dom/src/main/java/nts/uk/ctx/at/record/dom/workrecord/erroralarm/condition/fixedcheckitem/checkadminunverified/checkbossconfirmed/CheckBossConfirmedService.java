package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkadminunverified.checkbossconfirmed;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.algorithm.AppTargetPersonStatusAdapter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.checkconfirm.StateConfirm;

@Stateless
public class CheckBossConfirmedService {
	@Inject
	private AppTargetPersonStatusAdapter appTargetPersonStatusAdapter;
	
	public List<StateConfirm> checkBossConfirmed(String employeeID,GeneralDate startDate,GeneralDate endDate){
		appTargetPersonStatusAdapter.appTargetPersonStatus(employeeID, startDate, endDate, 0);
		return Collections.emptyList();
	}

}
