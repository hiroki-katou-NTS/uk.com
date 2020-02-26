package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEm;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEmRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.StateCorreHisEmAndLinkSetMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisEmp;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorreHisEmRepository extends JpaRepository implements StateCorreHisEmRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisEmp f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid ORDER BY f.endYearMonth DESC";
    private static final String SELECT_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisEmp f WHERE f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisEmp f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId";
    private static final String SELECT_BY_CID_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public Optional<StateCorreHisEm> getStateCorrelationHisEmployeeById(String cid) {
        List<QpbmtStateCorHisEmp> listStateCorHisEmp = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisEmp.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisEmp == null || listStateCorHisEmp.isEmpty()){
            return Optional.empty();
        }
        StateCorreHisEm domain = new StateCorreHisEm(cid,QpbmtStateCorHisEmp.toDomainYearMonth(listStateCorHisEmp));
        return Optional.of(domain);
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSettingMasterByHisId(String cId, String hisId){
        return this.queryProxy().query(SELECT_BY_HISID, QpbmtStateCorHisEmp.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<StateLinkSetMaster> getStateLinkSetMaster(String cid, YearMonth yearMonth) {
        List<QpbmtStateCorHisEmp> entitys = this.queryProxy().query(SELECT_BY_CID_YM, QpbmtStateCorHisEmp.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList();
        return QpbmtStateCorHisEmp.toDomainSetting(entitys);
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
        this.commandProxy().updateAll(QpbmtStateCorHisEmp.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth));
    }

    @Override
    public void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().insertAll(QpbmtStateCorHisEmp.toEntity(cid, stateLinkSetMasters, startYearMonth, endYearMonth));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorreHisEm> toDomain(List<QpbmtStateCorHisEmp> stateCorHisEmp){
        if(stateCorHisEmp.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorreHisEm(stateCorHisEmp.get(0).stateCorHisEmpPk.cid,stateCorHisEmp.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisEmpPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
