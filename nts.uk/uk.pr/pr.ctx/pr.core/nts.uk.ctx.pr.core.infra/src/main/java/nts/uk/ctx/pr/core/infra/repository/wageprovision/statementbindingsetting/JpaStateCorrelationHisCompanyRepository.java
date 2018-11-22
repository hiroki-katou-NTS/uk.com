package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementbindingsetting;


import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisCom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting.QpbmtStateCorHisComPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStateCorrelationHisCompanyRepository extends JpaRepository implements StateCorrelationHisCompanyRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateCorHisCom f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisComPk.cid =:cid ORDER BY f.endYearMonth DESC";
    private static final String SELECT_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisComPk.cid =:cid AND f.stateCorHisComPk.hisId =:hisId";
    private static final String SELECT_BY_DATE = SELECT_ALL_QUERY_STRING + " WHERE  f.stateCorHisComPk.cid =:cid AND f.startYearMonth <=:basedate AND f.endYearMonth >=:basedate";
    private static final String UPDATE_BY_HISID = "UPDATE QpbmtStateCorHisCom f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.stateCorHisComPk.cid =:cid AND f.stateCorHisComPk.hisId =:hisId";

    @Override
    public Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyById(String cid) {
        List<QpbmtStateCorHisCom> listStateCorHisCom = this.queryProxy().query(SELECT_BY_CID, QpbmtStateCorHisCom.class)
                .setParameter("cid", cid)
                .getList();
        return this.toDomain(listStateCorHisCom);
    }

    @Override
    public Optional<StateLinkSettingCompany> getStateLinkSettingCompanyById(String cid,String hisId){
        return this.queryProxy().query(SELECT_BY_HISID, QpbmtStateCorHisCom.class)
                .setParameter("cid",cid)
                .setParameter("hisId", hisId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyByDate(String cid,GeneralDate baseDate) {
        List<QpbmtStateCorHisCom> listStateCorHisCom = this.queryProxy().query(SELECT_BY_DATE, QpbmtStateCorHisCom.class)
                .setParameter("cid", cid)
                .setParameter("basedate", baseDate.yearMonth().toString())
                .getList();
        return this.toDomain(listStateCorHisCom);
    }

    @Override
    public void add(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode){
        this.commandProxy().insert(QpbmtStateCorHisCom.toEntity(cid,history,salaryCode,bonusCode));
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history){
        this.getEntityManager().createQuery(UPDATE_BY_HISID)
                .setParameter("startYearMonth",history.start().v())
                .setParameter("endYearMonth",history.end().v())
                .setParameter("cid",cid)
                .setParameter("hisId",history.identifier())
                .executeUpdate();
    }

    @Override
    public void update(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode){
        this.commandProxy().update(QpbmtStateCorHisCom.toEntity(cid, history,salaryCode,bonusCode));
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
