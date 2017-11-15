package nts.uk.ctx.sys.auth.ac.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.sys.auth.dom.adapter.employee.SyJobTitleAdapter;
import nts.uk.ctx.sys.auth.dom.employee.dto.JobTitleImport;

@Stateless
public class SyJobTitleAdapterImpl implements SyJobTitleAdapter {
	@Inject
	private SyJobTitlePub syJobTitlePub;

	private JobTitleImport toImport(JobTitleExport ex) {
		return new JobTitleImport(ex.getCompanyId(), ex.getJobTitleId(), ex.getJobTitleCode(), ex.getJobTitleName(),
				ex.getSequenceCode(), ex.getStartDate(), ex.getEndDate());
	}

	@Override
	public List<JobTitleImport> findJobTitleBySid(String employeeId) {
		return this.syJobTitlePub.findJobTitleBySid(employeeId).stream().map(x -> this.toImport(x))
				.collect(Collectors.toList());
	}

	@Override
	public JobTitleImport findJobTitleBySid(String employeeId, GeneralDate baseDate) {
		Optional<JobTitleExport> export = this.syJobTitlePub.findBySid(employeeId, baseDate);
		return export.map(x -> this.toImport(x)).orElse(null);
	}

	@Override
	public JobTitleImport findJobTitleByPositionId(String companyId, String positionId, GeneralDate baseDate) {
		return this.syJobTitlePub.findByJobId(companyId, positionId, baseDate).map(x -> this.toImport(x)).orElse(null);
	}

	@Override
	public List<JobTitleImport> findAll(String companyId, GeneralDate baseDate) {
		List<JobTitleImport> data = syJobTitlePub.findAll(companyId, baseDate).stream().map(x -> this.toImport(x))
				.collect(Collectors.toList());
		return data;
	}
}
