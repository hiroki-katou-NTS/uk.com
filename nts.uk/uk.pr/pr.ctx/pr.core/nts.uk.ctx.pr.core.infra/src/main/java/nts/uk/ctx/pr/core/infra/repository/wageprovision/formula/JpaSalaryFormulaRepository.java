package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class JpaSalaryFormulaRepository extends JpaRepository implements FormulaRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtFormula f ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.formulaPk.cid =:cid AND f.formulaPk.formulaCode =:formulaCode";
    private static final String SELECT_BY_COMPANY = SELECT_ALL_QUERY_STRING + "WHERE f.formulaPk.cid =:cid ORDER BY f.formulaPk.formulaCode";
    private static final String SELECT_FORMULA_HISTORY_BY_CODE = "SELECT f FROM QpbmtFormulaHistory f WHERE f.formulaHistoryPk.cid =:cid AND f.formulaHistoryPk.formulaCode =:formulaCode ORDER BY f.startMonth DESC";
    private static final String SELECT_BY_FORMULA_CDS = SELECT_ALL_QUERY_STRING + " WHERE  f.formulaPk.cid =:cid AND" +
            " f.formulaPk.formulaCode IN :formulaCodes" +
            " ORDER BY f.formulaPk.formulaCode ASC";

    private static final String SELECT_ALL_HIS_QUERY_STRING = "SELECT f FROM QpbmtFormulaHistory f";
    private static final String SELECT_BY_YM = SELECT_ALL_HIS_QUERY_STRING + " WHERE  f.formulaHistoryPk.cid =:cid AND" +
            " f.startMonth <= :yearMonth AND f.endMonth >= :yearMonth ";
    private static final String SELECT_USABLE_DETAIL_SETTING_FORMULA = "SELECT f FROM QpbmtFormula f JOIN QpbmtFormulaHistory h ON f.formulaPk.cid = h.formulaHistoryPk.cid AND f.formulaPk.formulaCode = h.formulaHistoryPk.formulaCode WHERE f.formulaPk.cid =:cid f. AND f.nestedAtr = 1 AND h.endMonth = 999912 AND ORDER BY f.formulaPk.formulaCode ";

    private static final String DELETE_FORMULA_BY_CODE = "DELETE FROM QpbmtFormula f WHERE f.formulaPk.formulaCode =:formulaCode";
    private static final String DELETE_FORMULA_HISTORY_BY_ID = "DELETE FROM QpbmtFormulaHistory f WHERE f.formulaHistoryPk.historyID =:historyID";
    @Override
    public List<Formula> getAllFormula(){
        return this.queryProxy().query(SELECT_BY_COMPANY, QpbmtFormula.class).setParameter("cid", AppContexts.user().companyId())
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<Formula> getFormulaById(String formulaCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtFormula.class).setParameter("cid", AppContexts.user().companyId()).setParameter("formulaCode", formulaCode)
        .getSingle(c->c.toDomain());
    }

    public Optional<FormulaHistory> getFormulaHistoryByCode (String formulaCode) {
        return QpbmtFormulaHistory.toDomain(this.queryProxy().query(SELECT_FORMULA_HISTORY_BY_CODE, QpbmtFormulaHistory.class).setParameter("cid", AppContexts.user().companyId()).setParameter("formulaCode", formulaCode)
                .getList());
    }

    @Override
    public void add(Formula domain){
        this.commandProxy().insert(QpbmtFormula.toEntity(domain));
    }

    @Override
    public void update(Formula domain){
        this.commandProxy().update(QpbmtFormula.toEntity(domain));
    }

    @Override
    public void remove(Formula domain){
        this.commandProxy().remove(QpbmtFormula.toEntity(domain));
    }

    @Override
    public void insertFormulaHistory(String formulaCode, YearMonthHistoryItem yearMonth) {
        this.commandProxy().insert(new QpbmtFormulaHistory(new QpbmtFormulaHistoryPk(AppContexts.user().companyId(), formulaCode, yearMonth.identifier()), yearMonth.start().v(), yearMonth.end().v()));
    }

    @Override
    public void updateFormulaHistory(String formulaCode, YearMonthHistoryItem yearMonth) {
        this.commandProxy().update(new QpbmtFormulaHistory(new QpbmtFormulaHistoryPk(AppContexts.user().companyId(), formulaCode, yearMonth.identifier()), yearMonth.start().v(), yearMonth.end().v()));
    }

    @Override
    public int removeFormulaHistory(String historyID) {
        return this.getEntityManager().createQuery(DELETE_FORMULA_HISTORY_BY_ID).setParameter("historyID", historyID).executeUpdate();
    }

    @Override
    public void removeByFormulaCode(String formulaCode) {
        this.getEntityManager().createQuery(DELETE_FORMULA_BY_CODE).setParameter("formulaCode", formulaCode).executeUpdate();
    }

    @Override
    public List<Formula> getFormulaByCodes(String cid, List<String> formulaCodes) {
        if (formulaCodes == null || formulaCodes.isEmpty()) return Collections.emptyList();
        return this.queryProxy().query(SELECT_BY_FORMULA_CDS, QpbmtFormula.class)
                .setParameter("cid", cid)
                .setParameter("formulaCodes", formulaCodes)
                .getList(QpbmtFormula::toDomain);
    }

    @Override
    public Map<String, String> getFormulaWithUsableDetailSetting() {
        return this.queryProxy().query(SELECT_USABLE_DETAIL_SETTING_FORMULA, QpbmtFormula.class).setParameter("cid", AppContexts.user().companyId())
                .getList().stream().collect(Collectors.toMap(item -> item.formulaPk.formulaCode, item -> item.formulaName));
    }

    @Override
    public List<FormulaHistory> getFormulaHistByYearMonth(YearMonth yearMonth) {
        String cid = AppContexts.user().companyId();
        return QpbmtFormulaHistory.toDomainFromList(this.queryProxy().query(SELECT_BY_YM, QpbmtFormulaHistory.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList());
    }
}
