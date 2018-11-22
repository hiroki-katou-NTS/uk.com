package nts.uk.ctx.pr.core.dom.adapter.employee.classification;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface ClassificationHisExportAdapter {
    Optional<ClassificationHistoryExport> getClassificationHisByBaseDate(String companyId, String employeeId, GeneralDate baseDate);
}
