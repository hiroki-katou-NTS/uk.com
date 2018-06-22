package nts.uk.ctx.pereg.app.command.roles.auth.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;

@Stateless
@Transactional
public class UpdatePersonInfoCategoryAuthCommandHandler extends CommandHandler<UpdatePersonInfoCategoryAuthCommand> {

	@Inject
	private PersonInfoCategoryAuthRepository categoryAuthRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdatePersonInfoCategoryAuthCommand> context) {

		UpdatePersonInfoCategoryAuthCommand category = context.getCommand();
		
		category.getLstCategory().stream().forEach(p -> {

			PersonInfoCategoryAuth categoryAuth = this.categoryAuthRepo
					.getDetailPersonCategoryAuthByPId(p.getRoleId(), p.getCategoryId()).orElse(null);

			if (categoryAuth != null) {
				categoryAuth.updateFromJavaType(p.getAllowPersonRef(), p.getAllowOtherRef());
				this.categoryAuthRepo.update(categoryAuth);

			} else {
				PersonInfoCategoryAuth addCategoryAuth = PersonInfoCategoryAuth.createFromDefaulValue(p.getRoleId(),
						p.getCategoryId(), p.getAllowPersonRef(), p.getAllowOtherRef());
				this.categoryAuthRepo.add(addCategoryAuth);
			}

		});

	}


}
