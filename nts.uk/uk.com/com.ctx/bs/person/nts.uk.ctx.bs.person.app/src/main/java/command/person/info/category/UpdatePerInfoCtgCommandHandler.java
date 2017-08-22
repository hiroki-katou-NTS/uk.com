package command.person.info.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePerInfoCtgCommandHandler extends CommandHandler<UpdatePerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCommand> context) {
		UpdatePerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String categoryCode = null;
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaType(PersonInfoCategory.ROOT_COMPANY_ID,
				categoryCode, perInfoCtgCommand.getCategoryName(), perInfoCtgCommand.getCategoryType());
		this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, AppContexts.user().companyId());
	}

}
