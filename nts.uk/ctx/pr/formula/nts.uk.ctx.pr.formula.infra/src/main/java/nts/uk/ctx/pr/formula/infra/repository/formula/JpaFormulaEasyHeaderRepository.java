package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHead;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyHead;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyHeadPK;

@RequestScoped
public class JpaFormulaEasyHeaderRepository extends JpaRepository implements FormulaEasyHeaderRepository {

	@Override
	public Optional<FormulaEasyHead> find(String companyCode, String formulaCode, String historyId,
			String referenceMasterNo) {
		return null;
	}

	@Override
	public void add(FormulaEasyHead formulaEasyHead) {
		this.commandProxy().insert(toEntity(formulaEasyHead));				
	}

	private QcfmtFormulaEasyHead toEntity(FormulaEasyHead command){
		val entity = new QcfmtFormulaEasyHead();
		
		entity.qcfmtFormulaEasyHeadPK = new QcfmtFormulaEasyHeadPK();
		entity.qcfmtFormulaEasyHeadPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaEasyHeadPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaEasyHeadPK.historyId = command.getHistoryId().v();
		entity.qcfmtFormulaEasyHeadPK.refMasterNo = new BigDecimal(command.getReferenceMasterNo().value);
		entity.conditionAtr = new BigDecimal(command.getConditionAtr().value);
		
		return entity;
	}
}
