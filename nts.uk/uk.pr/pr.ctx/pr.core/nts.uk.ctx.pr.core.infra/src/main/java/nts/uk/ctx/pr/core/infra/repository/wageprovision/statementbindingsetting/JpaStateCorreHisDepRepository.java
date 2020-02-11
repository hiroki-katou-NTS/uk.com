package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisDepar;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisDeparRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisDep;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorreHisDepRepository extends JpaRepository implements StateCorreHisDeparRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisDep f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid ORDER BY f.endYearMonth DESC";
    private static final String SELECT_BY_CID_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid AND f.stateCorHisDepPk.hisId = :hisId";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisDep f WHERE f.stateCorHisDepPk.cid =:cid AND f.stateCorHisDepPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisDep f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisDepPk.cid =:cid AND f.stateCorHisDepPk.hisId =:hisId";
    private static final String SELECT_BY_CID_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public Optional<StateCorreHisDepar> getStateCorrelationHisDeparmentById(String cid) {
        List<QpbmtStateCorHisDep> listStateCorHisDep = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisDep.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisDep == null || listStateCorHisDep.isEmpty()){
            return Optional.empty();
        }

        StateCorreHisDepar stateCorreHisDepar = new StateCorreHisDepar(cid,QpbmtStateCorHisDep.toDomainYearMonth(listStateCorHisDep));
        return Optional.of(stateCorreHisDepar);
    }

    @Override
    public Optional<StateLinkSetDate> getStateLinkSettingDateById(String cId, String hisId) {
        List<QpbmtStateCorHisDep> result = this.queryProxy().query(SELECT_BY_CID_HISID, QpbmtStateCorHisDep.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList();

        return QpbmtStateCorHisDep.getBaseDate(result);
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSetMaster(String cid, YearMonth yearMonth) {
        List<QpbmtStateCorHisDep> entitys = this.queryProxy().query(SELECT_BY_CID_YM, QpbmtStateCorHisDep.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList();
        return QpbmtStateCorHisDep.toDomainSetting(entitys);
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
        this.commandProxy().updateAll(QpbmtStateCorHisDep.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth,baseDate));
    }

    @Override
    public void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        this.commandProxy().insertAll(QpbmtStateCorHisDep.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth,baseDate));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorreHisDepar> toDomain(List<QpbmtStateCorHisDep> stateCorHisDep){
        if(stateCorHisDep.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorreHisDepar(stateCorHisDep.get(0).stateCorHisDepPk.cid,stateCorHisDep.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisDepPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
