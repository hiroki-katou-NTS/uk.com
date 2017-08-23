package command.person.info.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Stateless
public class UpdatePerInfoCtgCommandHandler extends CommandHandler<UpdatePerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCommand> context) {
		UpdatePerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		if (!this.perInfoCtgRep.checkCtgNameIsUnique(PersonInfoCategory.ROOT_COMPANY_ID,
				perInfoCtgCommand.getCategoryName())) {
			throw new BusinessException("Msg_215");
		}
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaTypeUpdate(perInfoCtgCommand.getId(),
				PersonInfoCategory.ROOT_COMPANY_ID, perInfoCtgCommand.getCategoryType());
		this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, PersonInfoItemDefinition.ROOT_CONTRACT_CODE);
		
	}

}
