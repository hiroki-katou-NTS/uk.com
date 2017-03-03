package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyCondi;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyCondiPK;

@Stateless
public class JpaFormulaEasyConditionRepository extends JpaRepository implements FormulaEasyConditionRepository {

	private final String REMOVE_EASY_CONDITION = "DELETE From QcfmtFormulaEasyCondi a "
			+ "where a.QcfmtFormulaEasyCondiPK.companyCode = :companyCode AND"
			+ "where a.QcfmtFormulaEasyCondiPK.formulaCode = :formulaCode AND"
			+ "where a.QcfmtFormulaEasyCondiPK.historyId = :historyId";


	@Override
	public void remove(FormulaEasyCondition formulaEasyCondition) {

		this.getEntityManager().createQuery(REMOVE_EASY_CONDITION)
				.setParameter("companyCode", formulaEasyCondition.getCompanyCode())
				.setParameter("formulaCode", formulaEasyCondition.getFormulaCode().v())
				.setParameter("historyId", formulaEasyCondition.getHistoryId()).executeUpdate();

	}

	@Override
	public void add(FormulaEasyCondition formulaEasyCondition) {
		this.commandProxy().insert(toEntity(formulaEasyCondition));
	}

	@Override
	public Optional<FormulaEasyCondition> find(String companyCode, FormulaCode formulaCode, String historyId,
			ReferenceMasterCode referenceMasterCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private QcfmtFormulaEasyCondi toEntity(FormulaEasyCondition command) {
		val entity = new QcfmtFormulaEasyCondi();

		entity.qcfmtFormulaEasyCondiPK = new QcfmtFormulaEasyCondiPK();
		entity.qcfmtFormulaEasyCondiPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaEasyCondiPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaEasyCondiPK.historyId = command.getHistoryId();
		entity.qcfmtFormulaEasyCondiPK.refMasterCd = command.getReferenceMasterCode().v();
		entity.easyFormulaCd = command.getEasyFormulaCode().v();
		entity.fixFormulaAtr = new BigDecimal(command.getFixFormulaAtr().value);
		entity.fixMny = command.getFixMoney().v();

		return entity;
	}

}
