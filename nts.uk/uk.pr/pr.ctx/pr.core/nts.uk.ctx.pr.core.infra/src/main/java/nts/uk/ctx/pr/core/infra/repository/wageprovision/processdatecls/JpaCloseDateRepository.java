package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CloseDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CloseDateRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtCloseDate;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtCloseDatePk;

@Stateless
public class JpaCloseDateRepository extends JpaRepository implements CloseDateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtCloseDate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.closeDatePk.processCateNo =:processCateNo AND  f.closeDatePk.cid =:cid ";

    @Override
    public List<CloseDate> getAllCloseDate(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtCloseDate.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<CloseDate> getCloseDateById(int processCateNo, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtCloseDate.class)
        .setParameter("processCateNo", processCateNo)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(CloseDate domain){
        this.commandProxy().insert(QpbmtCloseDate.toEntity(domain));
    }

    @Override
    public void update(CloseDate domain){
        this.commandProxy().update(QpbmtCloseDate.toEntity(domain));
    }

    @Override
    public void remove(int processCateNo, String cid){
        this.commandProxy().remove(QpbmtCloseDate.class, new QpbmtCloseDatePk(processCateNo, cid)); 
    }
}
