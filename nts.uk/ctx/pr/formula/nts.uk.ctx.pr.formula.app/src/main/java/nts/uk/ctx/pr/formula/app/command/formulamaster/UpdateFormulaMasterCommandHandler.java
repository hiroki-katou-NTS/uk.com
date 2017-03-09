package nts.uk.ctx.pr.formula.app.command.formulamaster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.enums.AdjustmentAtr;
import nts.uk.ctx.pr.formula.dom.enums.BaseMoneyAtr;
import nts.uk.ctx.pr.formula.dom.enums.ConditionAtr;
import nts.uk.ctx.pr.formula.dom.enums.DifficultyAtr;
import nts.uk.ctx.pr.formula.dom.enums.DivideValueSet;
import nts.uk.ctx.pr.formula.dom.enums.EasyFormulaTypeAtr;
import nts.uk.ctx.pr.formula.dom.enums.FixFormulaAtr;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.enums.RoundAtr;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;
import nts.uk.ctx.pr.formula.dom.primitive.DivideValue;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.MaxValue;
import nts.uk.ctx.pr.formula.dom.primitive.MinValue;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.PremiumRate;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;
import nts.uk.ctx.pr.formula.dom.primitive.WorkItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.WorkValue;
import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterDomainService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */
@Stateless
public class UpdateFormulaMasterCommandHandler extends CommandHandler<UpdateFormulaMasterCommand> {

	@Inject
	private FormulaMasterDomainService formulaMasterDomainService;

	@Override
	protected void handle(CommandHandlerContext<UpdateFormulaMasterCommand> context) {
		UpdateFormulaMasterCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		FormulaMaster formulaMaster = new FormulaMaster(companyCode, new FormulaCode(command.getFormulaCode()),
				EnumAdaptor.valueOf(command.getDifficultyAtr().intValue(), DifficultyAtr.class),
				new FormulaName(command.getFormulaName()));

		FormulaHistory formulaHistory = new FormulaHistory(companyCode, new FormulaCode(command.getFormulaCode()),
				command.getHistoryId(), new YearMonth(command.getStartDate()), new YearMonth(command.getEndDate()));

		FormulaEasyHeader formulaEasyHeader = new FormulaEasyHeader(companyCode,
				new FormulaCode(command.getFormulaCode()), command.getHistoryId(),
				EnumAdaptor.valueOf(command.getConditionAtr(), ConditionAtr.class),
				EnumAdaptor.valueOf(command.getRefMasterNo(), ReferenceMasterNo.class));

		List<FormulaEasyCondition> formulaEasyCondition = command.getEasyFormulaCode().stream().map(item -> {
			return new FormulaEasyCondition(companyCode, new FormulaCode(command.getFormulaCode()),
					command.getHistoryId(), new EasyFormulaCode(item.getEasyFormulaCode()),
					EnumAdaptor.valueOf(command.getFixFormulaAtr(), FixFormulaAtr.class), new Money(item.getValue()),
					new ReferenceMasterCode(Integer.toString(command.getRefMasterNo())));
		}).collect(Collectors.toList());

		FormulaEasyDetail formulaEasyDetail = new FormulaEasyDetail(companyCode,
				new FormulaCode(command.getFormulaCode()), command.getHistoryId(),
				new EasyFormulaCode(command.getFormulaCode()), new EasyFormulaName(command.getFormulaName()),
				EnumAdaptor.valueOf(command.getEasyFormulaTypeAtr(), EasyFormulaTypeAtr.class),
				new Money(command.getBaseFixedAmount()),
				EnumAdaptor.valueOf(command.getBaseAmountDevision(), BaseMoneyAtr.class),
				new DivideValue(command.getBaseFixedValue()),
				EnumAdaptor.valueOf(command.getBaseValueDevision(), DivideValueSet.class), new PremiumRate(command.getPremiumRate()),
				EnumAdaptor.valueOf(command.getRoundProcessingDevision(), RoundAtr.class),
				new WorkItemCode(command.getCoefficientDivision()),
				new WorkValue(command.getCoefficientFixedValue()),
				EnumAdaptor.valueOf(command.getAdjustmentDevision(), AdjustmentAtr.class),
				EnumAdaptor.valueOf(command.getTotalRounding(), RoundAtr.class),
				new MaxValue(command.getMaxValue()),
				new MinValue(command.getMinValue()));
		List<FormulaEasyStandardItem> formulaEasyStandardItem = new ArrayList<>();
	    command.getEasyFormulaCode().forEach(item -> {
	    	formulaEasyStandardItem.addAll(item.getReferenceItemCode().stream().map(item2 -> {
				return new FormulaEasyStandardItem(companyCode, new FormulaCode(command.getFormulaCode()), command.getHistoryId()
						, new EasyFormulaCode(item.getEasyFormulaCode()), new ReferenceItemCode(item2.getReferenceItemCode()));
			}).collect(Collectors.toList()));
		});

		formulaMasterDomainService.update(formulaMaster, formulaHistory, formulaEasyHeader, formulaEasyCondition, formulaEasyDetail, formulaEasyStandardItem);
	}

}
