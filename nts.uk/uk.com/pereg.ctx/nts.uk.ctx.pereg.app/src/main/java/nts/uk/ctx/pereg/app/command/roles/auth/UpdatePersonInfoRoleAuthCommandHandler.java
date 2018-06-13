package nts.uk.ctx.pereg.app.command.roles.auth;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuth;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdatePersonInfoRoleAuthCommandHandler extends CommandHandler<UpdatePersonInfoRoleAuthCommand> {
	@Inject
	private PersonInfoRoleAuthRepository personRoleAuthRepository;

	@Inject
	private PersonInfoCategoryAuthRepository ctgAuthRepo;

	@Inject
	private PersonInfoItemAuthRepository itemAuthRepo;

	@Override
	public void handle(CommandHandlerContext<UpdatePersonInfoRoleAuthCommand> context) {
		UpdatePersonInfoRoleAuthCommand update = context.getCommand();
		if (update.getRoleIds().size() <= 0) {
			throw new BusinessException(new RawErrorMessage("Msg_365"));
		}
		String companyId = AppContexts.user().companyId();
		Optional<PersonInfoRoleAuth> p_RoleDestination = this.personRoleAuthRepository
				.getDetailPersonRoleAuth(update.getRoleIdDestination(), companyId);
		update.getRoleIds().forEach(c -> {
			Optional<PersonInfoRoleAuth> p_RoleSource = this.personRoleAuthRepository.getDetailPersonRoleAuth(c,
					AppContexts.user().companyId());
			PersonInfoRoleAuth insert = null;
			if (p_RoleDestination.isPresent()) {
				PersonInfoRoleAuth auth = p_RoleDestination.get();
				insert = PersonInfoRoleAuth.createFromJavaType(c, companyId, auth.getAllowDocUpload().value,
						auth.getAllowMapBrowse().value, auth.getAllowDocUpload().value, auth.getAllowDocRef().value,
						auth.getAllowAvatarUpload().value, auth.getAllowAvatarRef().value);
			} else {
				insert = PersonInfoRoleAuth.createFromDefaultValue(c, companyId);
			}
			if (p_RoleSource.isPresent()) {
				this.personRoleAuthRepository.delete(c);
				this.ctgAuthRepo.deleteByRoleId(c);
				this.itemAuthRepo.deleteByRoleId(c);
			}

			List<PersonInfoCategoryAuth> ctgLstUpdate = this.ctgAuthRepo
					.getAllCategoryAuthByRoleId(update.getRoleIdDestination());
			if (ctgLstUpdate.size() > 0) {
				ctgLstUpdate.stream().forEach(ctg -> {
					PersonInfoCategoryAuth addCtg = new PersonInfoCategoryAuth(c, ctg.getPersonInfoCategoryAuthId(),
							ctg.getAllowPersonRef(), ctg.getAllowOtherRef(),
							ctg.getSelfPastHisAuth(), ctg.getSelfFutureHisAuth(), ctg.getSelfAllowAddHis(),
							ctg.getSelfAllowDelHis(), ctg.getOtherPastHisAuth(), ctg.getOtherFutureHisAuth(),
							ctg.getOtherAllowAddHis(), ctg.getOtherAllowDelMulti(), ctg.getSelfAllowAddMulti(),
							ctg.getSelfAllowDelMulti(), ctg.getOtherAllowAddMulti(), ctg.getOtherAllowDelMulti());
					this.ctgAuthRepo.add(addCtg);
					List<PersonInfoItemAuth> itemLst = this.itemAuthRepo.getAllItemAuth(update.getRoleIdDestination(),
							ctg.getPersonInfoCategoryAuthId());

					itemLst.stream().forEach(item -> {
						PersonInfoItemAuth addItem = new PersonInfoItemAuth(c, ctg.getPersonInfoCategoryAuthId(),
								item.getPersonItemDefId(), item.getSelfAuth(), item.getOtherAuth());
						this.itemAuthRepo.add(addItem);

					});

				});
			}

			this.personRoleAuthRepository.add(insert);
		});
	}
}
