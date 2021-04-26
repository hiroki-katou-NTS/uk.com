package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControl;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtIdentityProcess;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtIdentityProcessPk;

@Stateless
public class JpaIdentityProcessRepository extends JpaRepository implements IdentityProcessRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtDaiFuncControl f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.daiFuncControlPk.cid =:cid ";

    @Override
    public List<IdentityProcess> getAllIdentityProcess(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtDaiFuncControl.class)
                .getList(item -> item.toDomainIdentityProcess());
    }

    @Override
    @SneakyThrows
    public Optional<IdentityProcess> getIdentityProcessById(String cid){
    	return this.queryProxy().query(SELECT_BY_KEY_STRING, KrcmtDaiFuncControl.class)
    	        .setParameter("cid", cid)
    	        .getSingle(c->c.toDomainIdentityProcess());
//    	try (PreparedStatement statement = this.connection().prepareStatement("SELECT * from KRCMT_DAI_FUNC_CONTROL h WHERE h.CID = ?")) {
//			statement.setString(1, cid);
//			return new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
//				return new IdentityProcess(cid, 
//						rec.getInt("DAY_SELF_CHK"), 
//		        		rec.getInt("MON_SELF_CHK"),
//		        		rec.getInt("DAY_SELF_CHK_ERROR") == null ? null : EnumAdaptor.valueOf(rec.getInt("DAY_SELF_CHK_ERROR"), YourselfConfirmError.class));
//			});
//    	}
    }

    @Override
    public void add(IdentityProcess domain){
        this.commandProxy().insert(KrcmtIdentityProcess.toEntity(domain));
    }

    @Override
    public void update(IdentityProcess domain){
        KrcmtIdentityProcess newIdentityProcess = KrcmtIdentityProcess.toEntity(domain);
        KrcmtIdentityProcess updateIdentityProcess = this.queryProxy().find(newIdentityProcess.identityProcessPk, KrcmtIdentityProcess.class).get();
        if (null == updateIdentityProcess) {
        	this.add(domain);
            return;
        }
        updateIdentityProcess.useDailySelfCk = newIdentityProcess.useDailySelfCk;
        updateIdentityProcess.useMonthSelfCK = newIdentityProcess.useMonthSelfCK;
        updateIdentityProcess.yourselfConfirmError = newIdentityProcess.yourselfConfirmError;
        this.commandProxy().update(updateIdentityProcess);
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrcmtIdentityProcess.class, new KrcmtIdentityProcessPk(cid)); 
    }
}
