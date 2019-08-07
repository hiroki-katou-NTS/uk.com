package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqbmtEmpCorpOffHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqbmtEmpCorpOffHisPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpCorpHealthOffHisRepository extends JpaRepository implements EmpCorpHealthOffHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqbmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND  f.empCorpOffHisPk.hisId =:hisId ";

    @Override
    public List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqbmtEmpCorpOffHis.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqbmtEmpCorpOffHis.class)
        .setParameter("employeeId", employeeId)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmpCorpHealthOffHis domain){
        this.commandProxy().insert(QqbmtEmpCorpOffHis.toEntity(domain));
    }

    @Override
    public void update(EmpCorpHealthOffHis domain){
        this.commandProxy().update(QqbmtEmpCorpOffHis.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String hisId){
        this.commandProxy().remove(QqbmtEmpCorpOffHis.class, new QqbmtEmpCorpOffHisPk(employeeId, hisId));
    }
}
