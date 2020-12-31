package nts.uk.ctx.at.record.ac.approvalrootstate;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.ApprovalPhaseStateImport;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.ApprovalRootStateAdapter;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.ApprovalRootStateImport;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalRootStatePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ApprovalRootStateAdapterAcFinder implements ApprovalRootStateAdapter {

    @Inject
    private ApprovalRootStatePub approvalRootStatePub;

    @Override
    public List<ApprovalRootStateImport> findByEmployeesAndPeriod(List<String> empIds, DatePeriod period, int rootType) {
        return approvalRootStatePub.findByEmployeesAndPeriod(empIds, period, rootType)
                .stream().map(a -> this.toImport(a)).collect(Collectors.toList());
    }

    private ApprovalRootStateImport toImport(ApprovalRootStateExport export) {
        List<ApprovalPhaseStateImport> appPhases = export.getListApprovalPhaseState().stream().map(p ->
                new ApprovalPhaseStateImport(p.getPhaseOrder(), p.getApprovalAtr()))
                .collect(Collectors.toList());
        return new ApprovalRootStateImport(export.getRootStateID(), export.getApprovalRecordDate(),
                export.getEmployeeID(), appPhases);
    }
}
