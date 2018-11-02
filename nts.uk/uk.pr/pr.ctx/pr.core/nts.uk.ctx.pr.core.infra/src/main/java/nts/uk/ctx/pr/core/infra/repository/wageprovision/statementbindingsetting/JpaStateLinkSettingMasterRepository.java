package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetMas;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetMasPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStateLinkSettingMasterRepository extends JpaRepository implements StateLinkSettingMasterRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateLinkSetMas f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateLinkSetMasPk.hisId =:hisId AND  f.stateLinkSetMasPk.masterCode =:masterCode ";
    private static final String SELECT_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateLinkSetMasPk.hisId =:hisId ";

    @Override
    public List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String hisId){
        return this.queryProxy().query(SELECT_BY_HISID, QpbmtStateLinkSetMas.class)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String hisId, String masterCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateLinkSetMas.class)
        .setParameter("hisId", hisId)
        .setParameter("masterCode", masterCode)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StateLinkSettingMaster domain){
        this.commandProxy().insert(QpbmtStateLinkSetMas.toEntity(domain));
    }

    @Override
    public void addAll(List<StateLinkSettingMaster> domain) {
        this.commandProxy().insertAll(QpbmtStateLinkSetMas.toEntity(domain));
    }

    @Override
    public void update(StateLinkSettingMaster domain){
        this.commandProxy().update(QpbmtStateLinkSetMas.toEntity(domain));
    }

    @Override
    public void updateAll(List<StateLinkSettingMaster> domain){
        this.commandProxy().updateAll(QpbmtStateLinkSetMas.toEntity(domain));
    }

    @Override
    public void remove(String hisId, String masterCode){
        this.commandProxy().remove(QpbmtStateLinkSetMas.class, new QpbmtStateLinkSetMasPk(hisId, masterCode));
    }
}
