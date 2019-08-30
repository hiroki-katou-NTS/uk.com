package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtEmpWelfInsQcIf;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpWelfarePenInsQualiInforRepository extends JpaRepository implements EmpWelfarePenInsQualiInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpWelfInsQcIf f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId =:employeeId AND  f.empWelfInsQcIfPk.historyId =:historyId ";
    private static final String SELECT_BY_LIST_EMP = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId IN :employeeIds  AND f.startDate <= :startDate AND f.endDate >= :startDate";
    private static final String SELECT_BY_LIST_EMP_END = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId IN :employeeIds  AND f.startDate <= :endDate AND f.endDate >= :endDate";
    private static final String SELECT_BY_KEY_STRING_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE f.empWelfInsQcIfPk.employeeId =:employeeId ";

    @Override
    public EmpWelfarePenInsQualiInfor getEmpWelfarePenInsQualiInfor(GeneralDate start, List<String> empIds) {
        List<QqsmtEmpWelfInsQcIf> qqsmtEmpWelfInsQcIf =  this.queryProxy().query(SELECT_BY_LIST_EMP, QqsmtEmpWelfInsQcIf.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .getList();
        return qqsmtEmpWelfInsQcIf.isEmpty() ? null : QqsmtEmpWelfInsQcIf.toDomain(qqsmtEmpWelfInsQcIf);
    }


    @Override
    public boolean checkEmpWelfarePenInsQualiInfor(String empIds) {
        return false;
    }

    @Override
    public Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforById(String employeeId, String historyId){
        List<QqsmtEmpWelfInsQcIf> qqsmtEmpWelfInsQcIf =  this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpWelfInsQcIf.class)
        .setParameter("employeeId", employeeId)
        .setParameter("historyId", historyId)
        .getList();
        return qqsmtEmpWelfInsQcIf.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpWelfInsQcIf.toDomain(qqsmtEmpWelfInsQcIf));
    }

    @Override
    public void add(EmpWelfarePenInsQualiInfor domain){
        this.commandProxy().insert(QqsmtEmpWelfInsQcIf.toEntity(domain));
    }

    @Override
    public void update(EmpWelfarePenInsQualiInfor domain){
        this.commandProxy().update(QqsmtEmpWelfInsQcIf.toEntity(domain));
    }

    @Override
    public Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforByEmpId(String employeeId) {
        return Optional.empty();
    }

}
