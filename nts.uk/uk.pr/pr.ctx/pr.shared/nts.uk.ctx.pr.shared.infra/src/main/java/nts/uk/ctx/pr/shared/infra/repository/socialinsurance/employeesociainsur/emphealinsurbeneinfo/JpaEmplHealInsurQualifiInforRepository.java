package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmplHealInsurQualifiInforRepository extends JpaRepository implements EmplHealInsurQualifiInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId AND  f.empHealInsurQiPk.hisId =:hisId ";
    private static final String SELECT_BY_LIST_EMP_START = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.startDate <= :startDate AND f.endDate >= :startDate";
    private static final String SELECT_BY_EMPLID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId ";
    private static final String SELECT_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.hisId =:hisId ";

    @Override
    public boolean checkEmplHealInsurQualifiInfor(GeneralDate start, List<String> empIds){
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi =  this.queryProxy().query(SELECT_BY_LIST_EMP_START, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforByEmpId(String empId) {
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi = this.queryProxy().query(SELECT_BY_EMPLID, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .getList();
         return Optional.of(new EmplHealInsurQualifiInfor(empId,toDomain(qqsmtEmpHealInsurQi)));
    }
    private List<EmpHealthInsurBenefits> toDomain(List<QqsmtEmpHealInsurQi> entities) {
        List<EmpHealthInsurBenefits> dateHistoryItems = new ArrayList<EmpHealthInsurBenefits>();
        if (entities == null || entities.isEmpty()) {
            return dateHistoryItems;
        }

        entities.forEach(entity -> {
            dateHistoryItems.add( new EmpHealthInsurBenefits(entity.empHealInsurQiPk.hisId,
                    new DateHistoryItem(entity.empHealInsurQiPk.hisId,new DatePeriod(entity.startDate,
                            entity.endDate))));
        });
        return dateHistoryItems;
    }
    @Override
    public boolean checkHealInsurQualifiInformation(String userIds) {
        return false;
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforById(String employeeId, String hisId){
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpHealInsurQi.class)
        .setParameter("employeeId", employeeId)
        .setParameter("hisId", hisId)
        .getList();
        return qqsmtEmpHealInsurQi == null ? Optional.empty() : Optional.of(QqsmtEmpHealInsurQi.toDomain(qqsmtEmpHealInsurQi));
    }

    @Override
    public Optional<HealInsurNumberInfor> getHealInsurNumberInforByHisId(String hisId) {
        return this.queryProxy().query(SELECT_BY_HISID, QqsmtEmpHealInsurQi.class)
                .setParameter("hisId", hisId)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public void add(EmplHealInsurQualifiInfor domain){
        this.commandProxy().insert(QqsmtEmpHealInsurQi.toEntity(domain));
    }

    @Override
    public void update(EmplHealInsurQualifiInfor domain){
        this.commandProxy().update(QqsmtEmpHealInsurQi.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String hisId){
        this.commandProxy().remove(QqsmtEmpHealInsurQi.class, new QqsmtEmpHealInsurQiPk(employeeId, hisId));
    }
}
