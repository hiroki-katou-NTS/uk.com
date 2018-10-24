package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompanyRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetCom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateLinkSetComPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStateLinkSettingCompanyRepository extends JpaRepository implements StateLinkSettingCompanyRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateLinkSetCom f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateLinkSetComPk.hisId =:hisId ";

    @Override
    public List<StateLinkSettingCompany> getAllStateLinkSettingCompany(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStateLinkSetCom.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingCompany> getStateLinkSettingCompanyById(String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateLinkSetCom.class)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StateLinkSettingCompany domain){
        this.commandProxy().insert(QpbmtStateLinkSetCom.toEntity(domain));
    }

    @Override
    public void update(StateLinkSettingCompany domain){
        this.commandProxy().update(QpbmtStateLinkSetCom.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
        this.commandProxy().remove(QpbmtStateLinkSetCom.class, new QpbmtStateLinkSetComPk(hisId));
    }
}
