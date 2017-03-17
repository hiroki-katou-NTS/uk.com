package nts.uk.ctx.pr.formula.app.command.formulahistory;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.enums.ConditionAtr;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.shr.com.context.AppContexts;;

/**
 * @author nampt
 *
 */
@Stateless
public class AddFormulaHistoryCommandHandler extends CommandHandler<AddFormulaHistoryCommand>{

	@Inject
	private FormulaHistoryRepository formulaHistoryRepository;
	
	@Inject
	private FormulaEasyHeaderRepository formulaEasyHeaderRepository;
	
	@Override
	protected void handle(CommandHandlerContext<AddFormulaHistoryCommand> context) {
		
		AddFormulaHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaHistory formulaHistoryAdd = new FormulaHistory(
				companyCode,
				new FormulaCode(command.getFormulaCode()),
				command.getHistoryId(),
				new YearMonth(command.getStartDate()),
				new YearMonth(command.getEndDate()));
		
		formulaHistoryRepository.add(formulaHistoryAdd);	
		
		if(command.getSettingFormula() == 0){
			FormulaEasyHeader formulaEasyHead = new FormulaEasyHeader(
					companyCode,
					new FormulaCode(command.getFormulaCode()),
					command.getHistoryId(),
					EnumAdaptor.valueOf(command.getConditionAtr(), ConditionAtr.class),
					EnumAdaptor.valueOf(command.getReferenceMasterNo(), ReferenceMasterNo.class));
			
			formulaEasyHeaderRepository.add(formulaEasyHead);
		} else if(command.getSettingFormula() == 1){
			FormulaHistory formulaHistoryUpdate = new FormulaHistory(
					companyCode,
					new FormulaCode(command.getFormulaCode()),
					command.getHistoryId(),
					new YearMonth(command.getStartDate()),
					new YearMonth(command.getEndDate()));
			formulaHistoryRepository.update(formulaHistoryUpdate);
		}
	}
	
}
