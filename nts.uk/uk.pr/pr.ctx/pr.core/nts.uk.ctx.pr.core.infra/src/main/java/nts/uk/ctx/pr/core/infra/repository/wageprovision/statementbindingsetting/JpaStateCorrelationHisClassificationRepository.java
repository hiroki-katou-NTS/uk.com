package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassification;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisClass;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisClassificationRepository extends JpaRepository implements StateCorrelationHisClassificationRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisClass f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid AND  f.stateCorHisClassPk.hisId =:hisId ";
    private static final String SELECT_BY_CID_HISID_MASTERCODE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid AND  f.stateCorHisClassPk.hisId =:hisId AND f.stateCorHisClassPk.masterCode = :masterCode";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid ORDER BY f.startYearMonth";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisClass f WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisClass f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";
    
    @Override
    public Optional<StateCorrelationHisClassification> getStateCorrelationHisClassificationById(String cid, String hisId){
        List<QpbmtStateCorHisClass> listStateCorHisClass = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisClass);
    }

    @Override
    public Optional<StateCorrelationHisClassification> getStateCorrelationHisClassificationByCid(String cid){
        List<QpbmtStateCorHisClass> listStateCorHisClass = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisClass == null || listStateCorHisClass.isEmpty()){
            return Optional.empty();
        }

        StateCorrelationHisClassification stateCorrelationHisClassification = new StateCorrelationHisClassification(cid,QpbmtStateCorHisClass.toDomainYearMonth(listStateCorHisClass));
        return Optional.of(stateCorrelationHisClassification);
    }

    @Override
    public List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cid, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode) {
        Optional<QpbmtStateCorHisClass> listStateCorHisClass = this.queryProxy().query(SELECT_BY_CID_HISID_MASTERCODE, QpbmtStateCorHisClass.class)
                .setParameter("cid",cid)
                .setParameter("hisId", hisId)
                .setParameter("masterCode",masterCode)
                .getSingle();

        if(!listStateCorHisClass.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(listStateCorHisClass.get().toDomain());
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
        this.commandProxy().updateAll(QpbmtStateCorHisClass.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth));
    }

    @Override
    public void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().insertAll(QpbmtStateCorHisClass.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorrelationHisClassification> toDomain(List<QpbmtStateCorHisClass> stateCorHisClass){
        if(stateCorHisClass.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisClassification(stateCorHisClass.get(0).stateCorHisClassPk.cid,stateCorHisClass.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisClassPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
