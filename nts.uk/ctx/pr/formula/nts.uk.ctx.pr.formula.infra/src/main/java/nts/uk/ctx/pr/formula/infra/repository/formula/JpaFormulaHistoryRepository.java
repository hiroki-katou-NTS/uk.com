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
	
	private static final String FIND_BY_FORMULACODE;

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
		builderString.append("ORDER BY a.qcfmtFormulaHistoryPK.companyCode, a.qcfmtFormulaHistoryPK.formulaCode, a.qcfmtFormulaHistoryPK.historyId ");
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
		FIND_BY_FORMULACODE = builderString.toString();
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

	private static FormulaHistory toDomain(QcfmtFormulaHistory hist) {

		FormulaHistory formulaHistory = FormulaHistory.createFromJavaType(hist.qcfmtFormulaHistoryPK.companyCode,
				hist.qcfmtFormulaHistoryPK.formulaCode, hist.qcfmtFormulaHistoryPK.historyId, hist.startDate.intValue(),
				hist.endDate.intValue());

		return formulaHistory;
	}

	private QcfmtFormulaHistory toEntity(FormulaHistory command) {
		val entity = new QcfmtFormulaHistory();

		entity.qcfmtFormulaHistoryPK = new QcfmtFormulaHistoryPK();
		entity.qcfmtFormulaHistoryPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaHistoryPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaHistoryPK.historyId = command.getHistoryId();
		entity.startDate = new BigDecimal(command.getStartDate().v());
		entity.endDate = new BigDecimal(command.getEndDate().v());

		return entity;
	}

	@Override
	public boolean isExistedHistory(String companyCode) {
		return this.queryProxy().query(IS_EXISTED_HISTORY, long.class).setParameter("companyCode", companyCode)
				.getSingle().get() > 0;
	}

	@Override
	public List<FormulaHistory> findByFormulaCode(String companyCode, FormulaCode formulaCode) {
		return this.queryProxy().query(FIND_BY_FORMULACODE, QcfmtFormulaHistory.class)
				.setParameter("companyCode", companyCode)
				.setParameter("formulaCode", formulaCode.v())
				.getList(f -> toDomain(f));
	}

}
