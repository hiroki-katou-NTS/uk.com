package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.IncomTaxBaseYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.IncomTaxBaseYearRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtIncomTaxBaseYear;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtIncomTaxBaseYearPk;

@Stateless
public class JpaIncomTaxBaseYearRepository extends JpaRepository implements IncomTaxBaseYearRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtIncomTaxBaseYear f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.incomTaxBaseYearPk.cid =:cid AND  f.incomTaxBaseYearPk.processCateNo =:processCateNo ";

    @Override
    public List<IncomTaxBaseYear> getAllIncomTaxBaseYear(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtIncomTaxBaseYear.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<IncomTaxBaseYear> getIncomTaxBaseYearById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtIncomTaxBaseYear.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(IncomTaxBaseYear domain){
        this.commandProxy().insert(QpbmtIncomTaxBaseYear.toEntity(domain));
    }

    @Override
    public void update(IncomTaxBaseYear domain){
        this.commandProxy().update(QpbmtIncomTaxBaseYear.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtIncomTaxBaseYear.class, new QpbmtIncomTaxBaseYearPk(cid, processCateNo)); 
    }
}
