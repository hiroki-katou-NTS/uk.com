package nts.uk.ctx.pereg.app.command.person.info.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;

@Stateless
public class UpdatePerInfoCtgCommandHandler extends CommandHandler<UpdatePerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCommand> context) {

		UpdatePerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String ctgName = perInfoCtgCommand.getCategoryName();
		if (CheckNameSpace.checkName(ctgName)) {
			throw new BusinessException(new RawErrorMessage("Msg_928"));
		}
		if (!this.perInfoCtgRep.checkCtgNameIsUnique(PersonInfoCategory.ROOT_COMPANY_ID,
				perInfoCtgCommand.getCategoryName(), perInfoCtgCommand.getId())) {
			throw new BusinessException(new RawErrorMessage("Msg_215"));
		}
		PersonInfoCategory perInfoCtg = this.perInfoCtgRep
				.getPerInfoCategory(perInfoCtgCommand.getId(), PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.orElse(null);
		if (perInfoCtg == null) {
			return;
		}
		perInfoCtg.setCategoryName(perInfoCtgCommand.getCategoryName());
		perInfoCtg.setCategoryType(perInfoCtgCommand.getCategoryType());
		this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, PersonInfoItemDefinition.ROOT_CONTRACT_CODE);
	}
}
