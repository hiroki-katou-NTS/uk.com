package nts.uk.ctx.bs.person.ac.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.bs.employee.pub.jobtitle.SubJobPosPub;
import nts.uk.ctx.bs.person.dom.person.adapter.SubJobPosAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.SubJobPosImport;

@Stateless
public class SubJobPosAdapterImpl implements SubJobPosAdapter{

	@Inject
	private SubJobPosPub subJobPosPub;
	
	@Override
	public List<SubJobPosImport> getSubJobPosByDeptId(String deptId) {
		return subJobPosPub.getSubJobPosByDeptId(deptId).stream()
				.map(x -> new SubJobPosImport(x.getSubJobPosId(), x.getAffiDeptId(), 
						x.getJobTitleId(), x.getStartDate(), x.getEndDate())).collect(Collectors.toList());
	}

}
