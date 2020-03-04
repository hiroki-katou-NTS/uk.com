package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPo;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPoRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisPos;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorreHisPoRepository extends JpaRepository implements StateCorreHisPoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisPos f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid AND  f.stateCorHisPosPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid ORDER BY f.startYearMonth DESC";
    private static final String SELECT_BASE_DATE = "SELECT TOP (1) HIST_ID, JOB_BASE_DATE  FROM QPBMT_STATE_COR_HIS_POS WHERE  CID = ?cid AND  HIST_ID = ?hisId ";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisPos f WHERE f.stateCorHisPosPk.cid =:cid AND f.stateCorHisPosPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisPos f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisPosPk.cid =:cid AND f.stateCorHisPosPk.hisId =:hisId";
    private static final String SELECT_BY_CID_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public Optional<StateCorreHisPo> getStateCorrelationHisPositionByCid(String cid){
        List<QpbmtStateCorHisPos> listStateCorHisPos = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisPos.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisPos == null || listStateCorHisPos.isEmpty()){
            return Optional.empty();
        }
        StateCorreHisPo stateCorreHisPo = new StateCorreHisPo(cid,QpbmtStateCorHisPos.toDomainYearMonth(listStateCorHisPos));
        return Optional.of(stateCorreHisPo);
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSettingMasterByHisId(String cId, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisPos.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSetDate> getStateLinkSettingDateById(String cId, String hisId) {
        Object[] resultQuery = null;
        resultQuery = (Object[]) this.getEntityManager().createNativeQuery(SELECT_BASE_DATE)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getSingleResult();
        return QpbmtStateCorHisPos.toBaseDate(resultQuery);
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSetMaster(String cid, YearMonth yearMonth) {
        List<QpbmtStateCorHisPos> entitys = this.queryProxy().query(SELECT_BY_CID_YM, QpbmtStateCorHisPos.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList();
        return QpbmtStateCorHisPos.toDomainSetting(entitys);
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
    public void updateAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        this.commandProxy().updateAll(QpbmtStateCorHisPos.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth,baseDate));
    }

    @Override
    public void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        this.commandProxy().insertAll(QpbmtStateCorHisPos.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth,baseDate));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorreHisPo> toDomain(List<QpbmtStateCorHisPos> stateCorHisPos){
        if(stateCorHisPos.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorreHisPo(stateCorHisPos.get(0).stateCorHisPosPk.cid,stateCorHisPos.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisPosPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
