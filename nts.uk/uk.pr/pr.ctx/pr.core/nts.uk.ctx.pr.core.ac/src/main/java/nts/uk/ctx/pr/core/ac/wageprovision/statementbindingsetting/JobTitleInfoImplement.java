package nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmployeeJobHistExport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.JobTitleInfoAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JobTitleInfoImplement implements JobTitleInfoAdapter {
    @Inject
    private SyJobTitlePub syJobTitlePub;

    @Override
    public List<JobTitle> getJobTitleInfoByBaseDate(String cid, GeneralDate baseDate) {
        return syJobTitlePub.findAll(cid, baseDate).stream().map(i -> {return new JobTitle(
                i.getCompanyId(),
                i.getJobTitleId(),
                i.getJobTitleCode(),
                i.getJobTitleName()
        ) ;}).collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeJobHistExport> findSJobHistBySId(String employeeId, GeneralDate baseDate) {
        return syJobTitlePub.findSJobHistBySId(employeeId,baseDate).map(x -> {
            return new EmployeeJobHistExport(x.getEmployeeId(),x.getJobTitleID(),x.getJobTitleName(),x.getStartDate(),x.getEndDate());

        });
    }
}
