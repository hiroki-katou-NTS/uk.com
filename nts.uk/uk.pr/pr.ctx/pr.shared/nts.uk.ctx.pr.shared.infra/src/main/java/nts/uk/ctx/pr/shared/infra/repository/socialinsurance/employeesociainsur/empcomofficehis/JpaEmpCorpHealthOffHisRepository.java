package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffParam;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHisPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Stateless
public class JpaEmpCorpHealthOffHisRepository extends JpaRepository implements EmpCorpHealthOffHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND  f.empCorpOffHisPk.historyId =:hisId ";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId ";
    private static final String SELECT_BY_KEY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId IN :employeeIds  AND f.empCorpOffHisPk.cid =:cid AND f.startDate <= :startDate AND f.endDate >= :startDate ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.cid =:cid AND f.empCorpOffHisPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate ";
    private static final String SELECT_BY_SIDS = SELECT_ALL_QUERY_STRING + " WHERE f.empCorpOffHisPk.employeeId IN :sids AND f.empCorpOffHisPk.cid =:cid";
    private static final String SELECT_BY_ID_AND_BASE_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empCorpOffHisPk.employeeId =:sid AND f.empCorpOffHisPk.cid =:cid AND f.startDate <= :baseDate AND f.endDate >= :baseDate ";

    @Override
    public List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis(){
        return null;
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId){
        val result = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", employeeId)
                .setParameter("hisId", hisId)
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpCorpOffHis.toDomain(result));
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

    @Override
    public Optional<EmpCorpHealthOffHis> getBySidAndBaseDate(String sid, GeneralDate baseDate){
        List<QqsmtEmpCorpOffHis> result =  this.queryProxy().query(SELECT_BY_ID_AND_BASE_DATE, QqsmtEmpCorpOffHis.class)
                .setParameter("sid", sid)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("baseDate", baseDate)
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpCorpOffHis.toDomain(result));
    }

    @Override
    public List<EmpCorpHealthOffHis> getByCidAndSids(List<String> sids){
        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_SIDS, QqsmtEmpCorpOffHis.class)
                .setParameter("sids", sids)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();
        Map<String, List<QqsmtEmpCorpOffHis>> mapResult = qqsmtEmpCorpOffHis
                .stream().collect(Collectors.groupingBy(e -> e.empCorpOffHisPk.employeeId));
        List<EmpCorpHealthOffHis> result = new ArrayList<>();
        mapResult.forEach( (key,value) -> {
            result.add(QqsmtEmpCorpOffHis.toDomain(value));
        });
        return result;
    }

    @Override
    public void add(EmpCorpHealthOffHis domain, DateHistoryItem itemAdded, AffOfficeInformation itemInfo) {
        this.commandProxy().insert(new QqsmtEmpCorpOffHis(
                new QqsmtEmpCorpOffHisPk(),
                itemAdded.start(),
                itemAdded.end(),
                itemInfo.getSocialInsurOfficeCode().v()
        ));
    }

    @Override
    public void update(DateHistoryItem domain) {
        Optional<QqsmtEmpCorpOffHis> itemToBeUpdated = this.queryProxy().find(domain.identifier(), QqsmtEmpCorpOffHis.class);
        if (!itemToBeUpdated.isPresent()){
            throw new RuntimeException("Invalid QqsmtEmpCorpOffHis");
        }
        QqsmtEmpCorpOffHis itemUpdate = new QqsmtEmpCorpOffHis(
                itemToBeUpdated.get().empCorpOffHisPk,
                domain.start(),
                domain.end(),
                itemToBeUpdated.get().socialInsuranceOfficeCd
        );
        this.commandProxy().update(itemUpdate);
    }

    @Override
    public void update(DateHistoryItem domain, AffOfficeInformation info){
        Optional<QqsmtEmpCorpOffHis> itemToBeUpdated = this.queryProxy().find(domain.identifier(), QqsmtEmpCorpOffHis.class);
        if (!itemToBeUpdated.isPresent()){
            throw new RuntimeException("Invalid QqsmtEmpCorpOffHis");
        }
        QqsmtEmpCorpOffHis itemUpdate = new QqsmtEmpCorpOffHis(
                itemToBeUpdated.get().empCorpOffHisPk,
                domain.start(),
                domain.end(),
                info.getSocialInsurOfficeCode().v()
        );
        this.commandProxy().update(itemUpdate);
    }

    @Override
    public void delete(String hisId) {
        Optional<QqsmtEmpCorpOffHis> itemToBeDeleted = this.queryProxy().find(hisId, QqsmtEmpCorpOffHis.class);
        if (!itemToBeDeleted.isPresent()){
            throw new RuntimeException("Invalid QqsmtEmpCorpOffHis");
        }
        this.commandProxy().remove(QqsmtEmpCorpOffHis.class, hisId);
    }

    @Override
    public void addAll(List<EmpCorpHealthOffParam> domains) {
        String INS_SQL = "INSERT INTO QQSDT_SYAHO_OFFICE_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
                + " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG,"
                + " HIST_ID, SID, CID,"
                + " START_DATE, END_DATE, SYAHO_OFFICE_CD)"
                + " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
                + " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
                + " HIST_ID_VAL, SID_VAL, CID_VAL, START_DATE_VAL, END_DATE_VAL, SYAHO_OFFICE_CD); ";
        String cid = AppContexts.user().companyId();
        String insCcd = AppContexts.user().companyCode();
        String insScd = AppContexts.user().employeeCode();
        String insPg = AppContexts.programId();

        String updCcd = insCcd;
        String updScd = insScd;
        String updPg = insPg;
        StringBuilder sb = new StringBuilder();
        domains.stream().forEach(c ->{
            String sql = INS_SQL;
            DateHistoryItem dateHistItem = c.getHistoryItem();
            sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
            sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
            sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
            sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
            sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

            sql = sql.replace("HIST_ID_VAL", "'" + dateHistItem.identifier() + "'");
            sql = sql.replace("CID_VAL", "'" + cid + "'");
            sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
            sql = sql.replace("START_DATE_VAL", "'" + dateHistItem.start() + "'");
            sql = sql.replace("END_DATE_VAL","'" +  dateHistItem.end() + "'");
            sql = sql.replace("SYAHO_OFFICE_CD","'" +  c.getSocialInsurOfficeCode() + "'");

            sb.append(sql);
        });

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void updateAll(List<DateHistoryItem> items) {
        String UP_SQL = "UPDATE QQSDT_SYAHO_OFFICE_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        String cid = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        items.stream().forEach(c ->{
            String sql = UP_SQL;
            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
            sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
            sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");

            sql = sql.replace("START_DATE_VAL", "'" + c.start() + "'");
            sql = sql.replace("END_DATE_VAL","'" +  c.end() + "'");

            sql = sql.replace("HIST_ID_VAL", "'" + c.identifier() +"'");
            sql = sql.replace("CID_VAL", "'" + cid +"'");
            sb.append(sql);
        });
        int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

}
