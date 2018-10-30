package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividual;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividualRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetIndi;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetIndiPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStateLinkSettingIndividualRepository extends JpaRepository implements StateLinkSettingIndividualRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateLinkSetIndi f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateLinkSetIndiPk.hisId =:hisId ";

    @Override
    public List<StateLinkSettingIndividual> getAllStateLinkSettingIndividual(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStateLinkSetIndi.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingIndividual> getStateLinkSettingIndividualById(String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateLinkSetIndi.class)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StateLinkSettingIndividual domain){
        this.commandProxy().insert(QpbmtStateLinkSetIndi.toEntity(domain));
    }

    @Override
    public void update(StateLinkSettingIndividual domain){
        this.commandProxy().update(QpbmtStateLinkSetIndi.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
        this.commandProxy().remove(QpbmtStateLinkSetIndi.class, new QpbmtStateLinkSetIndiPk(hisId));
    }
}
