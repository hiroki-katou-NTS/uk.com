package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateUseUnitSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateUseUnitSetPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStateUseUnitSettingRepository extends JpaRepository implements StateUseUnitSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateUseUnitSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateUseUnitSetPk.cid =:cid ";

    @Override
    public List<StateUseUnitSetting> getAllStateUseUnitSetting(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStateUseUnitSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateUseUnitSetting> getStateUseUnitSettingById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateUseUnitSet.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StateUseUnitSetting domain){
        this.commandProxy().insert(QpbmtStateUseUnitSet.toEntity(domain));
    }

    @Override
    public void update(StateUseUnitSetting domain){
        this.commandProxy().update(QpbmtStateUseUnitSet.toEntity(domain));
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(QpbmtStateUseUnitSet.class, new QpbmtStateUseUnitSetPk(cid));
    }
}
