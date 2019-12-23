package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Stateless
public class JpaHealInsNumberInfoRepository extends JpaRepository implements HealInsurNumberInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";
    private static final String GET_ALL_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.hisId =:hisId";
    private static final String SELECT_BY_ID_EMPIDS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId";


    @Override
    public List<HealInsurNumberInfor> getAllHealInsurNumberInfor() {
        return null;
    }

    @Override
    public Optional<HealInsurNumberInfor> getHealInsurNumberInforById(String historyId) {
        Optional<QqsmtEmpHealInsurQi> optionData = this.queryProxy().find(historyId, QqsmtEmpHealInsurQi.class);
        if (optionData.isPresent()){
            return Optional.of(toDomain(optionData.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<HealInsurNumberInfor> findByHistoryId(List<String> hisId){
        if (hisId.isEmpty()){
            return new ArrayList<>();
        }
        List<HealInsurNumberInfor> results = new ArrayList<>();
        CollectionUtil.split(hisId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, e -> {
            List<HealInsurNumberInfor> subResults = this.queryProxy()
                    .query(GET_ALL_BY_HISID, QqsmtEmpHealInsurQi.class)
                    .setParameter("hisId", e)
                    .getList(c->toDomain(c));
            results.addAll(subResults);
        });
        return results;
    }

    @Override
    public Optional<HealInsurNumberInfor> getHealInsNumberInfoById(String cid, String empId) {
        List<QqsmtEmpHealInsurQi> list = this.queryProxy()
                .query(SELECT_BY_ID_EMPIDS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .getList();
        return Optional.empty();
    }

    private HealInsurNumberInfor toDomain(QqsmtEmpHealInsurQi data){
        return HealInsurNumberInfor.createFromJavaType(data.empHealInsurQiPk.getHisId(), data.careIsNumber, data.healInsurNumber);
    }

    @Override
    public void add(HealInsurNumberInfor numberInfor) {
        commandProxy().insert(toEntity(numberInfor));
        this.getEntityManager().flush();
    }

    private QqsmtEmpHealInsurQi toEntity(HealInsurNumberInfor domain){
        String cId = AppContexts.user().companyId();
        QqsmtEmpHealInsurQiPk entityPk = new QqsmtEmpHealInsurQiPk(null,domain.getHistoryId(), cId);

        return new QqsmtEmpHealInsurQi(entityPk, null, null, domain.getCareInsurNumber().map(e->e.v()).orElse(null), domain.getHealInsNumber().map(e->e.v()).orElse(null));
    }

    @Override
    public void addAll(List<HealInsurNumberInfor> domains) {
        String INS_SQL = "INSERT INTO QQSDT_KENHO_INFO (INS_DATE, INS_CCD, INS_SCD, INS_PG,"
                + " UPD_DATE, UPD_CCD, UPD_SCD, UPD_PG,"
                + " empHealInsurQiPk.SID, empHealInsurQiPk.HIST_ID, empHealInsurQiPk.CID,"
                + " START_DATE, END_DATE, KAIHO_NUM, KENHO_NUM)"
                + " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
                + " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
                + " SID_VAL, HIST_ID_VAL, CID_VAL,"
                + " START_DATE_VAL, END_DATE_VAL, KAIHO_NUM_VAL, KENHO_NUM_VAL)";
        String insCcd = AppContexts.user().companyCode();
        String insScd = AppContexts.user().employeeCode();
        String insPg = AppContexts.programId();
        String updCcd = insCcd;
        String updScd = insScd;
        String updPg = insPg;
        StringBuilder sb = new StringBuilder();
        domains.stream().forEach(c ->{
            String sql = INS_SQL;
            sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
            sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
            sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
            sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
            sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

            sql = sql.replace("HIST_ID_VAL", "'" + c.getHistoryId() + "'");
            sql = sql.replace("KAIHO_NUM_VAL", "'" + c.getCareInsurNumber()+ "'");
            sql = sql.replace("KENHO_NUM_VAL",  "'"+ c.getHealInsNumber()+ "'");
            sb.append(sql);
        });

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void update(HealInsurNumberInfor domain) {
        Optional<QqsmtEmpHealInsurQi> existItem = this.queryProxy().find(domain.getHistoryId(), QqsmtEmpHealInsurQi.class);
        if (!existItem.isPresent()){
            throw new RuntimeException("Invalid QqsmtEmpHealInsurQi");
        }
        updateEntity(domain, existItem.get());
        this.commandProxy().update(existItem.get());
    }

    private void updateEntity(HealInsurNumberInfor domain, QqsmtEmpHealInsurQi entity){
        entity.empHealInsurQiPk.hisId = domain.getHistoryId();
        entity.careIsNumber = domain.getCareInsurNumber().map(c->c.v()).orElse(null);
        entity.healInsurNumber = domain.getHealInsNumber().map(c->c.v()).orElse(null);
    }

    @Override
    public void updateAll(List<HealInsurNumberInfor> items) {
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE, END_DATE = END_DATE, KAIHO_NUM = KAIHO_NUM, KENHO_NUM = KENHO_NUM"
                + " WHERE empHealInsurQiPk.HIST_ID = HIST_ID AND empHealInsurQiPk.CID = CID;";
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

            sql = sql.replace("KAIHO_NUM", "'" + c.getCareInsurNumber() + "'");
            sql = sql.replace("KENHO_NUM", "'" + c.getHealInsNumber() + "'");

            sql = sql.replace("HIST_ID", "'" + c.getHistoryId() + "'");
            sb.append(sql);
        });
        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void remove(String historyId) {

    }
}
