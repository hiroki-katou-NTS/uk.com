package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSala;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSalaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisSal;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorreHisSaRepository extends JpaRepository implements StateCorreHisSalaRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisSal f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid ORDER BY f.startYearMonth DESC";
    private static final String SELECT_BY_KEY = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisSal f WHERE f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisSal f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisSalPk.cid =:cid AND f.stateCorHisSalPk.hisId =:hisId";
    private static final String SELECT_BY_CID_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public  Optional<StateCorreHisSala> getStateCorrelationHisSalaryByCid(String cid){
        List<QpbmtStateCorHisSal> listStateCorHisSal = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisSal.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisSal == null || listStateCorHisSal.isEmpty()){
            return Optional.empty();
        }
        StateCorreHisSala stateCorreHisSala = new StateCorreHisSala(cid,QpbmtStateCorHisSal.toDomainYearMonth(listStateCorHisSal));
        return Optional.of(stateCorreHisSala);
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSettingMasterByHisId(String cId, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY, QpbmtStateCorHisSal.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSetMaster(String cid, YearMonth yearMonth) {
        List<QpbmtStateCorHisSal> entitys = this.queryProxy().query(SELECT_BY_CID_YM, QpbmtStateCorHisSal.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList();
        return QpbmtStateCorHisSal.toDomainSetting(entitys);
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
        this.commandProxy().updateAll(QpbmtStateCorHisSal.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth));
    }

    @Override
    public void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().insertAll(QpbmtStateCorHisSal.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorreHisSala> toDomain(List<QpbmtStateCorHisSal> stateCorHisSal){
        if(stateCorHisSal.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorreHisSala(stateCorHisSal.get(0).stateCorHisSalPk.cid,stateCorHisSal.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisSalPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
