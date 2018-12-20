package nts.uk.ctx.pr.core.ac.employee.jobtitle;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitleImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;
import java.util.List;

@Stateless
public class SyJobTitleAdapterImplement implements SyJobTitleAdapter{

    @Inject
    private SyJobTitlePub syJobTitlePub;

    @Override
    public List<JobTitle> findAll(String companyId, GeneralDate baseDate) {

        return syJobTitlePub.findAll(companyId, baseDate).stream().map(i -> {return new JobTitle(
                i.getCompanyId(),
                i.getJobTitleId(),
                i.getJobTitleCode(),
                i.getJobTitleName()
        ) ;}).collect(Collectors.toList());
    }
    @Override
    public List<JobTitleImport> getListJobTitleByCompanyID(String companyID, GeneralDate baseDate) {
        return syJobTitlePub.findAll(AppContexts.user().companyId(), baseDate).stream().map(jobTitle -> {
            return new JobTitleImport(jobTitle.getCompanyId(), jobTitle.getJobTitleId(), jobTitle.getJobTitleCode(), jobTitle.getJobTitleName(), jobTitle.getSequenceCode(), jobTitle.getStartDate(), jobTitle.getEndDate(), jobTitle.isManager());
        }).collect(Collectors.toList());
    }
}
