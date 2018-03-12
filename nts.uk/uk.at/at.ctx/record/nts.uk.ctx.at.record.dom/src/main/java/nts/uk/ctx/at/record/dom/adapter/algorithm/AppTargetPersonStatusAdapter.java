package nts.uk.ctx.at.record.dom.adapter.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.checkconfirm.StateConfirm;

public interface AppTargetPersonStatusAdapter {
	public List<StateConfirm> appTargetPersonStatus(String employeeID,GeneralDate startDate,GeneralDate endDate,int routeType);
}
