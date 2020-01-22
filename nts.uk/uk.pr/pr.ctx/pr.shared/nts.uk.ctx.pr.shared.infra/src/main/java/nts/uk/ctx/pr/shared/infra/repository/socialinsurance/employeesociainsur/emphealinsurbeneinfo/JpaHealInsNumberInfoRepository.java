package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Stateless
public class JpaHealInsNumberInfoRepository extends JpaRepository implements HealInsurNumberInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";
    private static final String GET_ALL_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.hisId IN :hisId";
    private static final String SELECT_BY_ID_EMPIDS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId AND f.empHealInsurQiPk.hisId =:hisId AND  f.empHealInsurQiPk.cid =:cid ";


    @Override
    public List<HealInsurNumberInfor> getAllHealInsurNumberInfor() {
        return null;
    }

    @Override
    public Optional<HealInsurNumberInfor> getHealInsurNumberInforById(String empId, String hisId) {
        val result = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .setParameter("hisId",hisId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle();
        return result.isPresent() ? Optional.of(new HealInsurNumberInfor(result.get().empHealInsurQiPk.hisId, result.get().careIsNumber, result.get().healInsurNumber)) : Optional.empty();
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
            sql = sql.replace("KAIHO_NUM_VAL", c.getCareInsurNumber().isPresent() == true? "'" + c.getCareInsurNumber().get().v()+ "'" : "null");
            sql = sql.replace("KENHO_NUM_VAL",  c.getHealInsNumber().isPresent() == true? "'"+ c.getHealInsNumber().get().v()+ "'" : "null");
            sb.append(sql);
        });

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void update(HealInsurNumberInfor domain) {
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " KAIHO_NUM = KAIHO_NUM_VAL, KENHO_NUM = KENHO_NUM_VAL"
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

        sql = sql.replace("KAIHO_NUM_VAL", domain.getCareInsurNumber().isPresent() == true? "'" + domain.getCareInsurNumber().get().v() + "'": "null");
        sql = sql.replace("KENHO_NUM_VAL", domain.getHealInsNumber().isPresent() == true? "'" + domain.getHealInsNumber().get().v() + "'": "null");

        sql = sql.replace("HIST_ID_VAL", "'" + domain.getHistoryId() + "'");
        sql = sql.replace("CID_VAL", "'" + cid + "'");
        sb.append(sql);

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void updateAll(List<HealInsurNumberInfor> items) {
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " KAIHO_NUM = KAIHO_NUM_VAL, KENHO_NUM = KENHO_NUM_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL;";
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

            sql = sql.replace("KAIHO_NUM_VAL", c.getCareInsurNumber().isPresent() == true? "'" +  c.getCareInsurNumber().get().v() + "'": "null");
            
            sql = sql.replace("KENHO_NUM_VAL", c.getHealInsNumber().isPresent() == true? "'" + c.getHealInsNumber().get().v() + "'" : "null");

            sql = sql.replace("HIST_ID_VAL", "'" + c.getHistoryId() + "'");
            
            sb.append(sql);
        });
        
        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        
        System.out.println(records);
    }

    @Override
    public void remove(String historyId) {
    }
}
