package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHis;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpCorpHealthOffHisRepository extends JpaRepository implements EmpCorpHealthOffHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqbmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND  f.empCorpOffHisPk.hisId =:hisId ";
    private static final String SELECT_BY_KEY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId IN :employeeIds  AND f.startDate <= :startDate AND f.endDate >= :startDate ";

    @Override
    public List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis(){
        return null;
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId){
        return null;
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(List<String> employeeIds, GeneralDate startDate) {

        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_KEY_EMPID, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeIds", employeeIds)
                .setParameter("startDate", startDate)
                .getList();
        return Optional.of(qqsmtEmpCorpOffHis == null ? null : QqsmtEmpCorpOffHis.toDomain(qqsmtEmpCorpOffHis));
    }

}
