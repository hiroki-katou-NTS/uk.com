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
import nts.uk.ctx.bs.person.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.LayoutPersonInfoClsDefinition;

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

		// get new layout domain and command
		NewLayout update = layoutRepo.getLayout().get();
		NewLayoutCommand command = context.getCommand();

		// rmove all classification in this layout
		classfRepo.removeAllByLayoutId(update.getLayoutID());

		// remove all itemdefinition relation with classification in this layout
		clsDefRepo.removeAllByLayoutId(update.getLayoutID());

		//
		List<ClassificationCommand> classCommands = command.getListClass();
		if (!classCommands.isEmpty()) {
			// add all classification on client to db
			classfRepo.addClassifications(classCommands.stream()
					.map(m -> toClassificationDomain(m, update.getLayoutID())).collect(Collectors.toList()));

			// add all item definition relation with classification to db
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
