package nts.uk.ctx.pr.core.infra.repository.individualwagecontract;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountHis;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountHisPk;

@Stateless
public class JpaSalIndAmountHisRepository extends JpaRepository implements SalIndAmountHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmountHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountHisPk.historyId =:historyId AND  f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId =:empId ";

    @Override
    public List<SalIndAmountHis> getAllSalIndAmountHis(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalIndAmountHis.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalIndAmountHis> getSalIndAmountHisById(String historyId, String perValCode, String empId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmountHis.class)
        .setParameter("historyId", historyId)
        .setParameter("perValCode", perValCode)
        .setParameter("empId", empId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalIndAmountHis domain){
        this.commandProxy().insert(QpbmtSalIndAmountHis.toEntity(domain));
    }

    @Override
    public void update(SalIndAmountHis domain){
        this.commandProxy().update(QpbmtSalIndAmountHis.toEntity(domain));
    }

    @Override
    public void remove(String historyId, String perValCode, String empId){
        this.commandProxy().remove(QpbmtSalIndAmountHis.class, new QpbmtSalIndAmountHisPk(historyId, perValCode, empId));
    }
}
