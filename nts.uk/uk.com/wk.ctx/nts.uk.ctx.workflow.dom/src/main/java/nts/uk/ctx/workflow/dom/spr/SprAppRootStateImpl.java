package nts.uk.ctx.workflow.dom.spr;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.pub.spr.dailystatus.SprAppRootStateService;
import nts.uk.pub.spr.dailystatus.output.AppRootStateStatusSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class SprAppRootStateImpl implements SprAppRootStateService {
	
	@Inject
	private ApprovalRootStateStatusService approvalRootStateStatusService;

	@Override
	public List<AppRootStateStatusSpr> getStatusByEmpAndDate(String employeeID, GeneralDate startDate, GeneralDate endDate,
			Integer rootType) {
		return approvalRootStateStatusService.getStatusByEmpAndDate(employeeID, startDate, endDate, rootType)
				.stream().map(x -> new AppRootStateStatusSpr(
						x.getDate(), 
						x.getEmployeeID(), 
						x.getDailyConfirmAtr().value))
				.collect(Collectors.toList());
	}

}
