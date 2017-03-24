package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemName;
import nts.uk.ctx.pr.core.dom.itemmaster.UniteCode;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;

@Stateless
@Transactional
public class AddItemDeductBDCommandHandler extends CommandHandler<AddItemDeductBDCommand> {

	@Inject
	ItemDeductBDRepository itemDeductBDRepo;

	@Override
	protected void handle(CommandHandlerContext<AddItemDeductBDCommand> context) {
		
		ItemDeductBD itemDeductBD = new ItemDeductBD(
				new ItemCode(context.getCommand().getItemCd()), new ItemCode(context.getCommand().getItemBreakdownCd()),
				new ItemName(context.getCommand().getItemBreakdownName()),
				new ItemName(context.getCommand().getItemBreakdownAbName()),
				new UniteCode(context.getCommand().getUniteCd()),
				EnumAdaptor.valueOf(context.getCommand().getZeroDispSet(), DisplayAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getItemDispAtr(), DisplayAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeLowAtr(), RangeAtr.class),
				new ErrRangeLow(context.getCommand().getErrRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeHighAtr(), RangeAtr.class),
				new ErrRangeHigh(context.getCommand().getErrRangeHigh()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeLowAtr(), RangeAtr.class),
				new AlRangeLow(context.getCommand().getAlRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeHighAtr(), RangeAtr.class),
				new AlRangeHigh(context.getCommand().getAlRangeHigh()));
		this.itemDeductBDRepo.add(itemDeductBD);
	}
}
