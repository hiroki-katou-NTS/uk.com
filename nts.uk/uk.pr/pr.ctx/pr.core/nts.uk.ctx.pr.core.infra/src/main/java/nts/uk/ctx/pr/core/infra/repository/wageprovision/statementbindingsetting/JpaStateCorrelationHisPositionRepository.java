package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;



import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPosition;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPositionRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisPos;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisPosPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisPositionRepository extends JpaRepository implements StateCorrelationHisPositionRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisPos f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid AND  f.stateCorHisPosPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisPosPk.cid =:cid ORDER BY f.startYearMonth DESC";

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
        return this.toDomain(listStateCorHisPos);
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisPos.toEntity(cid,history));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisPos.toEntity(cid,history));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisPos.class, new QpbmtStateCorHisPosPk(cid, hisId));
    }

    private Optional<StateCorrelationHisPosition> toDomain(List<QpbmtStateCorHisPos> stateCorHisPos){
        if(stateCorHisPos.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisPosition(stateCorHisPos.get(0).stateCorHisPosPk.cid,stateCorHisPos.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisPosPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
