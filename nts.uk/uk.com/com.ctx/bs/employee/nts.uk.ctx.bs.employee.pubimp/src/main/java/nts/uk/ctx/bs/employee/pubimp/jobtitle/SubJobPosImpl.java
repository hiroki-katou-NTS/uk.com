package nts.uk.ctx.bs.employee.pubimp.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.jobtitle.SubJobPosExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SubJobPosPub;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;

@Stateless
public class SubJobPosImpl implements SubJobPosPub{

	@Inject 
	private SubJobPosRepository subJobPosRepository;
	
	@Override
	public List<SubJobPosExport> getSubJobPosByDeptId(String deptId) {
		return subJobPosRepository.getSubJobPosByDeptId(deptId).stream()
				.map(x -> new SubJobPosExport(x.getSubJobPosId(),
						x.getAffiDeptId(), x.getJobTitleId(), x.getStartDate(), x.getEndDate()))
				.collect(Collectors.toList());
	}
}
