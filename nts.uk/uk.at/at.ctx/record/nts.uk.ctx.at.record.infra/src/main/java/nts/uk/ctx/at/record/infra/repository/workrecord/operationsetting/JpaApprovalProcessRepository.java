package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.YourselfConfirmError;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcess;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcessPk;

@Stateless
public class JpaApprovalProcessRepository extends JpaRepository implements ApprovalProcessRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtApprovalProcess f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.approvalProcessPk.cid =:cid ";

    @Override
    public List<ApprovalProcess> getAllApprovalProcess(){
    	List<ApprovalProcess> data = this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtApprovalProcess.class)
    			.getList(c -> c.toDomain());
    	
        return data;
    }

    @Override
    @SneakyThrows
    public Optional<ApprovalProcess> getApprovalProcessById(String cid){
    	try (PreparedStatement statement = this.connection().prepareStatement("SELECT * from KRCMT_BOSS_CHECK_SET h WHERE h.CID = ?")) {
			statement.setString(1, cid);
			return new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
				return new ApprovalProcess(cid, rec.getString("JOB_TITLE_NOT_BOSS_CHECK"), 
		        		rec.getInt("USE_DAILY_BOSS_CHECK"), rec.getInt("USE_MONTHLY_BOSS_CHECK"), 
		        		rec.getInt("SUPERVISOR_CONFIRM_ERROR") == null ? null : EnumAdaptor.valueOf(rec.getInt("SUPERVISOR_CONFIRM_ERROR"), YourselfConfirmError.class));
			});
    	}
    }

    @Override
    public void add(ApprovalProcess domain){
        this.commandProxy().insert(KrcmtApprovalProcess.toEntity(domain));
    }

    @Override
    public void update(ApprovalProcess domain){
        KrcmtApprovalProcess newApprovalProcess = KrcmtApprovalProcess.toEntity(domain);
        KrcmtApprovalProcess updateApprovalProcess = this.queryProxy().find(newApprovalProcess.approvalProcessPk, KrcmtApprovalProcess.class).get();
        if (null == updateApprovalProcess) {
            return;
        }
        updateApprovalProcess.useDailyBossChk = newApprovalProcess.useDailyBossChk;
        updateApprovalProcess.useMonthBossChk = newApprovalProcess.useMonthBossChk;
        updateApprovalProcess.supervisorConfirmError = newApprovalProcess.supervisorConfirmError;
        this.commandProxy().update(updateApprovalProcess);
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrcmtApprovalProcess.class, new KrcmtApprovalProcessPk(cid)); 
    }

}
