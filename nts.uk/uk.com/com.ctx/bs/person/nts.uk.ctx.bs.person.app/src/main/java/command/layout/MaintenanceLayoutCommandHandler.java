/**
 * 
 */
package command.layout;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;
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
	
	

	String companyId = AppContexts.user().companyId();

	Boolean checkExit = false;

	@Override
	protected void handle(CommandHandlerContext<MaintenanceLayoutCommand> context) {

		MaintenanceLayoutCommand command = context.getCommand();

		String newLayoutId = IdentifierUtil.randomUniqueId();

		// get Old Layout
		MaintenanceLayout oldLayout = this.repo.getById(companyId, command.getId()).get();

		// kiem tra newLayoutcode da ton tai chua.
		checkExit = this.repo.checkExit(companyId, command.getCode());

		switch (command.getAction()) {
		case 0: // insert
			// set new layoutID for insert object
			command.setId(newLayoutId);

			break;
		case 1: // update

			break;
		case 2: // copy
		case 3: // clone and override

			this.overrideLayout(command, newLayoutId);
			break;
		case 4: // remove

			break;
		}

	}

	private void overrideLayout(MaintenanceLayoutCommand command, String newLayoutId) {

		if (checkExit) {
			// khong check 上書き Overwrite ==> la truong hop coppy
			if (command.getAction() == 2) {
				// throw #Msg_3
			} else if (command.getAction() == 3) {
				// co check ==> truong hop override

			}
		}

	}

	private void coppyLayout(MaintenanceLayoutCommand command, String newLayoutId) {
		if (!checkExit) {
			// insert vao bang MaintenanceLayout
			MaintenanceLayout newLayout = MaintenanceLayout.createFromJavaType(companyId, newLayoutId,
					command.getCode(), command.getName());
			this.repo.add(newLayout);
			
			
			
			
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
