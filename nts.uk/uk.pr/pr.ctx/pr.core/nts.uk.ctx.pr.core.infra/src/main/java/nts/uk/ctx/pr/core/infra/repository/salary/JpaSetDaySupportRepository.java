package nts.uk.ctx.pr.core.infra.repository.salary;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.salary.SetDaySupport;
import nts.uk.ctx.pr.core.dom.salary.SetDaySupportRepository;
import nts.uk.ctx.pr.core.infra.entity.salary.QpbmtSetDaySupport;
import nts.uk.ctx.pr.core.infra.entity.salary.QpbmtSetDaySupportPk;

@Stateless
public class JpaSetDaySupportRepository extends JpaRepository implements SetDaySupportRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSetDaySupport f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.setDaySupportPk.cid =:cid AND  f.setDaySupportPk.processCateNo =:processCateNo ";

    @Override
    public List<SetDaySupport> getAllSetDaySupport(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSetDaySupport.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SetDaySupport> getSetDaySupportById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSetDaySupport.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SetDaySupport domain){
        this.commandProxy().insert(QpbmtSetDaySupport.toEntity(domain));
    }

    @Override
    public void update(SetDaySupport domain){
        this.commandProxy().update(QpbmtSetDaySupport.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtSetDaySupport.class, new QpbmtSetDaySupportPk(cid, processCateNo)); 
    }
}
