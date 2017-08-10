/**
 * 
 */
package command.layout;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;
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
	private ILayoutPersonInfoClsRepository clsRepo;

	String companyId = AppContexts.user().companyId();

	@Override
	protected void handle(CommandHandlerContext<MaintenanceLayoutCommand> context) {

		MaintenanceLayoutCommand command = context.getCommand();

		String newLayoutId = IdentifierUtil.randomUniqueId();

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

		// get Old Layout
		MaintenanceLayout oldLayout = this.repo.getById(companyId, command.getId()).get();

		// kiem tra newLayoutcode da ton tai chua.
		Boolean checkExit = this.repo.checkExit(companyId, command.getCode());
		if (checkExit) {
			// khong check 上書き Overwrite ==> la truong hop coppy
			if (command.getAction() == 2) {
				// throw #Msg_3
			} else if (command.getAction() == 3) {
				// co check ==> truong hop override

			}
		} else {
			// truong hop coppy
			// insert vao bang MaintenanceLayout
			MaintenanceLayout newLayout = MaintenanceLayout.createFromJavaType(companyId, newLayoutId,
					command.getCode(), command.getName());
			this.repo.add(newLayout);
			
			
			

			// lay ra list itemcls , sau do update layoutId = new LayoutId , sau do insert
			// list nay bang ItemCls
			List<LayoutPersonInfoClassification> listCls = this.clsRepo.getAllByLayoutId(command.getId());

			listCls.forEach(item -> {
				item.setLayoutID(newLayoutId);
			});
			// insert list to ItemCls Table
			this.clsRepo.addClassifications(listCls);

			// lay ra list itemclsDf

		}

	}
}
