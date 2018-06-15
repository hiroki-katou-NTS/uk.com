package nts.uk.ctx.pereg.app.command.roles.auth;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.app.command.roles.auth.category.SavePersonInfoCategoryAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.item.PersonInfoItemAuthCommand;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;

@Stateless
public class SavePersonInfoRoleAuthCommandHandler extends CommandHandler<SavePersonInfoRoleAuthCommand> {
	
	@Inject
	private PersonInfoCategoryAuthRepository pCategoryAuthRepo;
	@Inject
	private PersonInfoItemAuthRepository pItemAuthRepo;

	@Override
	public void handle(CommandHandlerContext<SavePersonInfoRoleAuthCommand> context) {

		SavePersonInfoRoleAuthCommand roleCmd = context.getCommand();

		saveCtgAuth(roleCmd);

		saveItemAuth(roleCmd);

	}

	private void saveCtgAuth(SavePersonInfoRoleAuthCommand roleCmd) {

		SavePersonInfoCategoryAuthCommand ctgCmd = roleCmd.getCurrentCategory();

		String ctgId = ctgCmd.getCategoryId();

		String roleId = roleCmd.getRoleId();

		Optional<PersonInfoCategoryAuth> ctgAuthOpt = this.pCategoryAuthRepo.getDetailPersonCategoryAuthByPId(roleId,
				ctgId);
		if (ctgAuthOpt.isPresent()) {
			PersonInfoCategoryAuth oldCtgAuth = ctgAuthOpt.get();
			oldCtgAuth.updateFromJavaType(ctgCmd.getCategoryType(), ctgCmd.getAllowPersonRef(),
					ctgCmd.getAllowOtherRef(),ctgCmd.getAllowOtherCompanyRef(), ctgCmd.getSelfPastHisAuth(), ctgCmd.getSelfFutureHisAuth(),
					ctgCmd.getSelfAllowAddHis(), ctgCmd.getSelfAllowDelHis(), ctgCmd.getOtherPastHisAuth(),
					ctgCmd.getOtherFutureHisAuth(), ctgCmd.getOtherAllowAddHis(), ctgCmd.getOtherAllowDelHis(),
					ctgCmd.getSelfAllowAddMulti(), ctgCmd.getSelfAllowDelMulti(), ctgCmd.getOtherAllowAddMulti(),
					ctgCmd.getOtherAllowDelMulti());

			this.pCategoryAuthRepo.update(oldCtgAuth);

		} else {
			PersonInfoCategoryAuth ctg = PersonInfoCategoryAuth.createFromJavaType(ctgCmd.getCategoryType(), roleId,
					ctgCmd.getCategoryId(), ctgCmd.getAllowPersonRef(), ctgCmd.getAllowOtherRef(),
					ctgCmd.getAllowOtherCompanyRef(),
					ctgCmd.getSelfPastHisAuth(), ctgCmd.getSelfFutureHisAuth(), ctgCmd.getSelfAllowAddHis(),
					ctgCmd.getSelfAllowDelHis(), ctgCmd.getOtherPastHisAuth(), ctgCmd.getOtherFutureHisAuth(),
					ctgCmd.getOtherAllowAddHis(), ctgCmd.getOtherAllowDelHis(), ctgCmd.getSelfAllowAddMulti(),
					ctgCmd.getSelfAllowDelMulti(), ctgCmd.getOtherAllowAddMulti(), ctgCmd.getOtherAllowDelMulti());
			if (ctg != null) {
				this.pCategoryAuthRepo.add(ctg);
			}

		}
	}

	private void saveItemAuth(SavePersonInfoRoleAuthCommand roleCmd) {

		SavePersonInfoCategoryAuthCommand ctgCmd = roleCmd.getCurrentCategory();

		String roleId = roleCmd.getRoleId();

		String ctgId = ctgCmd.getCategoryId();

		List<PersonInfoItemAuthCommand> roleItems = ctgCmd.getItems();

		roleItems.forEach(roleItem -> {

			updateItemAuth(roleId, ctgId, roleItem, ctgCmd);

		});
	}

	private void updateItemAuth(String roleId, String categoryId, PersonInfoItemAuthCommand itemCmd,
			SavePersonInfoCategoryAuthCommand ctgCmd) {

		// boolean isNeedSave = ctgCmd.getAllowPersonRef() == 1 ||
		// ctgCmd.getAllowOtherRef() == 1;

		// if (isNeedSave) {
		// check available item auth
		Optional<PersonInfoItemAuth> oldItemAuthOpt = this.pItemAuthRepo.getItemDetai(roleId, categoryId,
				itemCmd.getPersonItemDefId());

		int selfAuth = itemCmd.getSelfAuth();

		int otherAuth = itemCmd.getOtherAuth();

		if (oldItemAuthOpt.isPresent()) {

			PersonInfoItemAuth oldItemAuth = oldItemAuthOpt.get();

			oldItemAuth.updateFromJavaType(selfAuth, otherAuth);

			this.pItemAuthRepo.update(oldItemAuth);

		} else {

			PersonInfoItemAuth newItemAuth = PersonInfoItemAuth.createFromJavaType(roleId, categoryId,
					itemCmd.getPersonItemDefId(), selfAuth, otherAuth);

			this.pItemAuthRepo.add(newItemAuth);

		}
		// }

	}

}
