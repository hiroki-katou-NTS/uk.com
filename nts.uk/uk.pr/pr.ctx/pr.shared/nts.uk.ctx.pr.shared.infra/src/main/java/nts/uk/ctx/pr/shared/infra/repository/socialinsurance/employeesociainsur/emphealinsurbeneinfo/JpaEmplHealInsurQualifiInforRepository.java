package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtEmpWelfInsQcIf;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmplHealInsurQualifiInforRepository extends JpaRepository implements EmplHealInsurQualifiInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";

    private static final String SELECT_BY_LIST_EMP_START = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.endDate >= :startDate AND f.endDate <= :endDate";
    private static final String SELECT_BY_EMPLID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId ";
    private static final String SELECT_BY_LIST_EMP = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.startDate >= :startDate AND f.startDate <= :endDate";
    private static final String SELECT_BY_ID_AND_DATE = SELECT_ALL_QUERY_STRING  + "f.empHealInsurQiPk.cid =:cid WHERE AND f.startDate <= :date AND f.endDate >= date";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_ID_HIS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate AND f.empHealInsurQiPk.hisId =:hisId";
    private static final String SELECT_BY_ID_LIST = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_ID_EMPIDS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId";

    @Override
    public boolean checkEmplHealInsurQualifiInforEndDate(GeneralDate start, GeneralDate end, List<String> empIds){
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi =  this.queryProxy().query(SELECT_BY_LIST_EMP_START, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public boolean checkEmplHealInsurQualifiInforStartDate(GeneralDate start, GeneralDate end, List<String> empIds){
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi =  this.queryProxy().query(SELECT_BY_LIST_EMP, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate",baseDate)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate date, String historyId) {
        return this.queryProxy().query(SELECT_BY_ID_HIS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate",date)
                .setParameter("hisId",historyId)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, String empId, GeneralDate date) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate",date)
                .getSingle(x -> x.toDomain());
    }


    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds, GeneralDate date) {
        return this.queryProxy().query(SELECT_BY_ID_LIST, QqsmtEmpHealInsurQi.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", empIds)
                .setParameter("baseDate",date)
                .getList(x -> x.toDomain());
    }

    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds) {
        return this.queryProxy().query(SELECT_BY_ID_EMPIDS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", empIds)
                .getList(x -> x.toDomain());
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
    public void add(EmplHealInsurQualifiInfor domain){
        this.commandProxy().insert(QqsmtEmpHealInsurQi.toEntity(domain));
    }

    @Override
    public void update(EmplHealInsurQualifiInfor domain){
        this.commandProxy().update(QqsmtEmpHealInsurQi.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String hisId){
        this.commandProxy().remove(QqsmtEmpHealInsurQi.class, new QqsmtEmpHealInsurQiPk(employeeId, hisId, AppContexts.user().companyId()));
    }
}
