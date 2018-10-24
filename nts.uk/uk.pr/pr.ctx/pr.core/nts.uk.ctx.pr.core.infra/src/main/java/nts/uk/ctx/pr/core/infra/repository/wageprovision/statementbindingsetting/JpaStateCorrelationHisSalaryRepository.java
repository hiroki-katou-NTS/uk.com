package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisSalary;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisSalaryRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisSal;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisSalPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisSalaryRepository extends JpaRepository implements StateCorrelationHisSalaryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisSal f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisSalPk.cid =:cid AND  f.stateCorHisSalPk.hisId =:hisId ";


    @Override
    public Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryById(String cid, String hisId){
        List<QpbmtStateCorHisSal> listStateCorHisSal = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisSal.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();

        return this.toDomain(listStateCorHisSal);
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisSal.toEntity(cid,history));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisSal.toEntity(cid,history));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisSal.class, new QpbmtStateCorHisSalPk(cid, hisId));
    }

    private Optional<StateCorrelationHisSalary> toDomain(List<QpbmtStateCorHisSal> stateCorHisSal){
        if(stateCorHisSal.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisSalary(stateCorHisSal.get(0).stateCorHisSalPk.cid,stateCorHisSal.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisSalPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
