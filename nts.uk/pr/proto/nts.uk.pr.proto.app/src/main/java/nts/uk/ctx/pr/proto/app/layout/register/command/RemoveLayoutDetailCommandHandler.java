package nts.uk.ctx.pr.proto.app.layout.register.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;

@RequestScoped
@Transactional
public class RemoveLayoutDetailCommandHandler extends CommandHandler<RemoveLayoutDetailCommand> {
	
	@Inject
	private LayoutMasterDetailRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveLayoutDetailCommand> context) {
		val companyCode = new CompanyCode(context.getCommand().getCompanyCode());
		val layoutCode = new LayoutCode(context.getCommand().getLayoutCode());
		val startYm = new YearMonth(context.getCommand().getStartYm());
		int categoryAtr = context.getCommand().getCategoryAtr();
		val itemCode = new ItemCode(context.getCommand().getItemCode());
		//this.repository.remove(companyCode, layoutCode, startYm, categoryAtr, itemCode);
	}

}
