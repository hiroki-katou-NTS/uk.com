package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparment;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisDep;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisDepPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisDeparmentRepository extends JpaRepository implements StateCorrelationHisDeparmentRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisDep f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid AND  f.stateCorHisDepPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisDepPk.cid =:cid ORDER BY f.endYearMonth DESC";

    @Override
    public Optional<StateCorrelationHisDeparment> getStateCorrelationHisDeparmentById(String cid, String hisId){

        List<QpbmtStateCorHisDep> listStateCorHisDep = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisDep.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();

        return this.toDomain(listStateCorHisDep);
    }

    @Override
    public Optional<StateCorrelationHisDeparment> getStateCorrelationHisDeparmentById(String cid) {
        List<QpbmtStateCorHisDep> listStateCorHisDep = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisDep.class)
                .setParameter("cid", cid)
                .getList();
        return this.toDomain(listStateCorHisDep);
    }

    @Override
    public Optional<StateCorrelationHisDeparment> getStateCorrelationHisDeparmentByDate(String cid, YearMonthHistoryItem baseHistory) {
        return Optional.empty();
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisDep.toEntity(cid,history));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisDep.toEntity(cid,history));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisDep.class, new QpbmtStateCorHisDepPk(cid, hisId));
    }

    private Optional<StateCorrelationHisDeparment> toDomain(List<QpbmtStateCorHisDep> stateCorHisDep){
        if(stateCorHisDep.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisDeparment(stateCorHisDep.get(0).stateCorHisDepPk.cid,stateCorHisDep.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisDepPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
