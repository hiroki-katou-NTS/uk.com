package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyCondition;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyConditionPK;

@Stateless
public class JpaFormulaEasyConditionRepository extends JpaRepository implements FormulaEasyConditionRepository {

	private final String REMOVE_EASY_CONDITION = "DELETE FROM QcfmtFormulaEasyCondition a "
			+ " WHERE a.qcfmtFormulaEasyConditionPK.companyCode = :companyCode "
			+ " AND a.qcfmtFormulaEasyConditionPK.formulaCode = :formulaCode "
			+ " AND a.qcfmtFormulaEasyConditionPK.historyId = :historyId ";

	private final String FIND_FORMULA_EASY_CONDITION = "SELECT a FROM QcfmtFormulaEasyCondition a "
			+ " WHERE a.qcfmtFormulaEasyConditionPK.companyCode = :companyCode "
			+ " AND a.qcfmtFormulaEasyConditionPK.formulaCode = :formulaCode "
			+ " AND a.qcfmtFormulaEasyConditionPK.historyId = :historyId ";

	@Override
	public void remove(String companyCode, FormulaCode formulaCode, String historyId) {

		this.getEntityManager().createQuery(REMOVE_EASY_CONDITION).setParameter("companyCode", companyCode)
				.setParameter("formulaCode", formulaCode.v()).setParameter("historyId", historyId).executeUpdate();

	}

	@Override
	public void add(List<FormulaEasyCondition> formulaEasyCondition) {
		formulaEasyCondition.forEach(item -> this.commandProxy().insert(toEntity(item)));
	}

	@Override
	public List<FormulaEasyCondition> find(String companyCode, FormulaCode formulaCode, String historyId) {
		return this.queryProxy().query(FIND_FORMULA_EASY_CONDITION, QcfmtFormulaEasyCondition.class)
				.setParameter("companyCode", companyCode).setParameter("formulaCode", formulaCode.v())
				.setParameter("historyId", historyId).getList(f -> (toDomain(f)));
	}

	private FormulaEasyCondition toDomain(QcfmtFormulaEasyCondition f) {
		FormulaEasyCondition formulaEasyCondition = FormulaEasyCondition.createFromJavaType(
				f.qcfmtFormulaEasyConditionPK.companyCode, f.qcfmtFormulaEasyConditionPK.formulaCode,
				f.qcfmtFormulaEasyConditionPK.historyId, f.easyFormulaCd, f.fixFormulaAtr, f.fixMny,
				f.qcfmtFormulaEasyConditionPK.refMasterCd);

		return formulaEasyCondition;
	}

	private QcfmtFormulaEasyCondition toEntity(FormulaEasyCondition command) {
		val entity = new QcfmtFormulaEasyCondition();

		entity.qcfmtFormulaEasyConditionPK = new QcfmtFormulaEasyConditionPK();
		entity.qcfmtFormulaEasyConditionPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaEasyConditionPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaEasyConditionPK.historyId = command.getHistoryId();
		entity.qcfmtFormulaEasyConditionPK.refMasterCd = command.getReferenceMasterCode().v();
		entity.easyFormulaCd = command.getEasyFormulaCode().v();
		entity.fixFormulaAtr = new BigDecimal(command.getFixFormulaAtr().value);
		entity.fixMny = command.getFixMoney().v();

		return entity;
	}

}
