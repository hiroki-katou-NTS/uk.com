package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyCondition;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyHeader;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyHeaderPK;

@Stateless
public class JpaFormulaEasyHeaderRepository extends JpaRepository implements FormulaEasyHeaderRepository {
	
	private static final String REMOVE_EASY_HEADER;
	
	private static final String FIND_EASY_HEADER;
	
	static{
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE FROM QcfmtFormulaEasyHeader a ");
		builderString.append("WHERE a.QcfmtFormulaEasyHeaderPK.companyCode = :companyCode ");
		builderString.append("AND a.QcfmtFormulaEasyHeaderPK.formulaCode = :formulaCode ");
		builderString.append("AND a.QcfmtFormulaEasyHeaderPK.historyId = :historyId ");
		REMOVE_EASY_HEADER = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a FROM QcfmtFormulaEasyHeader a ");
		builderString.append("WHERE a.QcfmtFormulaEasyHeaderPK.companyCode = :companyCode ");
		builderString.append("AND a.QcfmtFormulaEasyHeaderPK.formulaCode = :formulaCode ");
		builderString.append("AND a.QcfmtFormulaEasyHeaderPK.historyId = :historyId ");
		FIND_EASY_HEADER = builderString.toString();
	}

	@Override
	public Optional<FormulaEasyHeader> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId) {
//		return this.queryProxy().find(new QcfmtFormulaEasyHeaderPK(companyCode, formulaCode.v(), historyId,
//				new BigDecimal(referenceMasterNo.value)), QcfmtFormulaEasyHeader.class).map(f -> toDomain(f));
		return this.queryProxy().query(FIND_EASY_HEADER, QcfmtFormulaEasyHeader.class)
				.setParameter("companyCode", companyCode)
				.setParameter("formulaCode", formulaCode.v())
				.setParameter("historyId", historyId).getSingle(f -> (toDomain(f)));
	}

	@Override
	public void add(FormulaEasyHeader formulaEasyHead) {
		this.commandProxy().insert(toEntity(formulaEasyHead));
	}

	@Override
	public void remove(FormulaEasyHeader formulaEasyHead) {
		this.getEntityManager().createQuery(REMOVE_EASY_HEADER)
				.setParameter("companyCode", formulaEasyHead.getCompanyCode())
				.setParameter("formulaCode", formulaEasyHead.getFormulaCode().v())
				.setParameter("historyId", formulaEasyHead.getHistoryId()).executeUpdate();
	}

	private static FormulaEasyHeader toDomain(QcfmtFormulaEasyHeader easyHead) {
		FormulaEasyHeader formulaEasyHead = FormulaEasyHeader.createFromJavaType(
				easyHead.qcfmtFormulaEasyHeaderPK.companyCode, easyHead.qcfmtFormulaEasyHeaderPK.formulaCode,
				easyHead.qcfmtFormulaEasyHeaderPK.historyId, easyHead.conditionAtr,
				easyHead.qcfmtFormulaEasyHeaderPK.refMasterNo);
		return formulaEasyHead;
	}

	private static QcfmtFormulaEasyHeader toEntity(FormulaEasyHeader command) {
		val entity = new QcfmtFormulaEasyHeader();

		entity.qcfmtFormulaEasyHeaderPK = new QcfmtFormulaEasyHeaderPK();
		entity.qcfmtFormulaEasyHeaderPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaEasyHeaderPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaEasyHeaderPK.historyId = command.getHistoryId();
		entity.qcfmtFormulaEasyHeaderPK.refMasterNo = new BigDecimal(command.getReferenceMasterNo().value);
		entity.conditionAtr = new BigDecimal(command.getConditionAtr().value);

		return entity;
	}
}
