package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.StateCorreHisAndLinkSetIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndiviRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetIndivi;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisIndi;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisIndiPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorreHisIndiviRepository extends JpaRepository implements StateCorreHisIndiviRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisIndi f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisIndiPk.empId =:empId AND  f.stateCorHisIndiPk.hisId =:hisId ";
    private static final String SELECT_BY_KEYS_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisIndiPk.empId IN :empIds " +
            " AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisIndiPk.empId =:empId  ORDER BY f.startYearMonth DESC";
    private static final String UPDATE_BY_HISID = "UPDATE QpbmtStateCorHisIndi f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisIndiPk.empId =:empId AND f.stateCorHisIndiPk.hisId =:hisId";


    @Override
    public Optional<StateCorreHisIndivi> getStateCorrelationHisIndividualById(String empId, String hisId){
        List<QpbmtStateCorHisIndi> listStateCorHisIndi = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisIndi.class)
                .setParameter("empId", empId)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisIndi);
    }

    @Override
    public Optional<StateLinkSetIndivi> getStateLinkSettingIndividualById(String empId, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisIndi.class)
                .setParameter("empId", empId)
                .setParameter("hisId", hisId)
                .getSingle(c->c.toDomain());
    }


    @Override
    public Optional<StateCorreHisIndivi> getStateCorrelationHisIndividualByEmpId(String empId) {
        List<QpbmtStateCorHisIndi> listStateCorHisIndi = this.queryProxy().query(SELECT_BY_EMP_ID, QpbmtStateCorHisIndi.class)
                .setParameter("empId", empId)
                .getList();
        return this.toDomain(listStateCorHisIndi);
    }

    @Override
    public StateCorreHisAndLinkSetIndivi getStateCorreHisAndLinkSetIndivi(List<String> empIds, YearMonth yearMonth) {
        StateCorreHisAndLinkSetIndivi result = new StateCorreHisAndLinkSetIndivi(Collections.emptyList(), Collections.emptyList());
        if (empIds == null || empIds.isEmpty()) return result;
        List<QpbmtStateCorHisIndi> entitys = this.queryProxy().query(SELECT_BY_KEYS_STRING, QpbmtStateCorHisIndi.class)
                .setParameter("empIds", empIds)
                .setParameter("yearMonth", yearMonth.v())
                .getList();
        result.setHistorys(QpbmtStateCorHisIndi.toDomainHistory(entitys));
        result.setSettings(QpbmtStateCorHisIndi.toDomainSetting(entitys));
        return result;
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode){
        this.commandProxy().insert(QpbmtStateCorHisIndi.toEntity(cid,history,salaryCode,bonusCode));
    }

    @Override
    public void update(String empId, YearMonthHistoryItem history){
        this.getEntityManager().createQuery(UPDATE_BY_HISID)
                .setParameter("startYearMonth",history.start().v())
                .setParameter("endYearMonth",history.end().v())
                .setParameter("empId",empId)
                .setParameter("hisId",history.identifier())
                .executeUpdate();
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode){
        this.commandProxy().update(QpbmtStateCorHisIndi.toEntity(cid, history,salaryCode,bonusCode));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisIndi.class, new QpbmtStateCorHisIndiPk(cid, hisId));
    }

    private Optional<StateCorreHisIndivi> toDomain(List<QpbmtStateCorHisIndi> stateCorHisIndi){
        if(stateCorHisIndi.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorreHisIndivi(stateCorHisIndi.get(0).stateCorHisIndiPk.empId,stateCorHisIndi.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisIndiPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
