package nts.uk.ctx.pr.core.dom.adapter.employee.classification;

import java.util.List;

public interface SysClassificationAdapter {
    List<ClassificationImport> getClassificationByCompanyId(String companyId);
}
