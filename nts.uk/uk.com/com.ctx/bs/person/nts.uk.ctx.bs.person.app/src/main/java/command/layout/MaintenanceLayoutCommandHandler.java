/**
 * 
 */
package command.layout;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.bs.person.dom.person.layout.LayoutCode;
import nts.uk.ctx.bs.person.dom.person.layout.LayoutName;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.LayoutPersonInfoClsDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
@Transactional
public class MaintenanceLayoutCommandHandler extends CommandHandler<MaintenanceLayoutCommand> {

	@Inject
	private IMaintenanceLayoutRepository repo;

	@Inject
	private ILayoutPersonInfoClsRepository classfRepo;

	@Inject
	ILayoutPersonInfoClsDefRepository clsDefRepo;

	String companyId = AppContexts.user().companyId();
	Boolean checkExit = false;

	@Override
	protected void handle(CommandHandlerContext<MaintenanceLayoutCommand> context) {

		MaintenanceLayoutCommand command = context.getCommand();

		String newLayoutId = IdentifierUtil.randomUniqueId();

		// get Old Layout
		if (command.getId() != null) {
			MaintenanceLayout oldLayout = this.repo.getById(companyId, command.getId()).get();
		}
		// kiem tra newLayoutcode da ton tai chua.
		checkExit = this.repo.checkExit(companyId, command.getCode());

		switch (command.getAction()) {
		case 0: // insert
			// set new layoutID for insert object
			insertLayout(command, newLayoutId);
			break;
		case 1: // update
			updateLayout(command);
			break;
		case 2: // copy

			this.coppyLayout(command, newLayoutId);
			break;
		case 3: // clone and override

			this.overrideLayout(command, newLayoutId);
			break;
		case 4: // remove

			break;
		}

	}

	private void insertLayout(MaintenanceLayoutCommand command, String newLayoutId) {
		if (!checkExit) {
			MaintenanceLayout newLayout = new MaintenanceLayout(companyId, newLayoutId,
					new LayoutCode(command.getCode()), new LayoutName(command.getName()));
			this.repo.add(newLayout);
		}
	}

	private void deleteLayout(MaintenanceLayoutCommand command) {

	}

	private void updateLayout(MaintenanceLayoutCommand command) {
		
		

	}

	private void overrideLayout(MaintenanceLayoutCommand command, String newLayoutId) {

		if (checkExit) {
			// get Old Layout
			MaintenanceLayout oldLayout = this.repo.getByCode(companyId, command.getCode()).get();
			// xoa layout cu
			repo.remove(oldLayout);

			// rmove all classification in this layout
			classfRepo.removeAllByLayoutId(command.getId());

			// remove all itemdefinition relation with classification in this layout
			clsDefRepo.removeAllByLayoutId(command.getId());

			// insert vao bang MaintenanceLayout
			MaintenanceLayout newLayout = new MaintenanceLayout(companyId, newLayoutId,
					new LayoutCode(command.getCode()), new LayoutName(command.getName()));
			;
			this.repo.add(newLayout);

			List<ClassificationCommand> classCommands = command.getClassifications();
			if (classCommands != null) {
				// add all classification on client to db
				classfRepo.addClassifications(classCommands.stream().map(m -> toClassificationDomain(m, newLayoutId))
						.collect(Collectors.toList()));

				// add all item definition relation with classification to db
				for (ClassificationCommand classCommand : classCommands) {
					List<ClassificationItemDfCommand> clsIDfs = classCommand.getListItemClsDf();
					if (clsIDfs != null) {
						clsDefRepo.addClassificationItemDefines(clsIDfs.stream()
								.map(m -> toClassItemDefDomain(m, newLayoutId, classCommand.getDispOrder()))
								.collect(Collectors.toList()));
					}
				}
			}
		}
	}

	private void coppyLayout(MaintenanceLayoutCommand command, String newLayoutId) {

		if (!checkExit) {
			// insert vao bang MaintenanceLayout
			MaintenanceLayout newLayout = MaintenanceLayout.createFromJavaType(companyId, newLayoutId,
					command.getCode(), command.getName());
			this.repo.add(newLayout);

			List<ClassificationCommand> classCommands = command.getClassifications();
			if (classCommands != null) {
				// add all classification on client to db
				classfRepo.addClassifications(classCommands.stream()
						.map(item -> toClassificationDomain(item, newLayoutId)).collect(Collectors.toList()));

				// add all item definition relation with classification to db
				for (ClassificationCommand classCommand : classCommands) {
					List<ClassificationItemDfCommand> clsIDfs = classCommand.getListItemClsDf();
					if (clsIDfs != null) {
						clsDefRepo.addClassificationItemDefines(clsIDfs.stream()
								.map(m -> toClassItemDefDomain(m, newLayoutId, classCommand.getDispOrder()))
								.collect(Collectors.toList()));
					}
				}
			}
		} else {
			// throw #Msg_3
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
