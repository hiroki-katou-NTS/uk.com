package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private static final String SELECT_BY_EMPID_AND_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";

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
        Optional<QqsmtEmpHealInsurQi> data = this.queryProxy()
                .query(SELECT_BY_EMPID_AND_DATE, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", baseDate)
                .getSingle();
        if (data.isPresent()) {
            return Optional.of(toDomain(data.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String cid, String empId) {
        List<QqsmtEmpHealInsurQi> list = this.queryProxy()
                .query(SELECT_BY_ID_EMPIDS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .getList();
        return Optional.empty();
    }

    @Override
    public EmplHealInsurQualifiInfor getEmpHealInsQualifiInfoOfEmp(String empId) {
        List<QqsmtEmpHealInsurQi> listInsQi = this.queryProxy()
                .query(SELECT_BY_EMP_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .getList();
        return toDomain(listInsQi);
    }

    private EmplHealInsurQualifiInfor toDomain(List<QqsmtEmpHealInsurQi> entities){
        if (entities.isEmpty()){
            return null;
        }

        EmplHealInsurQualifiInfor domain = new EmplHealInsurQualifiInfor();

        for (QqsmtEmpHealInsurQi item : entities) {
            if (domain.getEmployeeId() == null) {
                domain.setEmployeeId(item.empHealInsurQiPk.employeeId);
            }

            EmpHealthInsurBenefits empHealthInsurBenefits = domain.getEmpHealthInsurBenefits(item.empHealInsurQiPk.hisId);

            if (empHealthInsurBenefits == null) {
                empHealthInsurBenefits = new EmpHealthInsurBenefits();
                empHealthInsurBenefits.setDatePeriod(
                        new DateHistoryItem(item.empHealInsurQiPk.hisId,
                                new DatePeriod(item.startDate, item.endDate)
                        )
                );
                domain.addEmpHealInsBenefits(empHealthInsurBenefits);
            }
        }
        return domain;
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getByHistoryId(String historyId) {
        Optional<QqsmtEmpHealInsurQi> data = this.queryProxy().find(historyId, QqsmtEmpHealInsurQi.class);
        if (data.isPresent()) {
            return Optional.of(toDomain(data.get()));
        }
        return Optional.empty();
    }

    private EmplHealInsurQualifiInfor toDomain(QqsmtEmpHealInsurQi entity) {
        EmplHealInsurQualifiInfor domain = new EmplHealInsurQualifiInfor(entity.empHealInsurQiPk.getCid(), new ArrayList<>());
        EmpHealthInsurBenefits dataItem = new EmpHealthInsurBenefits(entity.empHealInsurQiPk.getHisId(), new DateHistoryItem(entity.empHealInsurQiPk.getHisId(), new DatePeriod(entity.getStartDate(), entity.getEndDate())));
        domain.getMourPeriod().add(dataItem);
        return domain;
    }

    @Override
    public void add(String empId, EmpHealthInsurBenefits item) {
        this.commandProxy().insert(toEntity(item, empId));
    }

    private QqsmtEmpHealInsurQi toEntity(EmpHealthInsurBenefits benefits, String empId){
        String cId = AppContexts.user().companyId();
        return new QqsmtEmpHealInsurQi(
                new QqsmtEmpHealInsurQiPk(empId, benefits.identifier(), cId),
                benefits.getDatePeriod().start(),
                benefits.getDatePeriod().end(),
                null, null
        );
    }

    @Override
    public void addAll(Map<String, EmpHealthInsurBenefits> benefits) {
        String INS_SQL = "INSERT INTO QQSDT_KENHO_INFO (INS_DATE, INS_CCD, INS_SCD, INS_PG,"
                + " UPD_DATE, UPD_CCD, UPD_SCD, UPD_PG,"
                + " empHealInsurQiPk.SID, empHealInsurQiPk.HIST_ID, empHealInsurQiPk.CID,"
                + " START_DATE, END_DATE, KAIHO_NUM, KENHO_NUM)"
                + " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
                + " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
                + " SID_VAL, HIST_ID_VAL, CID_VAL,"
                + " START_DATE_VAL, END_DATE_VAL, KAIHO_NUM_VAL, KENHO_NUM_VAL)";
        String cId = AppContexts.user().companyId();
        String insCcd = AppContexts.user().companyCode();
        String insScd = AppContexts.user().employeeCode();
        String insPg = AppContexts.programId();
        String updCcd = insCcd;
        String updScd = insScd;
        String updPg = insPg;
        StringBuilder sb = new StringBuilder();
        benefits.entrySet().stream().forEach(c ->{
            String sql = INS_SQL;
            EmpHealthInsurBenefits benefitsItem = c.getValue();
            sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
            sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
            sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
            sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
            sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

            sql = sql.replace("HIST_ID_VAL", "'" + benefitsItem.identifier()+ "'");
            sql = sql.replace("SID_VAL", "'" + c.getKey() + "'");
            sql = sql.replace("CID_VAL", "'" + cId + "'");
            sql = sql.replace("START_DATE_VAL",  "'"+ benefitsItem.getDatePeriod().start() + "'");
            sql = sql.replace("END_DATE_VAL",  "'"+ benefitsItem.getDatePeriod().end() + "'");
            sb.append(sql);
        });

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void update(EmpHealthInsurBenefits domain) {
        Optional<QqsmtEmpHealInsurQi> existItem = this.queryProxy().find(domain.identifier(), QqsmtEmpHealInsurQi.class);
        if (!existItem.isPresent()){
            throw new RuntimeException("Invalid QqsmtEmpHealInsurQi");
        }
        updateEntity(domain, existItem.get());
        this.commandProxy().update(existItem.get());
    }

    private void updateEntity(EmpHealthInsurBenefits domain, QqsmtEmpHealInsurQi entity){
        entity.startDate = domain.getDatePeriod().start();
        entity.endDate = domain.getDatePeriod().end();
    }

    @Override
    public void updateAll (List<EmpHealthInsurBenefits> items){
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE, END_DATE = END_DATE, KAIHO_NUM = KAIHO_NUM, KENHO_NUM = KENHO_NUM"
                + " WHERE empHealInsurQiPk.HIST_ID = HIST_ID AND empHealInsurQiPk.CID = CID;";
        String cId = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        items.stream().forEach(c -> {
            String sql = UP_SQL;
            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
            sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
            sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

            sql = sql.replace("START_DATE", "'" + c.start() + "'");
            sql = sql.replace("END_DATE", "'" + c.end() + "'");

            sql = sql.replace("HIST_ID", "'" + c.identifier() + "'");
            sql = sql.replace("CID","'" + cId + "'");
            sb.append(sql);
        });
        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void remove(String employeeId, String hisId) {
        this.commandProxy().remove(QqsmtEmpHealInsurQi.class, new QqsmtEmpHealInsurQiPk(employeeId, hisId, AppContexts.user().companyId()));
    }
}
