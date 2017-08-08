package command.person.info.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class AddPerInfoCtgCommandHandler extends CommandHandler<AddPerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Override
	protected void handle(CommandHandlerContext<AddPerInfoCtgCommand> context) {
		AddPerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String categoryCode = null;
		List<String> companyIdList =  new ArrayList<>();
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaType(AppContexts.user().companyId(),
				categoryCode, perInfoCtgCommand.getCategoryName().v(), perInfoCtgCommand.getCategoryType().value);
		this.perInfoCtgRep.addPerInfoCtgRoot(perInfoCtg, AppContexts.user().companyId());
	}

}
