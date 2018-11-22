package nts.uk.ctx.pr.core.dom.adapter.employee.classification;

import java.util.Optional;

public interface ClassificationHisExportAdapter {
    Optional<ClassificationHistoryExport> getClassificationHisByBaseDate(String employeeId);
}
