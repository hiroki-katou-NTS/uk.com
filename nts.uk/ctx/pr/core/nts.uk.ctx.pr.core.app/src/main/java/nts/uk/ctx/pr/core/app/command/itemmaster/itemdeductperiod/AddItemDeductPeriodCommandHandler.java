package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriod;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemperiod.UsageClassification;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.Year;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.PeriodAtr;

public class AddItemDeductPeriodCommandHandler extends CommandHandler<AddItemDeductPeriodCommand> {

	@Inject
	ItemDeductPeriodRepository itemDeductPeriodRepo;

	@Override
	protected void handle(CommandHandlerContext<AddItemDeductPeriodCommand> context) {
		this.itemDeductPeriodRepo.add(context.getCommand().toDomain());
	}

}
