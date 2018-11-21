package nts.uk.ctx.pr.core.ac.employee.classification;

import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationHistoryExport;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class ClassificationHistoryImple implements ClassificationHisExportAdapter {
    @Override
    public Optional<ClassificationHistoryExport> getClassificationHisByBaseDate(String employeeId) {
        return Optional.empty();
    }
}
