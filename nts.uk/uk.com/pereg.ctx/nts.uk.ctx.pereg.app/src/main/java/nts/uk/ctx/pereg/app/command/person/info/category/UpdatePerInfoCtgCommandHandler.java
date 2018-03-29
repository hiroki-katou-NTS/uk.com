package nts.uk.ctx.pereg.app.command.person.info.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePerInfoCtgCommandHandler extends CommandHandler<UpdatePerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCommand> context) {

		UpdatePerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String categoryName = perInfoCtgCommand.getCategoryName();
		
		String contractCode = AppContexts.user().contractCode();
		String zeroCompanyId = AppContexts.user().zeroCompanyIdInContract();
		
		
		if (CheckNameSpace.checkName(categoryName)) {
			throw new BusinessException("Msg_928");
		}
		
		if (!this.perInfoCtgRep.checkCtgNameIsUnique(zeroCompanyId, categoryName, perInfoCtgCommand.getId())) {
			throw new BusinessException("Msg_215");
		}
		
		PersonInfoCategory perInfoCtg = this.perInfoCtgRep
				.getPerInfoCategory(perInfoCtgCommand.getId(), contractCode).orElse(null);
		
		if (perInfoCtg != null) {
			perInfoCtg.setCategoryName(categoryName);
			perInfoCtg.setCategoryType(perInfoCtgCommand.getCategoryType());
			
			this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, contractCode);
		}
		
	}
}
