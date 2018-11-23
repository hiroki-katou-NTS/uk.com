package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisSalary;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisSalaryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisClass;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisSal;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisSalaryRepository extends JpaRepository implements StateCorrelationHisSalaryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisSal f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid ORDER BY f.startYearMonth DESC";
    private static final String SELECT_BY_KEY = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId";
    private static final String SELECT_BY_CID_HISID_MASTERCODE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId AND f.stateCorHisSalPk.masterCode = :masterCode";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisSal f WHERE f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisSal f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId";

    @Override
    public  Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryByCid(String cid){
        List<QpbmtStateCorHisSal> listStateCorHisSal = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisSal.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisSal == null || listStateCorHisSal.isEmpty()){
            return Optional.empty();
        }
        StateCorrelationHisSalary stateCorrelationHisSalary = new StateCorrelationHisSalary(cid,QpbmtStateCorHisSal.toDomainYearMonth(listStateCorHisSal));
        return Optional.of(stateCorrelationHisSalary);
    }

    @Override
    public Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryByKey(String cid, String hisId) {
        List<QpbmtStateCorHisSal> listStateCorHisSal = this.queryProxy().query(SELECT_BY_KEY, QpbmtStateCorHisSal.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisSal);
    }

    @Override
    public List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cId, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY, QpbmtStateCorHisSal.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode) {
        Optional<QpbmtStateCorHisSal> listStateCorHisSal = this.queryProxy().query(SELECT_BY_CID_HISID_MASTERCODE, QpbmtStateCorHisSal.class)
                .setParameter("cid",cid)
                .setParameter("hisId", hisId)
                .setParameter("masterCode",masterCode)
                .getSingle();

        if(!listStateCorHisSal.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(listStateCorHisSal.get().toDomain());
    }
    
    @Override
    public void update(String cid, YearMonthHistoryItem history) {
        this.getEntityManager().createQuery(UPDATE_BY_HISID)
                .setParameter("startYearMonth",history.start().v())
                .setParameter("endYearMonth",history.end().v())
                .setParameter("cid",cid)
                .setParameter("hisId",history.identifier())
                .executeUpdate();
    }

    @Override
    public void updateAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().updateAll(QpbmtStateCorHisSal.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth));
    }

    @Override
    public void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().insertAll(QpbmtStateCorHisSal.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorrelationHisSalary> toDomain(List<QpbmtStateCorHisSal> stateCorHisSal){
        if(stateCorHisSal.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisSalary(stateCorHisSal.get(0).stateCorHisSalPk.cid,stateCorHisSal.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisSalPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
