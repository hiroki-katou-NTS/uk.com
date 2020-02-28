package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHis;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpCorpHealthOffHisRepository extends JpaRepository implements EmpCorpHealthOffHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND  f.empCorpOffHisPk.hisId =:hisId ";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId ";
    private static final String SELECT_BY_KEY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId IN :employeeIds  AND f.empCorpOffHisPk.cid =:cid AND f.startDate <= :startDate AND f.endDate >= :startDate ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.cid =:cid AND f.empCorpOffHisPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate ";
    @Override
    public List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis(){
        return null;
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId){
        return null;
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId) {
        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_EMPID, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", employeeId)
                .getList();
       return Optional.ofNullable(QqsmtEmpCorpOffHis.toDomain(qqsmtEmpCorpOffHis));

    }

    @Override
    public Optional<String> getSocialInsuranceOfficeCd(String cid, String employeeId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpCorpOffHis.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .setParameter("baseDate",baseDate)
                .getSingle(x -> x.socialInsuranceOfficeCd);

    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(List<String> employeeIds, GeneralDate startDate) {

        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_KEY_EMPID, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeIds", employeeIds)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("startDate", startDate)
                .getList();

        return qqsmtEmpCorpOffHis.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpCorpOffHis.toDomain(qqsmtEmpCorpOffHis));
    }

}
