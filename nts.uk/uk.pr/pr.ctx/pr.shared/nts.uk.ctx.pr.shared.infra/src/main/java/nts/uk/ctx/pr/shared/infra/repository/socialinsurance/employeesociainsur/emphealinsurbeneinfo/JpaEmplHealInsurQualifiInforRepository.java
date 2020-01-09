package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmplHealInsurQualifiInforRepository extends JpaRepository implements EmplHealInsurQualifiInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";

    private static final String SELECT_BY_LIST_EMP_START = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.endDate >= :startDate AND f.endDate <= :endDate";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId ";
    private static final String SELECT_BY_LIST_EMP = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.startDate >= :startDate AND f.startDate <= :endDate";
    private static final String SELECT_BY_ID_AND_DATE = SELECT_ALL_QUERY_STRING + "f.empHealInsurQiPk.cid =:cid WHERE AND f.startDate <= :date AND f.endDate >= date";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_ID_HIS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate AND f.empHealInsurQiPk.hisId =:hisId";
    private static final String SELECT_BY_ID_LIST = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_ID_EMPIDS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId";
    private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.employeeId =:employeeId ORDER BY f.startDate DESC ";
    private static final String SELECT_BY_EMPID_AND_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.employeeId =:employeeId AND f.empHealInsurQiPk.cid =:cid AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId AND  f.empHealInsurQiPk.hisId =:hisId ";

    @Override
    public boolean checkEmplHealInsurQualifiInforEndDate(GeneralDate start, GeneralDate end, List<String> empIds) {
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi = this.queryProxy().query(SELECT_BY_LIST_EMP_START, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public boolean checkEmplHealInsurQualifiInforStartDate(GeneralDate start, GeneralDate end, List<String> empIds) {
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi = this.queryProxy().query(SELECT_BY_LIST_EMP, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", baseDate)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate date, String historyId) {
        return this.queryProxy().query(SELECT_BY_ID_HIS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", date)
                .setParameter("hisId", historyId)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, String empId, GeneralDate date) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", date)
                .getSingle(x -> x.toDomain());
    }


    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds, GeneralDate date) {
        return this.queryProxy().query(SELECT_BY_ID_LIST, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empIds)
                .setParameter("baseDate", date)
                .getList(x -> x.toDomain());
    }

    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds) {
        return this.queryProxy().query(SELECT_BY_ID_EMPIDS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empIds)
                .getList(x -> x.toDomain());
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getByEmpIdAndBaseDate(String empId, GeneralDate baseDate) {
        List<QqsmtEmpHealInsurQi> dataList = this.queryProxy()
                .query(SELECT_BY_EMPID_AND_DATE, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", baseDate)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();
        return dataList.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpHealInsurQi.toDomain(dataList));
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String empId) {
        List<QqsmtEmpHealInsurQi> list = this.queryProxy()
                .query(SELECT_BY_SID, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .getList();
        return Optional.ofNullable(QqsmtEmpHealInsurQi.toDomain(list));
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String empId, String hisId) {
        val result = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .setParameter("hisId", hisId)
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpHealInsurQi.toDomain(result));
    }

    @Override
    public EmplHealInsurQualifiInfor getEmpHealInsQualifiInfoOfEmp(String empId) {
        return null;
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getByHistoryId(String historyId) {
        return null;
    }

    @Override
    public void add(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemAdded, HealInsurNumberInfor hisItem) {
        this.commandProxy().insert(new QqsmtEmpHealInsurQi(
                new QqsmtEmpHealInsurQiPk(domain.getEmployeeId(), itemAdded.identifier(), AppContexts.user().companyId()),
                itemAdded.start(),
                itemAdded.end(),
                hisItem.getCareInsurNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null),
                hisItem.getHealInsNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null)
        ));
    }

    @Override
    public void update(EmpHealthInsurBenefits domain) {
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        String cid = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        String sql = UP_SQL;

        sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
        sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
        sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
        sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

        sql = sql.replace("START_DATE_VAL", "'" + domain.start() + "'");
        sql = sql.replace("END_DATE_VAL", "'" + domain.end() + "'");

        sql = sql.replace("HIST_ID_VAL", "'" + domain.identifier() + "'");
        sql = sql.replace("CID_VAL","'" + cid + "'");
        sb.append(sql);

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void update(EmpHealthInsurBenefits domain, HealInsurNumberInfor item, String sid) {
        val oldEntity = this.queryProxy().find(new QqsmtEmpHealInsurQiPk(sid, item.getHistoryId(), AppContexts.user().companyId()), QqsmtEmpHealInsurQi.class);
        if (!oldEntity.isPresent()) {
            return;
        }
        oldEntity.get().startDate = domain.getDatePeriod().start();
        oldEntity.get().endDate = domain.getDatePeriod().end();
        oldEntity.get().healInsurNumber = item.getHealInsNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null);
        oldEntity.get().careIsNumber = item.getCareInsurNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null);

        this.commandProxy().update(oldEntity.get());
    }

    @Override
    public void remove(String employeeId, String hisId) {
        String cid = AppContexts.user().companyId();
        this.commandProxy().remove(QqsmtEmpHealInsurQi.class, new QqsmtEmpHealInsurQiPk(employeeId, hisId, cid));
    }
}
