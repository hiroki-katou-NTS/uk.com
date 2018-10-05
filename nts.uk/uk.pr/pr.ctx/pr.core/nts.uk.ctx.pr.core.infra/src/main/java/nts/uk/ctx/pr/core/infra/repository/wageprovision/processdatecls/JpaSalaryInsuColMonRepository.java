package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMon;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMonRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtSalaryInsuColMon;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtSalaryInsuColMonPk;

@Stateless
public class JpaSalaryInsuColMonRepository extends JpaRepository implements SalaryInsuColMonRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalaryInsuColMon f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salaryInsuColMonPk.processCateNo =:processCateNo AND  f.salaryInsuColMonPk.cid =:cid ";

    @Override
    public List<SalaryInsuColMon> getAllSalaryInsuColMon(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalaryInsuColMon.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalaryInsuColMon> getSalaryInsuColMonById(int processCateNo, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalaryInsuColMon.class)
        .setParameter("processCateNo", processCateNo)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalaryInsuColMon domain){
        this.commandProxy().insert(QpbmtSalaryInsuColMon.toEntity(domain));
    }

    @Override
    public void update(SalaryInsuColMon domain){
        this.commandProxy().update(QpbmtSalaryInsuColMon.toEntity(domain));
    }

    @Override
    public void remove(int processCateNo, String cid){
        this.commandProxy().remove(QpbmtSalaryInsuColMon.class, new QpbmtSalaryInsuColMonPk(processCateNo, cid)); 
    }
}
