package nts.uk.ctx.pr.core.app.command.itemmaster.itemattend;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.Memo;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.WorkDaysScopeAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class AddItemAttendCommandHandler extends CommandHandler<AddItemAttendCommand> {
	@Inject
	ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<AddItemAttendCommand> context) {
		ItemAttend itemAttend = new ItemAttend(
				new CompanyCode(AppContexts.user().companyCode()),
				new ItemCode(context.getCommand().getItemCd()),
				EnumAdaptor.valueOf(context.getCommand().getAvePayAtr(), AvePayAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getItemAtr(), ItemAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeLowAtr(), RangeAtr.class),
				new ErrRangeLow(context.getCommand().getErrRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeHighAtr(), RangeAtr.class),
				new ErrRangeHigh(context.getCommand().getErrRangeHigh()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeLowAtr(), RangeAtr.class),
				new AlRangeLow(context.getCommand().getAlRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeHighAtr(), RangeAtr.class),
				new AlRangeHigh(context.getCommand().getAlRangeHigh()),
				EnumAdaptor.valueOf(context.getCommand().getWorkDaysScopeAtr(), WorkDaysScopeAtr.class),
				new Memo(context.getCommand().getMemo()));
		this.itemAttendRespository.add(itemAttend);

	}

}
