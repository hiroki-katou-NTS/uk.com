package nts.uk.ctx.pr.shared.ac.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessDateClassificationExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessDateClassificationPub;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.ProcessDateClassificationAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.ProcessDateClassificationImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ProcessDateClassificationAdapterImpl implements ProcessDateClassificationAdapter {

    @Inject
    private ProcessDateClassificationPub processDateClassificationPub;

    @Override
    public Optional<ProcessDateClassificationImport> getCurrentProcessYearMonthAndExtraRefDate() {
        Optional<ProcessDateClassificationExport> processDateClassificationExport = processDateClassificationPub.getCurrentProcessYearMonthAndExtraRefDate();
        return processDateClassificationExport.map(x -> new ProcessDateClassificationImport(x.getGiveCurrTreatYear(), x.getEmpExtraRefeDate()));
    }
}