package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPosition;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPositionRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisPos;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisPositionRepository extends JpaRepository implements StateCorrelationHisPositionRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisPos f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid AND  f.stateCorHisPosPk.hisId =:hisId ";
    private static final String SELECT_BY_CID_HISID_MASTERCODE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid AND  f.stateCorHisPosPk.hisId =:hisId AND f.stateCorHisPosPk.masterCode = :masterCode";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid ORDER BY f.startYearMonth DESC";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisPos f WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisPos f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";

    @Override
    public Optional<StateCorrelationHisPosition> getStateCorrelationHisPositionById(String cid, String hisId){
        List<QpbmtStateCorHisPos> listStateCorHisPos = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisPos.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();

        return this.toDomain(listStateCorHisPos);
    }

    @Override
    public Optional<StateCorrelationHisPosition> getStateCorrelationHisPositionByCid(String cid){
        List<QpbmtStateCorHisPos> listStateCorHisPos = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisPos.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisPos == null || listStateCorHisPos.isEmpty()){
            return Optional.empty();
        }
        StateCorrelationHisPosition stateCorrelationHisPosition = new StateCorrelationHisPosition(cid,QpbmtStateCorHisPos.toDomainYearMonth(listStateCorHisPos));
        return Optional.of(stateCorrelationHisPosition);
    }

    @Override
    public List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cId,String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisPos.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }


    @Override
    public Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode) {
        Optional<QpbmtStateCorHisPos> listStateCorHisPos = this.queryProxy().query(SELECT_BY_CID_HISID_MASTERCODE, QpbmtStateCorHisPos.class)
                .setParameter("cid",cid)
                .setParameter("hisId", hisId)
                .setParameter("masterCode",masterCode)
                .getSingle();

        if(!listStateCorHisPos.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(listStateCorHisPos.get().toDomain());
    }
    
    @Override
    public Optional<StateLinkSettingDate> getStateLinkSettingDateById(String cId, String hisId) {
        List<QpbmtStateCorHisPos> result = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisPos.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList();

        return QpbmtStateCorHisPos.getBaseDate(result);
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
        this.commandProxy().updateAll(QpbmtStateCorHisPos.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth,baseDate));
    }

    @Override
    public void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate) {
        this.commandProxy().insertAll(QpbmtStateCorHisPos.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth,baseDate));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorrelationHisPosition> toDomain(List<QpbmtStateCorHisPos> stateCorHisPos){
        if(stateCorHisPos.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisPosition(stateCorHisPos.get(0).stateCorHisPosPk.cid,stateCorHisPos.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisPosPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
