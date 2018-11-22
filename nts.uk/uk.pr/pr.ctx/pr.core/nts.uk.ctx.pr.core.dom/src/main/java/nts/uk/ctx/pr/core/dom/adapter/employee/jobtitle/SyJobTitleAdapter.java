package nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface SyJobTitleAdapter {
    List<JobTitleImport> getListJobTitleByCompanyID(String companyID, GeneralDate baseData);
}
