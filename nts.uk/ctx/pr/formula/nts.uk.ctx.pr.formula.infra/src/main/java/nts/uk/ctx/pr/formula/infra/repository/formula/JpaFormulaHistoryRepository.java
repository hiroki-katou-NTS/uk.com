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

	private final String FIND_ALL_BY_COMPANYCODE = "SELECT a FROM QcfmtFormulaHistory a "
			+ " WHERE a.qcfmtFormulaHistoryPK.companyCode = :companyCode ";
	
	private final String FIND_ALL_DIFFERENT_FORMULACODE = "SELECT a FROM QcfmtFormulaHistory a "
			+ " WHERE a.qcfmtFormulaHistoryPK.companyCode = :companyCode "
			+ " AND a.startDate <= :baseDate "
			+ " AND a.endDate >= :baseDate "
			+ " AND a.qcfmtFormulaHistoryPK.formulaCode <> :formulaCode ";

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
	public List<FormulaHistory> findDataDifFormulaCode(String companyCode, FormulaCode formulaCode, YearMonth baseDate) {
		return this.queryProxy().query(FIND_ALL_DIFFERENT_FORMULACODE, QcfmtFormulaHistory.class)
				.setParameter("companyCode", companyCode)
				.setParameter("formulaCode",formulaCode.v())
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


}
