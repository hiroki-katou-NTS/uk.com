package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisEmp;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisEmployeeRepository extends JpaRepository implements StateCorrelationHisEmployeeRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisEmp f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid AND  f.stateCorHisEmpPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid ORDER BY f.endYearMonth DESC";
    private static final String SELECT_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId";
    private static final String SELECT_BY_CID_HISID_MASTERCODE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId AND f.stateCorHisEmpPk.masterCode";
    private static final String REMOVE_BY_HISID = "DELETE FROM QpbmtStateCorHisEmp f WHERE f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId";
    private static final String UPDATE_BY_HISID = "UPDATE  QpbmtStateCorHisEmp f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisEmpPk.cid =:cid AND f.stateCorHisEmpPk.hisId =:hisId";

    @Override
    public Optional<StateCorrelationHisEmployee> getStateCorrelationHisEmployeeById(String cid, String hisId){
        List<QpbmtStateCorHisEmp> listStateCorHisEmp = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisEmp.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisEmp);
    }

    @Override
    public Optional<StateCorrelationHisEmployee> getStateCorrelationHisEmployeeById(String cid) {
        List<QpbmtStateCorHisEmp> listStateCorHisEmp = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisEmp.class)
                .setParameter("cid", cid)
                .getList();
        if(listStateCorHisEmp == null || listStateCorHisEmp.isEmpty()){
            return Optional.empty();
        }
        StateCorrelationHisEmployee domain = new StateCorrelationHisEmployee(cid,QpbmtStateCorHisEmp.toDomainYearMonth(listStateCorHisEmp));
        return Optional.of(domain);
    }

    @Override
    public List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cId, String hisId){
        return this.queryProxy().query(SELECT_BY_HISID, QpbmtStateCorHisEmp.class)
                .setParameter("cid",cId)
                .setParameter("hisId", hisId)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode) {
        Optional<QpbmtStateCorHisEmp> listStateCorHisEmp = this.queryProxy().query(SELECT_BY_CID_HISID_MASTERCODE, QpbmtStateCorHisEmp.class)
                .setParameter("cid",cid)
                .setParameter("hisId", hisId)
                .setParameter("masterCode",masterCode)
                .getSingle();

        if(!listStateCorHisEmp.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(listStateCorHisEmp.get().toDomain());

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
        this.commandProxy().updateAll(QpbmtStateCorHisEmp.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth));
    }

    @Override
    public void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth) {
        this.commandProxy().insertAll(QpbmtStateCorHisEmp.toEntity(cid, stateLinkSettingMasters, startYearMonth, endYearMonth));
    }


    @Override
    public void removeAll(String cid, String hisId) {
        this.getEntityManager().createQuery(REMOVE_BY_HISID)
                .setParameter("cid",cid)
                .setParameter("hisId",hisId)
                .executeUpdate();
    }

    private Optional<StateCorrelationHisEmployee> toDomain(List<QpbmtStateCorHisEmp> stateCorHisEmp){
        if(stateCorHisEmp.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisEmployee(stateCorHisEmp.get(0).stateCorHisEmpPk.cid,stateCorHisEmp.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisEmpPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
