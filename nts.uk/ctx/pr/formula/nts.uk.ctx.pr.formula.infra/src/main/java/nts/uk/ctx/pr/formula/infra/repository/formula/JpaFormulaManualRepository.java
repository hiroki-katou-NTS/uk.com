package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaManual;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaManualPK;

@Stateless
public class JpaFormulaManualRepository extends JpaRepository implements FormulaManualRepository {

	@Override
	public Optional<FormulaManual> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId) {
		return this.queryProxy()
				.find(new QcfmtFormulaManualPK(companyCode, formulaCode.v(), historyId), QcfmtFormulaManual.class)
				.map(f -> toDomain(f));
	}

	@Override
	public void add(FormulaManual formulaManual) {
		this.commandProxy().insert(toEntity(formulaManual));
	}

	@Override
	public void update(FormulaManual formulaManual) {
		this.commandProxy().update(toEntity(formulaManual));
	}

	@Override
	public void remove(FormulaManual formulaManual) {
		this.commandProxy().remove(toEntity(formulaManual));
	}

	private FormulaManual toDomain(QcfmtFormulaManual qcfmtFormulaManual) {
		FormulaManual formulaManual = FormulaManual.createFromJavaType(
				qcfmtFormulaManual.qcfmtFormulaManualPK.companyCode,
				qcfmtFormulaManual.qcfmtFormulaManualPK.formulaCode, qcfmtFormulaManual.qcfmtFormulaManualPK.historyId,
				qcfmtFormulaManual.formulaContent, qcfmtFormulaManual.referMonthAtr, qcfmtFormulaManual.roundAtr,
				qcfmtFormulaManual.roundDigit);

		return formulaManual;
	}

	private QcfmtFormulaManual toEntity(FormulaManual formulaManual) {

		val entity = new QcfmtFormulaManual();

		entity.qcfmtFormulaManualPK = new QcfmtFormulaManualPK();
		entity.qcfmtFormulaManualPK.companyCode = formulaManual.getCompanyCode();
		entity.qcfmtFormulaManualPK.formulaCode = formulaManual.getFormulaCode().v();
		entity.qcfmtFormulaManualPK.historyId = formulaManual.getHistoryId();
		entity.formulaContent = formulaManual.getFormulaContent().v();
		entity.referMonthAtr = new BigDecimal(formulaManual.getReferenceMonthAtr().value);
		entity.roundAtr = new BigDecimal(formulaManual.getRoundAtr().value);
		entity.roundDigit = new BigDecimal(formulaManual.getRoundDigit().value);

		return entity;
	}

}
