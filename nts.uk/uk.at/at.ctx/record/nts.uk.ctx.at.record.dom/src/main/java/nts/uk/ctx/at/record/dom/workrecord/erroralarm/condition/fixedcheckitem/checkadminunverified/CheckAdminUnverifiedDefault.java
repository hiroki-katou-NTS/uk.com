package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkadminunverified;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkadminunverified.checkbossconfirmed.CheckBossConfirmedService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;

@Stateless
public class CheckAdminUnverifiedDefault implements CheckAdminUnverifiedService {

	@Inject
	private CheckBossConfirmedService checkBossConfirmedService;
	@Override
	public List<ValueExtractAlarmWR> checkAdminUnverified(String workplaceID, String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		checkBossConfirmedService.checkBossConfirmed(employeeID, startDate, endDate);
		
		return null;
	}

}
