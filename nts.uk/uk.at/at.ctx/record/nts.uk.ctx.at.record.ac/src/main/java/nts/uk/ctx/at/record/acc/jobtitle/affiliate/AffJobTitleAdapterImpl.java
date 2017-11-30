package nts.uk.ctx.at.record.acc.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleAdapter;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleSidImport;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;

@Stateless
public class AffJobTitleAdapterImpl implements AffJobTitleAdapter{
	
	@Inject
	private SyJobTitlePub syJobTitlePub;

	@Override
	public Optional<AffJobTitleSidImport> findByEmployeeId(String employeeId, GeneralDate baseDate) {
		Optional<JobTitleExport> jobTitle = this.syJobTitlePub.findBySid(employeeId, baseDate);
//		AffJobTitleSidImport affJobTitleSidImport = new AffJobTitleSidImport(jobTitle.get().ge, jobTitleId, dateRange)
		return null;
	}

}
