package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateUseUnitSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateUseUnitSetPk;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaStateUseUnitSetRepository extends JpaRepository implements StateUseUnitSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateUseUnitSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateUseUnitSetPk.cid =:cid ";

    @Override
    public Optional<StateUseUnitSet> getStateUseUnitSettingById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateUseUnitSet.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StateUseUnitSet domain){
        this.commandProxy().insert(QpbmtStateUseUnitSet.toEntity(domain));
    }

    @Override
    public void update(StateUseUnitSet domain){
        this.commandProxy().update(QpbmtStateUseUnitSet.toEntity(domain));
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(QpbmtStateUseUnitSet.class, new QpbmtStateUseUnitSetPk(cid));
    }
}
