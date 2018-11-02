package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface SyJobTitleAdapter {
    List<JobTitle> findAll(String companyId, GeneralDate baseDate);
}
