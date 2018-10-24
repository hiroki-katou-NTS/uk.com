package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDateRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetDate;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetDatePk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStateLinkSettingDateRepository extends JpaRepository implements StateLinkSettingDateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateLinkSetDate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateLinkSetDatePk.hisId =:hisId ";

    @Override
    public List<StateLinkSettingDate> getAllStateLinkSettingDate(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStateLinkSetDate.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingDate> getStateLinkSettingDateById(String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateLinkSetDate.class)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StateLinkSettingDate domain){
        this.commandProxy().insert(QpbmtStateLinkSetDate.toEntity(domain));
    }

    @Override
    public void update(StateLinkSettingDate domain){
        this.commandProxy().update(QpbmtStateLinkSetDate.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
        this.commandProxy().remove(QpbmtStateLinkSetDate.class, new QpbmtStateLinkSetDatePk(hisId));
    }
}
