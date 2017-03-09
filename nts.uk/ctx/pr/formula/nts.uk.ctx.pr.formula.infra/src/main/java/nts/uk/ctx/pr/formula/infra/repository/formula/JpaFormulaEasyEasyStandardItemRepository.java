package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyStandardItemRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyDetail;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyStandardItemPK;

@Stateless
public class JpaFormulaEasyEasyStandardItemRepository extends JpaRepository
		implements FormulaEasyStandardItemRepository {

	private final String REMOVE_FORMULA_EASY_STANRDARD_ITEM = "DELETE FROM QcfmtFormulaEasyStandardItem a "
			+ " WHERE a.qcfmtFormulaEasyStandardItemPK.companyCode = :companyCode "
			+ " AND a.qcfmtFormulaEasyStandardItemPK.formulaCode = :formulaCode "
			+ " AND a.qcfmtFormulaEasyStandardItemPK.historyId = :historyId ";

	private final String FIND_FORMULA_EASY_STANDARD = "SELECT a FROM QcfmtFormulaEasyStandardItem a "
			+ " WHERE a.qcfmtFormulaEasyStandardItemPK.companyCode = :companyCode "
			+ " AND a.qcfmtFormulaEasyStandardItemPK.formulaCode = :formulaCode "
			+ " AND a.qcfmtFormulaEasyStandardItemPK.historyId = :historyId "
			+ " AND a.qcfmtFormulaEasyStandardItemPK.easyFormulaCode = :easyFormulaCode ";

	@Override
	public List<FormulaEasyStandardItem> findAll(String companyCode, FormulaCode formulaCode, String historyId,
			EasyFormulaCode easyFormulaCode) {
		return this.queryProxy().query(FIND_FORMULA_EASY_STANDARD, QcfmtFormulaEasyStandardItem.class)
				.setParameter("companyCode", companyCode)
				.setParameter("formulaCode", formulaCode.v())
				.setParameter("historyId", historyId)
				.setParameter("easyFormulaCode", easyFormulaCode.v())
				.getList(f -> toDomain(f));
	}

	@Override
	public void remove(String companyCode, FormulaCode formulaCode, String historyId) {
		this.getEntityManager().createQuery(REMOVE_FORMULA_EASY_STANRDARD_ITEM)
				.setParameter("companyCode", companyCode)
				.setParameter("formulaCode", formulaCode.v())
				.setParameter("historyId", historyId).executeUpdate();
	}

	@Override
	public void add(List<FormulaEasyStandardItem> formulaEasyStandardItem) {
		formulaEasyStandardItem.stream().forEach(item -> this.commandProxy().insert(toEntity(item)));		;
	}

	private QcfmtFormulaEasyStandardItem toEntity(FormulaEasyStandardItem command) {
		val entity = new QcfmtFormulaEasyStandardItem();

		entity.qcfmtFormulaEasyAPK = new QcfmtFormulaEasyStandardItemPK();
		entity.qcfmtFormulaEasyAPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaEasyAPK.easyFormulaCode = command.getEasyFormulaCode().v();
		entity.qcfmtFormulaEasyAPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaEasyAPK.historyId = command.getHistoryId();
		entity.qcfmtFormulaEasyAPK.referenceItemCode = command.getReferenceItemCode().v();

		return entity;
	}

	private FormulaEasyStandardItem toDomain(QcfmtFormulaEasyStandardItem f) {
		FormulaEasyStandardItem formulaEasyStandardItem = FormulaEasyStandardItem.createFromJavaType(
				f.qcfmtFormulaEasyAPK.companyCode,
				f.qcfmtFormulaEasyAPK.formulaCode,
				f.qcfmtFormulaEasyAPK.historyId,
				f.qcfmtFormulaEasyAPK.easyFormulaCode,
				f.qcfmtFormulaEasyAPK.referenceItemCode);
		
		return formulaEasyStandardItem;
	}
}
