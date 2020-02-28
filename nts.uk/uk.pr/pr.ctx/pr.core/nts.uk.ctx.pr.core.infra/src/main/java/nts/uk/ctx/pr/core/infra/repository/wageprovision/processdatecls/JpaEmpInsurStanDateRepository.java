package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpInsurStanDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpInsurStanDateRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpInsurStanDate;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpInsurStanDatePk;

@Stateless
public class JpaEmpInsurStanDateRepository extends JpaRepository implements EmpInsurStanDateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurStanDate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurStanDatePk.cid =:cid AND  f.empInsurStanDatePk.processCateNo =:processCateNo ";

    @Override
    public List<EmpInsurStanDate> getAllEmpInsurStanDate(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtEmpInsurStanDate.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpInsurStanDate> getEmpInsurStanDateById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpInsurStanDate.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmpInsurStanDate domain){
        this.commandProxy().insert(QpbmtEmpInsurStanDate.toEntity(domain));
    }

    @Override
    public void update(EmpInsurStanDate domain){
        this.commandProxy().update(QpbmtEmpInsurStanDate.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtEmpInsurStanDate.class, new QpbmtEmpInsurStanDatePk(cid, processCateNo)); 
    }
}
