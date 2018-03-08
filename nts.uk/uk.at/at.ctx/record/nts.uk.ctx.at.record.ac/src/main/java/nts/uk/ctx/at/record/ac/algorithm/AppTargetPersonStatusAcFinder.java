package nts.uk.ctx.at.record.ac.algorithm;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.algorithm.AppTargetPersonStatusAdapter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.checkconfirm.StateConfirm;
@Stateless
public class AppTargetPersonStatusAcFinder implements AppTargetPersonStatusAdapter {

	@Override
	public List<StateConfirm> appTargetPersonStatus(String employeeID, GeneralDate startDate, GeneralDate endDate,
			int routeType) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}
}
