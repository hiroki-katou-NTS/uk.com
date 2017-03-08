package nts.uk.ctx.pr.formula.app.command.formulaeasydetail;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.enums.AdjustmentAtr;
import nts.uk.ctx.pr.formula.dom.enums.BaseMoneyAtr;
import nts.uk.ctx.pr.formula.dom.enums.DivideValueSet;
import nts.uk.ctx.pr.formula.dom.enums.EasyFormulaTypeAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundAtr;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.primitive.DivideValue;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.MaxValue;
import nts.uk.ctx.pr.formula.dom.primitive.MinValue;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.PremiumRate;
import nts.uk.ctx.pr.formula.dom.primitive.WorkItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.WorkValue;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */
@Stateless
public class AddFormulaEasyDetailCommandHandler extends CommandHandler<AddFormulaEasyDetailCommand>{

	@Inject
	private FormulaEasyDetailRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<AddFormulaEasyDetailCommand> context) {
		AddFormulaEasyDetailCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaEasyDetail formulaEasyDetail = new FormulaEasyDetail(
				companyCode, 
				new FormulaCode(command.getFormulaCode()), 
				command.getHistoryId(), 
				new EasyFormulaCode(command.getEasyFormulaCode()), 
				new EasyFormulaName(command.getEasyFormulaName()), 
				EnumAdaptor.valueOf(command.getEasyFormulaTypeAtr().intValue(), EasyFormulaTypeAtr.class), 
				new Money(command.getBaseFixedAmount()), 
				EnumAdaptor.valueOf(command.getBaseAmountDevision().intValue(), BaseMoneyAtr.class), 
				new DivideValue(command.getBaseFixedValue()), 
				EnumAdaptor.valueOf(command.getBaseValueDevision().intValue(), DivideValueSet.class), 
				new PremiumRate(command.getPremiumRate()), 
				EnumAdaptor.valueOf(command.getRoundProcessingDevision().intValue(), RoundAtr.class), 
				new WorkItemCode(command.getCoefficientDivision()), 
				new WorkValue(command.getCoefficientFixedValue()), 
				EnumAdaptor.valueOf(command.getAdjustmentDevision().intValue(), AdjustmentAtr.class), 
				EnumAdaptor.valueOf(command.getTotalRounding().intValue(), RoundAtr.class), 
				new MaxValue(command.getMaxLimitValue()), 
				new MinValue(command.getMinLimitValue()));
		
		repository.add(formulaEasyDetail);
	}

}
