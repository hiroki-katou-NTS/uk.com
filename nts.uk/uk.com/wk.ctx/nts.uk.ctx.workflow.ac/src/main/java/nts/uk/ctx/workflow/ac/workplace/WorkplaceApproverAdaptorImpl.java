package nts.uk.ctx.workflow.ac.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverDto;
@Stateless
public class WorkplaceApproverAdaptorImpl implements WorkplaceApproverAdaptor {
	@Inject
	private SyWorkplacePub wpPub;
	@Override
	public List<WorkplaceApproverDto> findByWkpId(String companyId, String workplaceId, GeneralDate baseDate) {
		List<WorkplaceApproverDto> lstWp = wpPub.findByWkpId(companyId, workplaceId, baseDate)
				.stream()
				.map(x -> new WorkplaceApproverDto(x.getWkpCode(), x.getWkpName()))
				.collect(Collectors.toList());						
		return lstWp;
	}

}
