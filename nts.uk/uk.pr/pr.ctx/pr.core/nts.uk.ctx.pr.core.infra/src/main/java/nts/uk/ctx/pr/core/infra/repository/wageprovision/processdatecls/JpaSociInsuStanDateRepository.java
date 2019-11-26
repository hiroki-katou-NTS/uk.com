package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDateRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtSociInsuStanDate;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtSociInsuStanDatePk;

@Stateless
public class JpaSociInsuStanDateRepository extends JpaRepository implements SociInsuStanDateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSociInsuStanDate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.sociInsuStanDatePk.cid =:cid AND  f.sociInsuStanDatePk.processCateNo =:processCateNo ";

    @Override
    public List<SociInsuStanDate> getAllSociInsuStanDate(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSociInsuStanDate.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SociInsuStanDate> getSociInsuStanDateById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSociInsuStanDate.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SociInsuStanDate domain){
        this.commandProxy().insert(QpbmtSociInsuStanDate.toEntity(domain));
    }

    @Override
    public void update(SociInsuStanDate domain){
        this.commandProxy().update(QpbmtSociInsuStanDate.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtSociInsuStanDate.class, new QpbmtSociInsuStanDatePk(cid, processCateNo)); 
    }
}
