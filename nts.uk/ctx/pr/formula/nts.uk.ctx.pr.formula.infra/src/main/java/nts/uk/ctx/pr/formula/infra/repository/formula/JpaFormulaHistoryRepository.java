package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaHistory;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaHistoryPK;

@Stateless
public class JpaFormulaHistoryRepository extends JpaRepository implements FormulaHistoryRepository {

	private static final String FIND_ALL_BY_COMPANYCODE;

	private static final String FIND_ALL_DIFFERENT_FORMULACODE;

	private static final String IS_EXISTED_HISTORY;

	private static final String LAST_HISTORY;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM QcfmtFormulaHistory a ");
		builderString.append("WHERE a.qcfmtFormulaHistoryPK.companyCode = :companyCode ");
		IS_EXISTED_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM QcfmtFormulaHistory a ");
		builderString.append("WHERE a.qcfmtFormulaHistoryPK.companyCode = :companyCode ");
		builderString.append(
				"ORDER BY a.qcfmtFormulaHistoryPK.companyCode, a.qcfmtFormulaHistoryPK.formulaCode, a.qcfmtFormulaHistoryPK.historyId ");
		FIND_ALL_BY_COMPANYCODE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM QcfmtFormulaHistory a ");
		builderString.append("WHERE a.qcfmtFormulaHistoryPK.companyCode = :companyCode ");
		builderString.append("AND a.startDate <= :baseDate ");
		builderString.append("AND a.endDate >= :baseDate ");
		builderString.append("AND a.qcfmtFormulaHistoryPK.formulaCode <> :formulaCode ");
		FIND_ALL_DIFFERENT_FORMULACODE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM QcfmtFormulaHistory a ");
		builderString.append("WHERE a.qcfmtFormulaHistoryPK.companyCode = :companyCode ");
		builderString.append("AND a.qcfmtFormulaHistoryPK.formulaCode = :formulaCode ");
		LAST_HISTORY = builderString.toString();
	}

	@Override
	public List<FormulaHistory> findAll(String companyCode) {

		return this.queryProxy().query(FIND_ALL_BY_COMPANYCODE, QcfmtFormulaHistory.class)
				.setParameter("companyCode", companyCode).getList(f -> toDomain(f));
	}

	@Override
	public Optional<FormulaHistory> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId) {
		return this.queryProxy()
				.find(new QcfmtFormulaHistoryPK(companyCode, formulaCode.v(), historyId), QcfmtFormulaHistory.class)
				.map(f -> toDomain(f));
	}

	@Override
	public List<FormulaHistory> findDataDifFormulaCode(String companyCode, FormulaCode formulaCode,
			YearMonth baseDate) {
		return this.queryProxy().query(FIND_ALL_DIFFERENT_FORMULACODE, QcfmtFormulaHistory.class)
				.setParameter("companyCode", companyCode).setParameter("formulaCode", formulaCode.v())
				.setParameter("baseDate", baseDate.v()).getList(f -> toDomain(f));
	}

	@Override
	public void add(FormulaHistory formulaHistory) {
		this.commandProxy().insert(toEntity(formulaHistory));
	}

	@Override
	public void remove(FormulaHistory formulaHistory) {
		this.commandProxy().remove(toEntity(formulaHistory));
	}

	@Override
	public void update(FormulaHistory formulaHistory) {
		this.commandProxy().update(toEntity(formulaHistory));
	}

	private static FormulaHistory toDomain(QcfmtFormulaHistory qcfmtFormulaHistory) {

		FormulaHistory formulaHistory = FormulaHistory.createFromJavaType(
				qcfmtFormulaHistory.qcfmtFormulaHistoryPK.companyCode,
				qcfmtFormulaHistory.qcfmtFormulaHistoryPK.formulaCode,
				qcfmtFormulaHistory.qcfmtFormulaHistoryPK.historyId, qcfmtFormulaHistory.startDate.intValue(),
				qcfmtFormulaHistory.endDate.intValue());

		return formulaHistory;
	}

	private QcfmtFormulaHistory toEntity(FormulaHistory formulaHistory) {
		val entity = new QcfmtFormulaHistory();

		entity.qcfmtFormulaHistoryPK = new QcfmtFormulaHistoryPK();
		entity.qcfmtFormulaHistoryPK.companyCode = formulaHistory.getCompanyCode();
		entity.qcfmtFormulaHistoryPK.formulaCode = formulaHistory.getFormulaCode().v();
		entity.qcfmtFormulaHistoryPK.historyId = formulaHistory.getHistoryId();
		entity.startDate = new BigDecimal(formulaHistory.getStartDate().v());
		entity.endDate = new BigDecimal(formulaHistory.getEndDate().v());

		return entity;
	}

	@Override
	public boolean isExistedHistory(String companyCode) {
		return this.queryProxy().query(IS_EXISTED_HISTORY, long.class).setParameter("companyCode", companyCode)
				.getSingle().get() > 0;
	}

	@Override
	public Optional<FormulaHistory> findLastHistory(String companyCode, FormulaCode formulaCode, String historyId) {
		return null;
	}
}
