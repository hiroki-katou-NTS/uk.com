package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;

public interface ClassificationHisExportAdapter {
    Optional<ClassificationHistoryExport> getClassificationHisByBaseDate(String employeeId);
}
