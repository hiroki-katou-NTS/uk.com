package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ApplyFor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.FixPayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.InsAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.LimitMny;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.LimitMnyAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.LimitMnyRefItemCd;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.TaxAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@RequestScoped
@Transactional
public class AddItemSalaryCommandHandler extends CommandHandler<AddItemSalaryCommand> {

	@Inject
	ItemSalaryRespository itemSalaryRespository;

	@Override
	protected void handle(CommandHandlerContext<AddItemSalaryCommand> context) {
		ItemSalary itemSalary = new ItemSalary(
				new CompanyCode(AppContexts.user().companyCode()),
				new ItemCode(context.getCommand().getItemCd()),
				EnumAdaptor.valueOf(context.getCommand().getTaxAtr(), TaxAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getSocialInsAtr(), InsAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getLaborInsAtr(), InsAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getFixPayAtr(), FixPayAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getApplyForAllEmpFlg(), ApplyFor.class),
				EnumAdaptor.valueOf(context.getCommand().getApplyForMonthlyPayEmp(), ApplyFor.class),
				EnumAdaptor.valueOf(context.getCommand().getApplyForDaymonthlyPayEmp(), ApplyFor.class),
				EnumAdaptor.valueOf(context.getCommand().getApplyForDaylyPayEmp(), ApplyFor.class),
				EnumAdaptor.valueOf(context.getCommand().getApplyForHourlyPayEmp(), ApplyFor.class),
				EnumAdaptor.valueOf(context.getCommand().getAvePayAtr(), ApplyFor.class),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeLowAtr(), RangeAtr.class),
				new ErrRangeLow(context.getCommand().getErrRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeHighAtr(), RangeAtr.class),
				new ErrRangeHigh(context.getCommand().getErrRangeHigh()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeLowAtr(), RangeAtr.class),
				new AlRangeLow(context.getCommand().getAlRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeHighAtr(), RangeAtr.class),
				new AlRangeHigh(context.getCommand().getAlRangeHigh()), new Memo(context.getCommand().getMemo()),
				EnumAdaptor.valueOf(context.getCommand().getLimitMnyAtr(), LimitMnyAtr.class),
				new LimitMnyRefItemCd(context.getCommand().getLimitMnyRefItemCd()),
				new LimitMny(context.getCommand().getLimitMny()));
		this.itemSalaryRespository.add(itemSalary);

	}

}
