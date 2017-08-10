package command.layout;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;
import nts.uk.shr.com.context.AppContexts;

public class NewLayoutCommandHandler extends CommandHandler<NewLayoutCommand> {

	@Inject
	INewLayoutReposotory layoutRepo;
	
	@Inject
	ILayoutPersonInfoClsRepository classfRepo;

	@Override
	protected void handle(CommandHandlerContext<NewLayoutCommand> context) {
		// TODO Auto-generated method stub
		NewLayoutCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		NewLayout domain = NewLayout.createFromJavaType(companyId, command.getLayoutID(), command.getLayoutCode(),
				command.getLayoutName());
		
		layoutRepo.update(domain);
		
		for(ClassificationCommand classCommand: command.getListClass()) {
			//classfRepo.
		}
	}
}
