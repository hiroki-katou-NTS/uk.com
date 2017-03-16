package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;

public interface FormulaMasterDomainService {
	void add(FormulaMaster formulaMaster, FormulaHistory formulaHistory, FormulaEasyHeader formulaEasyHeader);
	
	void update(FormulaMaster formulaMaster, FormulaHistory formulaHistory, FormulaEasyHeader formulaEasyHeader,
			List<FormulaEasyCondition> formulaEasyCondition, FormulaEasyDetail formulaEasyDetail,
			List<FormulaEasyStandardItem> formulaEasyStandardItem, FormulaManual formulaManual);
}
