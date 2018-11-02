package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisEmp;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisEmpPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisEmployeeRepository extends JpaRepository implements StateCorrelationHisEmployeeRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisEmp f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid AND  f.stateCorHisEmpPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisEmpPk.cid =:cid ";

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
        return this.toDomain(listStateCorHisEmp);
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisEmp.toEntity(cid,history));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisEmp.toEntity(cid,history));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisEmp.class, new QpbmtStateCorHisEmpPk(cid, hisId));
    }

    private Optional<StateCorrelationHisEmployee> toDomain(List<QpbmtStateCorHisEmp> stateCorHisEmp){
        if(stateCorHisEmp.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisEmployee(stateCorHisEmp.get(0).stateCorHisEmpPk.cid,stateCorHisEmp.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisEmpPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
