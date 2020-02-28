package nts.uk.ctx.pr.core.ac.employee.jobtitle;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
}
