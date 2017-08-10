package command.layout;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.LayoutPersonInfoClsDefinition;

@Stateless
@Transactional
public class NewLayoutCommandHandler extends CommandHandler<NewLayoutCommand> {

	@Inject
	INewLayoutReposotory layoutRepo;

	@Inject
	ILayoutPersonInfoClsRepository classfRepo;

	@Inject
	ILayoutPersonInfoClsDefRepository clsDefRepo;

	@Override
	protected void handle(CommandHandlerContext<NewLayoutCommand> context) {
		// TODO Auto-generated method stub
		NewLayoutCommand command = context.getCommand();

		NewLayout update = layoutRepo.getLayout().get();

		// layoutRepo.update(update);

		classfRepo.removeAllByLayoutId(update.getLayoutID());

		clsDefRepo.removeAllByLayoutId(update.getLayoutID());

		classfRepo.addClassifications(command.getListClass().stream()
				.map(m -> toClassificationDomain(m, update.getLayoutID())).collect(Collectors.toList()));

		List<ClassificationCommand> classCommands = command.getListClass();
		if (!classCommands.isEmpty()) {
			for (ClassificationCommand classCommand : classCommands) {
				List<ClassificationItemDfCommand> clsIDfs = classCommand.getListItemClsDf();
				if (!clsIDfs.isEmpty()) {
					clsDefRepo.addClassificationItemDefines(clsIDfs.stream()
							.map(m -> toClassItemDefDomain(m, update.getLayoutID(), classCommand.getDispOrder()))
							.collect(Collectors.toList()));
				}
			}
		}
	}

	private LayoutPersonInfoClassification toClassificationDomain(ClassificationCommand command, String layoutId) {
		return LayoutPersonInfoClassification.createFromJaveType(layoutId, command.getDispOrder(),
				command.getPersonInfoCategoryID(), command.getLayoutItemType());
	}

	private LayoutPersonInfoClsDefinition toClassItemDefDomain(ClassificationItemDfCommand command, String layoutId,
			int classDispOrder) {
		return LayoutPersonInfoClsDefinition.createFromJavaType(layoutId, classDispOrder, command.getDispOrder(),
				command.getPersonInfoItemDefinitionID());
	}

}
