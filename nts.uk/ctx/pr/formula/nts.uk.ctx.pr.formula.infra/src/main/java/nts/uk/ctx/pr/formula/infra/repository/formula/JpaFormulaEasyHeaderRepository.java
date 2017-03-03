package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHead;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyHead;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyHeadPK;

@Stateless
public class JpaFormulaEasyHeaderRepository extends JpaRepository implements FormulaEasyHeaderRepository {

	@Override
	public Optional<FormulaEasyHead> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId,
			ReferenceMasterNo referenceMasterNo) {
		return this.queryProxy()
				.find(new QcfmtFormulaEasyHeadPK(companyCode, formulaCode.v(), historyId, new BigDecimal(referenceMasterNo.value)),
						QcfmtFormulaEasyHead.class)
				.map(f -> toDomain(f));
	}

	@Override
	public void add(FormulaEasyHead formulaEasyHead) {
		this.commandProxy().insert(toEntity(formulaEasyHead));
	}

	private static FormulaEasyHead toDomain(QcfmtFormulaEasyHead easyHead) {
		FormulaEasyHead formulaEasyHead = FormulaEasyHead.createFromJavaType(
				easyHead.qcfmtFormulaEasyHeadPK.companyCode, easyHead.qcfmtFormulaEasyHeadPK.formulaCode,
				easyHead.qcfmtFormulaEasyHeadPK.historyId, easyHead.conditionAtr,
				easyHead.qcfmtFormulaEasyHeadPK.refMasterNo);
		return formulaEasyHead;
	}

	private static QcfmtFormulaEasyHead toEntity(FormulaEasyHead command) {
		val entity = new QcfmtFormulaEasyHead();

		entity.qcfmtFormulaEasyHeadPK = new QcfmtFormulaEasyHeadPK();
		entity.qcfmtFormulaEasyHeadPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaEasyHeadPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaEasyHeadPK.historyId = command.getHistoryId();
		entity.qcfmtFormulaEasyHeadPK.refMasterNo = new BigDecimal(command.getReferenceMasterNo().value);
		entity.conditionAtr = new BigDecimal(command.getConditionAtr().value);

		return entity;
	}
}
