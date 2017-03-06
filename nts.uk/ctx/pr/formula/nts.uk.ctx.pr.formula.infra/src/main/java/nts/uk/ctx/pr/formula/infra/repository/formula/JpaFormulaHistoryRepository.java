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
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaHist;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaHistPK;

@Stateless
public class JpaFormulaHistoryRepository extends JpaRepository implements FormulaHistoryRepository {

	private final String FIND_ALL_BY_COMPANYCODE = "SELECT a FROM QcfmtFormulaHist a "
			+ " WHERE a.qcfmtFormulaHistPK.companyCode = :companyCode ";
	
	private final String FIND_ALL_DIFFERENT_FORMULACODE = "SELECT a FROM QcfmtFormulaHist a "
			+ " WHERE a.qcfmtFormulaHistPK.companyCode = :companyCode "
			+ " AND a.startDate <= :baseDate "
			+ " AND a.endDate >= :baseDate "
			+ " AND a.qcfmtFormulaHistPK.formulaCode <> :formulaCode ";

	@Override
	public List<FormulaHistory> findAll(String companyCode) {

		return this.queryProxy().query(FIND_ALL_BY_COMPANYCODE, QcfmtFormulaHist.class)
				.setParameter("companyCode", companyCode).getList(f -> toDomain(f));
	}

	@Override
	public Optional<FormulaHistory> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId) {
		return this.queryProxy()
				.find(new QcfmtFormulaHistPK(companyCode, formulaCode.v(), historyId), QcfmtFormulaHist.class)
				.map(f -> toDomain(f));
	}

	@Override
	public List<FormulaHistory> findDataDifFormulaCode(String companyCode, FormulaCode formulaCode, YearMonth baseDate) {
		return this.queryProxy().query(FIND_ALL_DIFFERENT_FORMULACODE, QcfmtFormulaHist.class)
				.setParameter("companyCode", companyCode)
				.setParameter("formulaCode",formulaCode.v())
				.setParameter("baseDate", baseDate.v()).getList(f -> toDomain(f));
	}

	@Override
	public void add(FormulaHistory formulaHistory) {
		this.commandProxy().insert(toEntity(formulaHistory));
	}

	private static FormulaHistory toDomain(QcfmtFormulaHist hist) {

		FormulaHistory formulaHistory = FormulaHistory.createFromJavaType(hist.qcfmtFormulaHistPK.companyCode,
				hist.qcfmtFormulaHistPK.formulaCode, hist.qcfmtFormulaHistPK.historyId, hist.startDate.intValue(),
				hist.endDate.intValue());

		return formulaHistory;
	}

	private QcfmtFormulaHist toEntity(FormulaHistory command) {
		val entity = new QcfmtFormulaHist();

		entity.qcfmtFormulaHistPK = new QcfmtFormulaHistPK();
		entity.qcfmtFormulaHistPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaHistPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaHistPK.historyId = command.getHistoryId();
		entity.startDate = new BigDecimal(command.getStartDate().v());
		entity.endDate = new BigDecimal(command.getEndDate().v());

		return entity;
	}

}
