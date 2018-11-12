package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTable;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTablePk;

@Stateless
public class JpaWageTableRepository extends JpaRepository implements WageTableRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtWageTable f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.wageTablePk.cid =:cid AND  f.wageTablePk.wageTableCode =:wageTableCode ";

    @Override
    public List<WageTable> getAllWageTable(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtWageTable.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<WageTable> getWageTableById(String cid, String wageTableCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtWageTable.class)
        .setParameter("cid", cid)
        .setParameter("wageTableCode", wageTableCode)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(WageTable domain){
        this.commandProxy().insert(QpbmtWageTable.toEntity(domain));
    }

    @Override
    public void update(WageTable domain){
        this.commandProxy().update(QpbmtWageTable.toEntity(domain));
    }

    @Override
    public void remove(String cid, String wageTableCode){
        this.commandProxy().remove(QpbmtWageTable.class, new QpbmtWageTablePk(cid, wageTableCode));
    }
}
