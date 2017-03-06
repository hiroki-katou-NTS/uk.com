package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyStandardItemRepository;

@Stateless
public class JpaFormulaEasyEasyStandardItemRepository extends JpaRepository
		implements FormulaEasyStandardItemRepository {

	private final String REMOVE_FORMULA_EASY_STANRDARD_ITEM = "DELETE FROM QcfmtFormulaEasyStandardItem a "
			+ " WHERE a.QcfmtFormulaEasyStandardItemPK.companyCode = :companyCode AND "
			+ " AND a.QcfmtFormulaEasyStandardItemPK.formulaCode = :formulaCode AND "
			+ " AND a.QcfmtFormulaEasyStandardItemPK.historyId = :historyId ";

	@Override
	public List<ItemCode> findAll(String companyCode, FormulaCode formulaCode, String historyId,
			EasyFormulaCode easyFormulaCode) {
		return null;
	}

	@Override
	public void remove(FormulaEasyStandardItem formulaEasyStandardItem) {
		this.getEntityManager().createQuery(REMOVE_FORMULA_EASY_STANRDARD_ITEM)
				.setParameter("companyCode", formulaEasyStandardItem.getCompanyCode())
				.setParameter("formulaCode", formulaEasyStandardItem.getFormulaCode())
				.setParameter("historyId", formulaEasyStandardItem.getHistoryId()).executeUpdate();
	}

}
