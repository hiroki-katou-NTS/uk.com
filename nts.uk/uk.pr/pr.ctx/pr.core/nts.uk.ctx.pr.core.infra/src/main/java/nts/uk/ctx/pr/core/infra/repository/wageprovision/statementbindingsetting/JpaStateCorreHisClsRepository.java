package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisCls;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisClsRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisClass;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorreHisClsRepository extends JpaRepository implements StateCorreHisClsRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisClass f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid AND  f.stateCorHisClassPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid ORDER BY f.startYearMonth";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisClass f WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisClass f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisClassPk.cid =:cid AND f.stateCorHisClassPk.hisId =:hisId";
    private static final String SELECT_BY_CID_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public Optional<StateCorreHisCls> getStateCorrelationHisClassificationByCid(String cid){
        List<QpbmtStateCorHisClass> listStateCorHisClass = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisClass == null || listStateCorHisClass.isEmpty()){
            return Optional.empty();
        }

        StateCorreHisCls stateCorreHisCls = new StateCorreHisCls(cid,QpbmtStateCorHisClass.toDomainYearMonth(listStateCorHisClass));
        return Optional.of(stateCorreHisCls);
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSettingMasterByHisId(String cid, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSetMaster(String cid, YearMonth yearMonth) {
        List<QpbmtStateCorHisClass> entitys = this.queryProxy().query(SELECT_BY_CID_YM, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList();
        return QpbmtStateCorHisClass.toDomainSetting(entitys);
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
    public void updateAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().updateAll(QpbmtStateCorHisClass.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth));
    }

    @Override
    public void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().insertAll(QpbmtStateCorHisClass.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorreHisCls> toDomain(List<QpbmtStateCorHisClass> stateCorHisClass){
        if(stateCorHisClass.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorreHisCls(stateCorHisClass.get(0).stateCorHisClassPk.cid,stateCorHisClass.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisClassPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }

    public static List<StateLinkSetMaster> toDomainSetting(List<QpbmtStateCorHisClass> entitys) {
        return entitys.stream().map(x -> x.toDomain()).collect(Collectors.toList());
    }

}
