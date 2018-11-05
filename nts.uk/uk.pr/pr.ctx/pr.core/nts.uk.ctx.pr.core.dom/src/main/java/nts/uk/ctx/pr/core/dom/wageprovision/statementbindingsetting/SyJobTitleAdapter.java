package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import java.util.List;

public interface SyJobTitleAdapter {
    List<JobTitle> findAll(String companyId, GeneralDate baseDate);
}
