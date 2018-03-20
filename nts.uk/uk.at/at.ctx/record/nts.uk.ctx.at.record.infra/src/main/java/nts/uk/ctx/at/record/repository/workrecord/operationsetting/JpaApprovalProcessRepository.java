package nts.uk.ctx.at.record.repository.workrecord.operationsetting;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcess;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcessPk;
import nts.uk.ctx.at.record..dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record..dom.workrecord.operationsetting.ApprovalProcess;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaApprovalProcessRepository extends JpaRepository implements ApprovalProcessRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtApprovalProcess f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.approvalProcessPk.cid =:cid ";

    @Override
    public List<ApprovalProcess> getAllApprovalProcess(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtApprovalProcess.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ApprovalProcess> getApprovalProcessById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KrcmtApprovalProcess.class)
        .setParameter("cid", cid)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ApprovalProcess domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(ApprovalProcess domain){
        KrcmtApprovalProcess newApprovalProcess = toEntity(domain);
        KrcmtApprovalProcess updateApprovalProcess = this.queryProxy().find(newApprovalProcess.approvalProcessPk, KrcmtApprovalProcess.class).get();
        if (null == updateApprovalProcess) {
            return;
        }
        updateApprovalProcess.useDayApproverConfirm = newApprovalProcess.useDayApproverConfirm;
        updateApprovalProcess.useMonthApproverComfirm = newApprovalProcess.useMonthApproverComfirm;
        updateApprovalProcess.supervisorConfirmError = newApprovalProcess.supervisorConfirmError;
        this.commandProxy().update(updateApprovalProcess);
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrcmtApprovalProcess.class, new KrcmtApprovalProcessPk(cid)); 
    }
}
