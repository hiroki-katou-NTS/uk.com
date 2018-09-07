package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtSpecItemDispSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtSpecItemDispSetPk;

@Stateless
public class JpaStatementItemDisplaySetRepository extends JpaRepository implements StatementItemDisplaySetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecItemDispSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specItemDispSetPk.cid =:cid AND  f.specItemDispSetPk.salaryItemId =:salaryItemId ";

    @Override
    public List<StatementItemDisplaySet> getAllSpecItemDispSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecItemDispSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StatementItemDisplaySet> getSpecItemDispSetById(String cid, String salaryItemId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSpecItemDispSet.class)
        .setParameter("cid", cid)
        .setParameter("salaryItemId", salaryItemId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StatementItemDisplaySet domain){
        this.commandProxy().insert(QpbmtSpecItemDispSet.toEntity(domain));
    }

    @Override
    public void update(StatementItemDisplaySet domain){
        this.commandProxy().update(QpbmtSpecItemDispSet.toEntity(domain));
    }

    @Override
    public void remove(String cid, String salaryItemId){
        this.commandProxy().remove(QpbmtSpecItemDispSet.class, new QpbmtSpecItemDispSetPk(cid, salaryItemId)); 
    }
}
