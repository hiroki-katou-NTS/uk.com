package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.setting.UserInfoUseMethod_Dto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_Repository;

/**
 * Command ユーザ情報の使用方法を変更する, ユーザ情報の使用方法を追加する 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserInfoUseMethod_SaveCommandHandler extends CommandHandler<UserInfoUseMethod_SaveCommand> {
	@Inject
	private UserInfoUseMethod_Repository userInfoUseMethod_Repository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UserInfoUseMethod_SaveCommand> context) {
		UserInfoUseMethod_Dto userInfoUseMethod_Dto = context.getCommand().getUserInfoUseMethod_Dto();
		String cId = userInfoUseMethod_Dto.getCompanyId();
		Optional<UserInfoUseMethod_> userInfoUseMethod_ = userInfoUseMethod_Repository.findByCId(cId);
		if (userInfoUseMethod_.isPresent()) {
			/**
			 * ユーザ情報の使用方法を変更する
			 */
			this.userInfoUseMethod_Repository.update(UserInfoUseMethod_.createFromMemento(userInfoUseMethod_Dto));
		} else {
			/**
			 * ユーザ情報の使用方法を追加する
			 */
			this.userInfoUseMethod_Repository.insert(UserInfoUseMethod_.createFromMemento(userInfoUseMethod_Dto));
		}
	}
}
