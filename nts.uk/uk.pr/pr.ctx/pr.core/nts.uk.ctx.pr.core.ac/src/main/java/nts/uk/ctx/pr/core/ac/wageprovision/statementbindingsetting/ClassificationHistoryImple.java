package nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.ClassificationHistoryExport;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class ClassificationHistoryImple implements ClassificationHisExportAdapter {
    @Override
    public Optional<ClassificationHistoryExport> getClassificationHisByBaseDate(String employeeId) {
        return Optional.empty();
    }
}
