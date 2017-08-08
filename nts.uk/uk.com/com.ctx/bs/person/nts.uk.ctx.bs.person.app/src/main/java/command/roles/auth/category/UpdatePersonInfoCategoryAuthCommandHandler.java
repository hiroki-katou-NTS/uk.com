package command.roles.auth.category;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
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

//		UpdatePersonInfoCategoryAuthCommand category = context.getCommand();
//		String companyId = AppContexts.user().companyId();
//		category.getLstCategory().stream().forEach(p -> {
//			
//			PersonInfoRoleAuth roleAuth = this.roleAuthRepo.getDetailPersonRoleAuth(p.getRoleId()).orElse(null);
//			if (roleAuth == null) {
//				PersonInfoRoleAuth addRoleAuth = PersonInfoRoleAuth.createFromJavaType(p.getRoleId(), companyId, 0,
//						0, 0, 0, 0, 0);
//				this.roleAuthRepo.add(addRoleAuth);
//			} else {
//				List<PersonInfoCategoryAuth> categoryLst = this.categoryAuthRepo.getAllPersonCategoryAuthByRoleId(p.getRoleId());
//				if (categoryLst.size() > 0) {
//					categoryLst.stream().forEach(c ->{
//
//						PersonInfoCategoryAuth categoryAuth = this.categoryAuthRepo.getDetailPersonRoleAuth(p.getRoleId(), p.getCategoryId());
//						PersonInfoCategoryAuth updateCategory = PersonInfoCategoryAuth.createFromJavaType(category.getRoleId(),category.getCategoryId(),
//						 p.isAllowPersonRef() == true? 1: 0, p.isAllowOtherRef()== true? 1: 0, categoryAuth.getAllowOtherCompanyRef().value,
//						 categoryAuth.getSelfPastHisAuth().value, categoryAuth.getSelfFutureHisAuth().value, categoryAuth.getSelfAllowAddHis().value,
//						 categoryAuth.getSelfAllowDelHis().value, categoryAuth.getOtherPastHisAuth().value, categoryAuth.getOtherFutureHisAuth().value,
//						 categoryAuth.getOtherAllowAddHis().value, categoryAuth.getOtherAllowDelHis().value, categoryAuth.getSelfAllowAddMulti().value,
//						 categoryAuth.getSelfAllowDelMulti().value, categoryAuth.getOtherAllowAddMulti().value, categoryAuth.getOtherAllowDelMulti().value);
//						this.categoryAuthRepo.update(updateCategory);
//					});
//				} else {
//					PersonInfoCategoryAuth addCategoryAuth = PersonInfoCategoryAuth.createFromJavaType(p.getRoleId(),
//							p.getCategoryId(), 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0);
//					this.categoryAuthRepo.add(addCategoryAuth);
//				}
//
//			}
//		});

	}

}
