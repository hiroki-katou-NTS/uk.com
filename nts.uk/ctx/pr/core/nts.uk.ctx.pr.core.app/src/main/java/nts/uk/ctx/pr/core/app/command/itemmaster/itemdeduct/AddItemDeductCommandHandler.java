package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.DeductAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeduct;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@RequestScoped
@Transactional
public class AddItemDeductCommandHandler extends CommandHandler<AddItemDeductCommand> {

	@Inject
	ItemDeductRespository itemDeductRespository;

	@Override
	protected void handle(CommandHandlerContext<AddItemDeductCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		ItemDeduct itemDeduct = new ItemDeduct(
				new CompanyCode(companyCode),
				new ItemCode(context.getCommand().getItemCd()),
				EnumAdaptor.valueOf(context.getCommand().getDeductAtr(), DeductAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeLowAtr(), RangeAtr.class),
				new ErrRangeLow(context.getCommand().getErrRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getErrRangeHighAtr(), RangeAtr.class),
				new ErrRangeHigh(context.getCommand().getErrRangeHigh()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeLowAtr(), RangeAtr.class),
				new AlRangeLow(context.getCommand().getAlRangeLow()),
				EnumAdaptor.valueOf(context.getCommand().getAlRangeHighAtr(), RangeAtr.class),
				new AlRangeHigh(context.getCommand().getAlRangeHigh()), new Memo(context.getCommand().getMemo()));
		if (this.itemDeductRespository.find(companyCode, context.getCommand().getItemCd()).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		this.itemDeductRespository.add(itemDeduct);

	}
}
