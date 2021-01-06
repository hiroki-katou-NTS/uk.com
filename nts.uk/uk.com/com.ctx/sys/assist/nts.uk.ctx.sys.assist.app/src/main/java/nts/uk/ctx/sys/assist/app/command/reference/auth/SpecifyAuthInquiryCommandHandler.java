package nts.uk.ctx.sys.assist.app.command.reference.auth;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.reference.auth.SpecifyAuthInquiry;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.参照権限.App.参照できる権限を設定する.参照できる権限を設定する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SpecifyAuthInquiryCommandHandler extends CommandHandler<SpecifyAuthInquiryCommand> {

	@Override
	protected void handle(CommandHandlerContext<SpecifyAuthInquiryCommand> context) {
		SpecifyAuthInquiryCommand command = context.getCommand();
		SpecifyAuthInquiry domain = SpecifyAuthInquiry.builder()
				.cid(command.getCid())
				.employmentRoleId(command.getEmploymentRoleId())
				.positonIdSeen(command.getPositonIdSeen())
				.build();
	}

}
