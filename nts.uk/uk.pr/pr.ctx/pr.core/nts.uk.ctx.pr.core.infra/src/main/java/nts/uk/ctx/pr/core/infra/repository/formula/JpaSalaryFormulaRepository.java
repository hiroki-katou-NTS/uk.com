package nts.uk.ctx.pr.core.infra.repository.formula;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormula;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormulaHistory;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormulaHistoryPk;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormulaPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class JpaSalaryFormulaRepository extends JpaRepository implements FormulaRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtFormula f ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.formulaPk.cid =:cid AND f.formulaPk.formulaCode =:formulaCode";
    private static final String SELECT_BY_COMPANY = SELECT_ALL_QUERY_STRING + "WHERE f.formulaPk.cid =:cid ORDER BY f.formulaPk.formulaCode";
    private static final String SELECT_FORMULA_HISTORY_BY_CODE = "SELECT f FROM QpbmtFormulaHistory f WHERE f.formulaHistoryPk.cid =:cid AND f.formulaHistoryPk.formulaCode =:formulaCode ORDER BY f.startMonth DESC";

    private static final String DELETE_FORMULA_HISTORY_BY_ID = "DELETE FROM QpbmtFormulaHistory f WHERE f.formulaHistoryPk.historyID =:historyID";
    private static final String DELETE_FORMULA_HISTORY_BY_CODE = "DELETE FROM QpbmtFormulaHistory f WHERE f.formulaHistoryPk.cid =:cid AND f.formulaHistoryPk.formulaCode =:formulaCode";
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
    public void removeFormulaHistory(String historyID) {
        this.getEntityManager().createQuery(DELETE_FORMULA_HISTORY_BY_ID).setParameter("historyID", historyID).executeUpdate();
    }

    @Override
    public void removeByFormulaCode(String formulaCode) {
        this.commandProxy().remove(new QpbmtFormula(new QpbmtFormulaPk(AppContexts.user().companyId(), formulaCode)));
        this.getEntityManager().createQuery(DELETE_FORMULA_HISTORY_BY_CODE).setParameter("cid", AppContexts.user().companyId()).setParameter("formulaCode", formulaCode).executeUpdate();
    }
}
