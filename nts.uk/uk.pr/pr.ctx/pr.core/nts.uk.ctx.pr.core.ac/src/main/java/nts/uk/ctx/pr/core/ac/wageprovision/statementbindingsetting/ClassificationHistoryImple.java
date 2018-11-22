package nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.ClassificationHisExportAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.ClassificationHistoryExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ClassificationHistoryImple implements ClassificationHisExportAdapter {
    @Inject
    private SyClassificationPub mSyClassificationPub;
    @Override
    public Optional<ClassificationHistoryExport> getClassificationHisByBaseDate(String companyId, String employeeId, GeneralDate baseDate) {
        Optional<ClassificationHistoryExport> resulf =  mSyClassificationPub.findSClsHistBySid(companyId,employeeId,baseDate).map(i -> new ClassificationHistoryExport(i.getPeriod(),i.getEmployeeId(),i.getClassificationCode(),i.getClassificationName()));
        return resulf;
    }
}
