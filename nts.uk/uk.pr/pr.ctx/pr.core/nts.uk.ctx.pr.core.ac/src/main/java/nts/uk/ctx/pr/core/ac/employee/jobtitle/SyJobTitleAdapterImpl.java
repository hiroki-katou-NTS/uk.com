package nts.uk.ctx.pr.core.ac.employee.jobtitle;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitleImport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SyJobTitleAdapterImpl implements SyJobTitleAdapter {

    @Inject
    SyJobTitlePub syJobTitlePub;

    @Inject
    SyClassificationPub syClassificationPub;


    @Override
    public List<JobTitleImport> getListJobTitleByCompanyID(String companyID, GeneralDate baseDate) {
        return syJobTitlePub.findAll(AppContexts.user().companyId(), baseDate).stream().map(jobTitle -> {
            return new JobTitleImport(jobTitle.getCompanyId(), jobTitle.getJobTitleId(), jobTitle.getJobTitleCode(), jobTitle.getJobTitleName(), jobTitle.getSequenceCode(), jobTitle.getStartDate(), jobTitle.getEndDate(), jobTitle.isManager());
        }).collect(Collectors.toList());

    }
}
