package nts.uk.ctx.exio.infra.repository.exi.opmanage;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.opmanage.OiottExAcOpMng;
import nts.uk.ctx.exio.infra.entity.exi.opmanage.OiottExAcOpMngPk;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManageRepository;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManage;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaExAcOpManageRepository extends JpaRepository implements ExAcOpManageRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiottExAcOpMng f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exAcOpManagePk.cid =:cid AND  f.exAcOpManagePk.processId =:processId ";

    @Override
    public List<ExAcOpManage> getAllExAcOpManage(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiottExAcOpMng.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ExAcOpManage> getExAcOpManageById(String cid, String processId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiottExAcOpMng.class)
        .setParameter("cid", cid)
        .setParameter("processId", processId)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ExAcOpManage domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(ExAcOpManage domain){
        OiottExAcOpMng newExAcOpManage = toEntity(domain);
        OiottExAcOpMng updateExAcOpManage = this.queryProxy().find(newExAcOpManage.exAcOpManagePk, OiottExAcOpMng.class).get();
        if (null == updateExAcOpManage) {
            return;
        }
        updateExAcOpManage.version = newExAcOpManage.version;
        updateExAcOpManage.errorCount = newExAcOpManage.errorCount;
        updateExAcOpManage.interruption = newExAcOpManage.interruption;
        updateExAcOpManage.processCount = newExAcOpManage.processCount;
        updateExAcOpManage.processTotalCount = newExAcOpManage.processTotalCount;
        updateExAcOpManage.stateBehavior = newExAcOpManage.stateBehavior;
        this.commandProxy().update(updateExAcOpManage);
    }

    @Override
    public void remove(String cid, String processId){
        this.commandProxy().remove(OiottExAcOpMng.class, new OiottExAcOpMngPk(cid, processId)); 
    }

    private static ExAcOpManage toDomain(OiottExAcOpMng entity) {
        return ExAcOpManage.createFromJavaType(entity.version, entity.exAcOpManagePk.cid, entity.exAcOpManagePk.processId, entity.errorCount, entity.interruption, entity.processCount, entity.processTotalCount, entity.stateBehavior);
    }

    private OiottExAcOpMng toEntity(ExAcOpManage domain) {
        return new OiottExAcOpMng(domain.getVersion(), new OiottExAcOpMngPk(domain.getCid(), domain.getProcessId()), domain.getErrorCount(), domain.getInterruption(), domain.getProcessCount(), domain.getProcessTotalCount(), domain.getStateBehavior());
    }

}
