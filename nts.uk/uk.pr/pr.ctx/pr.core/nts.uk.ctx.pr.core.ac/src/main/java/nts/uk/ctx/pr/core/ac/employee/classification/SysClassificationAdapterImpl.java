package nts.uk.ctx.pr.core.ac.employee.classification;

import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.SysClassificationAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SysClassificationAdapterImpl implements SysClassificationAdapter{
    @Inject
    private SyClassificationPub syClassificationPub;

    @Override
    public List<ClassificationImport> getClassificationByCompanyId(String companyId) {
        return syClassificationPub.getClassificationByCompanyId(companyId).stream().map(
            item -> new ClassificationImport(item.getCompanyId(), item.getClassificationCode(), item.getClassificationName(), item.getMemo())).collect(Collectors.toList());
    }
}
