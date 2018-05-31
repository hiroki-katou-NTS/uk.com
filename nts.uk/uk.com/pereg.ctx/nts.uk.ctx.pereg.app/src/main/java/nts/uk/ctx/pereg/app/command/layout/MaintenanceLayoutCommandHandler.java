/**
 * 
 */
package nts.uk.ctx.pereg.app.command.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.pereg.dom.person.layout.LayoutCode;
import nts.uk.ctx.pereg.dom.person.layout.LayoutName;
import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutPersonInfoClassification;
import nts.uk.ctx.pereg.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.definition.LayoutPersonInfoClsDefinition;
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



	@Override
	protected void handle(CommandHandlerContext<MaintenanceLayoutCommand> context) {
		String companyId = AppContexts.user().companyId();
		Boolean checkExit = false;
		MaintenanceLayout oldLayout = null;
		MaintenanceLayoutCommand command = context.getCommand();
		String newLayoutId = IdentifierUtil.randomUniqueId();

		// kiem tra newLayoutcode da ton tai chua.
		checkExit = this.repo.checkExit(companyId, command.getCode());

		// get Old Layout
		if (command.getId() != null && checkExit) {
			oldLayout = this.repo.getById(companyId, command.getId()).get();
		}

		switch (command.getAction()) {
		case 0: // insert
			// set new layoutID for insert object
			insertLayout(command, newLayoutId, checkExit, companyId);
			break;
		case 1: // update
			updateLayout(command, checkExit, companyId);
			break;
		case 2: // copy
			this.coppyLayout(command, newLayoutId, checkExit, companyId );
			break;
		case 3: // clone and override
			this.overrideLayout(command, newLayoutId, checkExit, companyId);
			break;
		case 4: // remove
			this.deleteLayout(command, checkExit, companyId, oldLayout);
			break;
		}

	}

	private void insertLayout(MaintenanceLayoutCommand command, String newLayoutId, boolean checkExit, String companyId) {
		if (checkExit) {
			// throw Error Message #Msg_3
			throw new BusinessException(new RawErrorMessage("Msg_3"));

		} else {
			MaintenanceLayout newLayout = new MaintenanceLayout(companyId, newLayoutId,
					new LayoutCode(command.getCode()), new LayoutName(command.getName()));
			this.repo.add(newLayout);
			// hien thi information message (# Msg_15 #)
		}
	}

	private void deleteLayout(MaintenanceLayoutCommand command, boolean checkExit, String companyId, MaintenanceLayout oldLayout) {

		if (!checkExit) {
			// throw Error Message #Msg_3
			throw new BusinessException(new RawErrorMessage("Msg_16"));
		} else {
			this.repo.remove(oldLayout);
			this.classfRepo.removeAllByLayoutId(command.getId());
			this.clsDefRepo.removeAllByLayoutId(command.getId());
		}

	}

	private void updateLayout(MaintenanceLayoutCommand command, boolean checkExit, String companyId) {
		if (checkExit) {
			String layoutId = command.getId();
			// get Old Layout
			MaintenanceLayout layout = this.repo.getByCode(companyId, command.getCode()).get();
			layout.setLayoutName(new LayoutName(command.getName()));

			// update MaintenanceLayout table
			repo.update(layout);

			// truong hop co truyen list itemcls
			boolean check_cls_exit = classfRepo.checkExitItemCls(layoutId);
			if (command.getClassifications().size() > 0) {

				List<ClassificationCommand> classifications = command.getClassifications();
				List<LayoutPersonInfoClassification> list_cls = new ArrayList<>();
				List<LayoutPersonInfoClsDefinition> list_clsDf = new ArrayList<>();
				int sizeListCls = classifications.size();

				// check exit itemClassification in table ItemCls , neu có rồi thì xóa đi insert
				// lại. Chưa có thì insert
				if (check_cls_exit) {
					// update list itemCls <=> (xóa xong insert lai)
					classfRepo.removeAllByLayoutId(layoutId);

					for (int i = 0; i < sizeListCls; i++) {
						list_cls.add(LayoutPersonInfoClassification.createFromJaveType(layoutId,
								classifications.get(i).getDispOrder(), classifications.get(i).getPersonInfoCategoryID(),
								classifications.get(i).getLayoutItemType()));

						if (classifications.get(i).getListItemClsDf().size() > 0) {
							int sizeListClsDf = classifications.get(i).getListItemClsDf().size();
							for (int j = 0; j < sizeListClsDf; j++) {
								list_clsDf.add(LayoutPersonInfoClsDefinition.createFromJavaType(layoutId,
										classifications.get(i).getDispOrder(),
										classifications.get(i).getListItemClsDf().get(j).getDispOrder(), classifications
												.get(i).getListItemClsDf().get(j).getPersonInfoItemDefinitionID()));
							}
						}
					}

					classfRepo.addClassifications(list_cls);

					// xoa het itemClsDf theo layoutId

					clsDefRepo.removeAllByLayoutId(layoutId);

					clsDefRepo.addClassificationItemDefines(list_clsDf);

				} else {
					// insert list itemCls
					for (int i = 0; i < sizeListCls; i++) {
						list_cls.add(LayoutPersonInfoClassification.createFromJaveType(layoutId,
								classifications.get(i).getDispOrder(), classifications.get(i).getPersonInfoCategoryID(),
								classifications.get(i).getLayoutItemType()));

						if (classifications.get(i).getListItemClsDf().size() > 0) {
							int sizeListClsDf = classifications.get(i).getListItemClsDf().size();
							for (int j = 0; j < sizeListClsDf; j++) {
								list_clsDf.add(LayoutPersonInfoClsDefinition.createFromJavaType(layoutId,
										classifications.get(i).getDispOrder(),
										classifications.get(i).getListItemClsDf().get(j).getDispOrder(), classifications
												.get(i).getListItemClsDf().get(j).getPersonInfoItemDefinitionID()));
							}
						}
					}
					classfRepo.addClassifications(list_cls);

					clsDefRepo.addClassificationItemDefines(list_clsDf);

				}
			} else {
				if (check_cls_exit) {
					classfRepo.removeAllByLayoutId(layoutId);
					clsDefRepo.removeAllByLayoutId(layoutId);
				}
			}

		}

	}

	private void overrideLayout(MaintenanceLayoutCommand command, String newLayoutId, boolean checkExit, String companyId) {
		//truong hop layout nay da ton tai roi thi se ghi đè
		if (checkExit) {
			// get Old Layout
			MaintenanceLayout oldLayout = this.repo.getByCode(companyId, command.getCode()).get();
			String layoutID_old = oldLayout.getMaintenanceLayoutID();
			
			// xoa layout cu
			repo.remove(oldLayout);

			// rmove all classification in this layout
			classfRepo.removeAllByLayoutId(oldLayout.getMaintenanceLayoutID());

			// remove all itemdefinition relation with classification in this layout
			clsDefRepo.removeAllByLayoutId(oldLayout.getMaintenanceLayoutID());

			// insert vao bang MaintenanceLayout
			MaintenanceLayout newLayout = new MaintenanceLayout(companyId, layoutID_old,
					new LayoutCode(command.getCode()), new LayoutName(command.getName()));
			
			this.repo.add(newLayout);

			List<ClassificationCommand> classCommands = command.getClassifications();
			if (classCommands != null) {
				// add all classification on client to db
				classfRepo.addClassifications(classCommands.stream().map(m -> toClassificationDomain(m,layoutID_old))
						.collect(Collectors.toList()));

				// add all item definition relation with classification to db
				for (ClassificationCommand classCommand : classCommands) {
					List<ClassificationItemDfCommand> clsIDfs = classCommand.getListItemClsDf();
					if (clsIDfs != null) {
						clsDefRepo.addClassificationItemDefines(clsIDfs.stream()
								.map(m -> toClassItemDefDomain(m, layoutID_old, classCommand.getDispOrder()))
								.collect(Collectors.toList()));
					}
				}
			}
		}else {
			// chưa tồn tại thì insert
			this.insertForCoppyOrOvveride(command, newLayoutId, companyId);
		}
	}

	private void coppyLayout(MaintenanceLayoutCommand command, String newLayoutId, boolean checkExit, String companyId) {

		if (!checkExit) {
			insertForCoppyOrOvveride(command, newLayoutId, companyId);
		} else {
			// throw Error Message #Msg_3
			throw new BusinessException(new RawErrorMessage("Msg_3"));
		}
	}

	/**
	 * @param command
	 * @param newLayoutId
	 */
	private void insertForCoppyOrOvveride(MaintenanceLayoutCommand command, String newLayoutId, String companyId) {
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
