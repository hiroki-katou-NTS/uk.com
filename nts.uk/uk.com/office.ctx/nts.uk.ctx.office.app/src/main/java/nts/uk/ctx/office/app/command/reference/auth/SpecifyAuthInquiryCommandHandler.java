package nts.uk.ctx.office.app.command.reference.auth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.参照権限.App.参照できる権限を設定する.参照できる権限を設定する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SpecifyAuthInquiryCommandHandler extends CommandHandler<SpecifyAuthInquiryCommand> {

	@Inject
	SpecifyAuthInquiryRepository specifyAuthInquiryRepository;

	@Override
	protected void handle(CommandHandlerContext<SpecifyAuthInquiryCommand> context) {
		SpecifyAuthInquiryCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		SpecifyAuthInquiry domain = SpecifyAuthInquiry.builder()
				.cid(command.getCid())
				.employmentRoleId(command.getEmploymentRoleId())
				.positionIdSeen(command.getPositionIdSeen())
				.build();
		Optional<SpecifyAuthInquiry> checkDomainExists = specifyAuthInquiryRepository
				.getByCidAndRoleId(cid, command.getEmploymentRoleId());
		if (!checkDomainExists.isPresent()) {
			specifyAuthInquiryRepository.insert(domain);
		} else {
			specifyAuthInquiryRepository.update(domain);
		}
	}
}
