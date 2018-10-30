package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisCom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisComPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisCompanyRepository extends JpaRepository implements StateCorrelationHisCompanyRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisCom f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisComPk.cid =:cid AND  f.stateCorHisComPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisComPk.cid =:cid";

    @Override
    public Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyById(String cid, String hisId){
        List<QpbmtStateCorHisCom> listStateCorHisCom = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateCorHisCom.class)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getList();
        return this.toDomain(listStateCorHisCom);
    }

    @Override
    public Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyById(String cid) {
        List<QpbmtStateCorHisCom> listStateCorHisCom = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisCom.class)
                .setParameter("cid", cid)
                .getList();
        return this.toDomain(listStateCorHisCom);
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history){
        this.commandProxy().insert(QpbmtStateCorHisCom.toEntity(cid,history));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.commandProxy().update(QpbmtStateCorHisCom.toEntity(cid, history));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtStateCorHisCom.class, new QpbmtStateCorHisComPk(cid, hisId));
    }

    private Optional<StateCorrelationHisCompany> toDomain(List<QpbmtStateCorHisCom> stateCorHisCom){
        if(stateCorHisCom.isEmpty()) return Optional.empty();
        return Optional.of(new StateCorrelationHisCompany(stateCorHisCom.get(0).stateCorHisComPk.cid,stateCorHisCom.stream().map(item -> new YearMonthHistoryItem(item.stateCorHisComPk.hisId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }
}
