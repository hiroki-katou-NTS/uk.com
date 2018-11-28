package nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;

import java.util.List;

public interface SyJobTitleAdapter {
    List<JobTitle> findAll(String companyId, GeneralDate baseDate);
}
