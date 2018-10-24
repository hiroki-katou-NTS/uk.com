package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.YourselfConfirmError;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtIdentityProcess;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtIdentityProcessPk;

@Stateless
public class JpaIdentityProcessRepository extends JpaRepository implements IdentityProcessRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtIdentityProcess f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.identityProcessPk.cid =:cid ";

    @Override
    public List<IdentityProcess> getAllIdentityProcess(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtIdentityProcess.class)
                .getList(item -> item.toDomain());
    }

    @Override
    @SneakyThrows
    public Optional<IdentityProcess> getIdentityProcessById(String cid){
    	PreparedStatement statement = this.connection().prepareStatement("SELECT * from KRCMT_SELF_CHECK_SET h WHERE h.CID = ?");
		statement.setString(1, cid);
		return new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
			return new IdentityProcess(cid, rec.getInt("USE_DAILY_SELF_CHECK"), 
	        		rec.getInt("USE_MONTHLY_SELF_CHECK"),
	        		rec.getInt("YOURSELF_CONFIRM_ERROR") == null ? null : EnumAdaptor.valueOf(rec.getInt("YOURSELF_CONFIRM_ERROR"), YourselfConfirmError.class));
		});
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
