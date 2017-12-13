package nts.uk.ctx.pereg.app.command.roles.auth.category;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuth;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdatePersonInfoCategoryAuthCommandHandler extends CommandHandler<UpdatePersonInfoCategoryAuthCommand> {

	@Inject
	private PersonInfoRoleAuthRepository roleAuthRepo;
	@Inject
	private PersonInfoCategoryAuthRepository categoryAuthRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdatePersonInfoCategoryAuthCommand> context) {

		UpdatePersonInfoCategoryAuthCommand category = context.getCommand();
		this.registerCategory(category.getLstCategory());

	}

	private void registerCategory(List<UpdatePersonInfoCategory> lstCategory) {
		String companyId = AppContexts.user().companyId();
		lstCategory.stream().forEach(p -> {

			PersonInfoRoleAuth roleAuth = this.roleAuthRepo
					.getDetailPersonRoleAuth(p.getRoleId(), AppContexts.user().companyId()).orElse(null);
			PersonInfoCategoryAuth addCategoryAuth = PersonInfoCategoryAuth.createFromDefaulValue(p.getRoleId(),
					p.getCategoryId(), p.getAllowPersonRef(), p.getAllowOtherRef());
			if (roleAuth == null) {
				PersonInfoRoleAuth addRoleAuth = PersonInfoRoleAuth.createFromDefaultValue(p.getRoleId(), companyId);
				this.roleAuthRepo.add(addRoleAuth);
				this.categoryAuthRepo.add(addCategoryAuth);

			} else {

				List<PersonInfoCategoryAuth> categoryLst = this.categoryAuthRepo
						.getAllCategoryAuthByRoleId(p.getRoleId());
				if (categoryLst.size() > 0) {
					categoryLst.stream().forEach(c -> {

						PersonInfoCategoryAuth categoryAuth = this.categoryAuthRepo
								.getDetailPersonCategoryAuthByPId(p.getRoleId(), p.getCategoryId()).orElse(null);
						if (categoryAuth != null) {
							categoryAuth.updateFromJavaType(p.getAllowPersonRef(), p.getAllowOtherRef(),
									categoryAuth.getAllowOtherCompanyRef().value,
									categoryAuth.getSelfPastHisAuth().value, categoryAuth.getSelfFutureHisAuth().value,
									categoryAuth.getSelfAllowAddHis().value, categoryAuth.getSelfAllowDelHis().value,
									categoryAuth.getOtherPastHisAuth().value,
									categoryAuth.getOtherFutureHisAuth().value,
									categoryAuth.getOtherAllowAddHis().value, categoryAuth.getOtherAllowDelHis().value,
									categoryAuth.getSelfAllowAddMulti().value,
									categoryAuth.getSelfAllowDelMulti().value,
									categoryAuth.getOtherAllowAddMulti().value,
									categoryAuth.getOtherAllowDelMulti().value);
							this.categoryAuthRepo.update(categoryAuth);

						} else {

							this.categoryAuthRepo.add(addCategoryAuth);
						}
					});
				} else {

					this.categoryAuthRepo.add(addCategoryAuth);
				}

			}
		});

	}

}
