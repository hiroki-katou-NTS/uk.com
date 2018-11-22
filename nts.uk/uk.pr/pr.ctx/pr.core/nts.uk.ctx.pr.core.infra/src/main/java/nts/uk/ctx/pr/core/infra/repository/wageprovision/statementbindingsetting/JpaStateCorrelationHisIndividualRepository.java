package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividual;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividualRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisIndi;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisIndiPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisIndividualRepository extends JpaRepository implements StateCorrelationHisIndividualRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisIndi f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisIndiPk.empId =:empId AND  f.stateCorHisIndiPk.hisId =:hisId ";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisIndiPk.empId =:empId  ORDER BY f.startYearMonth DESC";

    @Override
    public Optional<StateCorrelationHisIndividual> getStateCorrelationHisIndividualById(String empId, String hisId){
        List<QpbmtStateCorHisIndi> listStateCorHisIndi = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisIndi.class)
                .setParameter("empId", empId)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisIndi);
    }

    @Override
    public Optional<StateCorrelationHisIndividual> getStateCorrelationHisIndividualByEmpId(String empId) {
        List<QpbmtStateCorHisIndi> listStateCorHisIndi = this.queryProxy().query(SELECT_BY_EMP_ID, QpbmtStateCorHisIndi.class)
                .setParameter("empId", empId)
                .getList();
        return this.toDomain(listStateCorHisIndi);
    }

    @Override
    public void add(String empId, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisIndi.toEntity(empId,history));
    }

    @Override
    public void update(String empId, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisIndi.toEntity(empId,history));
    }

    @Override
    public void remove(String empId, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisIndi.class, new QpbmtStateCorHisIndiPk(empId, hisId));
    }

    private Optional<StateCorrelationHisIndividual> toDomain(List<QpbmtStateCorHisIndi> stateCorHisIndi){
        if(stateCorHisIndi.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisIndividual(stateCorHisIndi.get(0).stateCorHisIndiPk.empId,stateCorHisIndi.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisIndiPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
