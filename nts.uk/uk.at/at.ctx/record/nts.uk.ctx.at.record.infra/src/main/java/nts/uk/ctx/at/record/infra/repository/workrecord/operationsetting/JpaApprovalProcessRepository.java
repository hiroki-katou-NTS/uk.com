package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControl;

@Stateless
public class JpaApprovalProcessRepository extends JpaRepository implements ApprovalProcessRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtDaiFuncControl f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.daiFuncControlPk.cid =:cid ";

    @Override
    public List<ApprovalProcess> getAllApprovalProcess(){
    	List<ApprovalProcess> data = this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtDaiFuncControl.class)
    			.getList(c -> c.toDomainApprovalProcess());
    	
        return data;
    }

    @Override
    @SneakyThrows
    public Optional<ApprovalProcess> getApprovalProcessById(String cid){
    	return this.queryProxy().query(SELECT_BY_KEY_STRING, KrcmtDaiFuncControl.class)
    	        .setParameter("cid", cid)
    	        .getSingle(c->c.toDomainApprovalProcess());
//    	try (PreparedStatement statement = this.connection().prepareStatement("SELECT * from KRCMT_DAI_FUNC_CONTROL h WHERE h.CID = ?")) {
//			statement.setString(1, cid);
//			return new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
//				return new ApprovalProcess(cid, null, 
//		        		rec.getInt("DAY_BOSS_CHK"), 
//		        		rec.getInt("MON_BOSS_CHK"), 
//		        		rec.getInt("DAY_BOSS_CHK_ERROR") == null ? null : EnumAdaptor.valueOf(rec.getInt("DAY_BOSS_CHK_ERROR"), YourselfConfirmError.class));
//			});
//    	}
    }
}
