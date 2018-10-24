package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassification;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisClass;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisClassPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisClassificationRepository extends JpaRepository implements StateCorrelationHisClassificationRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisClass f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisClassPk.cid =:cid AND  f.stateCorHisClassPk.hisId =:hisId ";

    @Override
    public Optional<StateCorrelationHisClassification> getStateCorrelationHisClassificationById(String cid, String hisId){
        List<QpbmtStateCorHisClass> listStateCorHisClass = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisClass.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisClass);
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisClass.toEntity(cid,history));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisClass.toEntity(cid,history));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisClass.class, new QpbmtStateCorHisClassPk(cid, hisId));
    }

    private Optional<StateCorrelationHisClassification> toDomain(List<QpbmtStateCorHisClass> stateCorHisClass){
        if(stateCorHisClass.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisClassification(stateCorHisClass.get(0).stateCorHisClassPk.cid,stateCorHisClass.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisClassPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
