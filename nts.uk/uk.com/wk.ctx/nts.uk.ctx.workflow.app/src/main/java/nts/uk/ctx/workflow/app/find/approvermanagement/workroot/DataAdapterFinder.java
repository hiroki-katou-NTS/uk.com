package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class DataAdapterFinder {
	@Inject
	private SyJobTitleAdapter jobTitle;
	public List<JobTitleImport> findAllJobTitle(GeneralDate baseDate){
		String companyId = AppContexts.user().companyId();
		return jobTitle.findAll(companyId, baseDate);
	}
}
