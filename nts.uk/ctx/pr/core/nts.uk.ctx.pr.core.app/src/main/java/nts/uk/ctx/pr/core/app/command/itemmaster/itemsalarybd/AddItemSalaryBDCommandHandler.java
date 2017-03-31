package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd;

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
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;

@Stateless
@Transactional
public class AddItemSalaryBDCommandHandler extends CommandHandler<AddItemSalaryBDCommand> {

	@Inject
	private ItemSalaryBDRepository ItemSalaryBDRepository;

	@Override
	protected void handle(CommandHandlerContext<AddItemSalaryBDCommand> context) {
		ItemSalaryBD itemSalaryBD = new ItemSalaryBD(new ItemCode(context.getCommand().getItemCd()),
				new ItemCode(context.getCommand().getItemBreakdownCd()),
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
		this.ItemSalaryBDRepository.add(itemSalaryBD);
	}
}
