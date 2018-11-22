package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparment;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisDep;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisDeparmentRepository extends JpaRepository implements StateCorrelationHisDeparmentRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisDep f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid ORDER BY f.endYearMonth DESC";
    private static final String SELECT_BY_CID_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid AND f.stateCorHisDepPk.hisId = :hisId";
    private static final String SELECT_BY_CID_HISID_MASTERCODE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid AND f.stateCorHisDepPk.hisId = :hisId AND f.stateCorHisDepPk.masterCode = :masterCode";
    private static final String SELECT_BY_DATE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid AND f.startYearMonth <=:basedate AND f.endYearMonth >=:basedate ";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisDep f WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisDep f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";


    @Override
    public Optional<StateCorrelationHisDeparment> getStateCorrelationHisDeparmentById(String cid) {
        List<QpbmtStateCorHisDep> listStateCorHisDep = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisDep.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisDep == null || listStateCorHisDep.isEmpty()){
            return Optional.empty();
        }

        StateCorrelationHisDeparment stateCorrelationHisDeparment = new StateCorrelationHisDeparment(cid,QpbmtStateCorHisDep.toDomainYearMonth(listStateCorHisDep));
        return Optional.of(stateCorrelationHisDeparment);
    }

    @Override
    public Optional<StateCorrelationHisDeparment> getStateCorrelationHisDeparmentByDate(String cid, GeneralDate baseDate) {
        List<QpbmtStateCorHisDep> listStateCorHisDep = this.queryProxy().query(SELECT_BY_DATE, QpbmtStateCorHisDep.class)
                .setParameter("cid", cid)
                .setParameter("basedate", baseDate.yearMonth().toString())
                .getList();
        return this.toDomain(listStateCorHisDep);
    }

    @Override
    public Optional<StateLinkSettingDate> getStateLinkSettingDateById(String cId, String hisId) {
        List<QpbmtStateCorHisDep> result = this.queryProxy().query(SELECT_BY_CID_HISID, QpbmtStateCorHisDep.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList();

        return QpbmtStateCorHisDep.getBaseDate(result);
    }

    @Override
    public Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode) {
        Optional<QpbmtStateCorHisDep> listStateCorHisDep = this.queryProxy().query(SELECT_BY_CID_HISID_MASTERCODE, QpbmtStateCorHisDep.class)
                .setParameter("cid",cid)
                .setParameter("hisId", hisId)
                .setParameter("masterCode",masterCode)
                .getSingle();

        if(!listStateCorHisDep.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(listStateCorHisDep.get().toDomain());
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
    public void updateAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        this.commandProxy().updateAll(QpbmtStateCorHisDep.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth,baseDate));
    }

    @Override
    public void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        this.commandProxy().insertAll(QpbmtStateCorHisDep.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth,baseDate));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorrelationHisDeparment> toDomain(List<QpbmtStateCorHisDep> stateCorHisDep){
        if(stateCorHisDep.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisDeparment(stateCorHisDep.get(0).stateCorHisDepPk.cid,stateCorHisDep.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisDepPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
