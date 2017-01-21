package nts.uk.ctx.pr.core.app.command.retirement.payitem;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.retirement.payitem.IndicatorCategory;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItem;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemCode;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemEnglishName;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemFullName;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemName;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemPrintName;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class RemoveRetirementPayItemCommandHandler extends CommandHandler<RemoveRetirementPayItemCommand>{
	@Inject
	private RetirementPayItemRepository retirementPayItemRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveRetirementPayItemCommand> context) {
		// TODO Auto-generated method stub
		RemoveRetirementPayItemCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		RetirementPayItem retirementPayItem = new RetirementPayItem(
				new CompanyCode(companyCode), 
				EnumAdaptor.valueOf(command.category, IndicatorCategory.class), 
				new RetirementPayItemCode(command.itemCode), 
				new RetirementPayItemName(command.itemName), 
				new RetirementPayItemPrintName(command.printName), 
				new RetirementPayItemEnglishName(command.englishName), 
				new RetirementPayItemFullName(command.fullName), 
				new Memo(command.memo));
		this.retirementPayItemRepository.remove(retirementPayItem);
	}
}
