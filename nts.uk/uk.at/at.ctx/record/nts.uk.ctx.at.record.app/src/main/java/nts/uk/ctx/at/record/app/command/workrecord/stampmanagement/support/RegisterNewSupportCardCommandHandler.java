package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEdit;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEditRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRequireImpl;

/**
 * 応援カードの新規登録を行う
 * 
 * @author NWS_namnv
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterNewSupportCardCommandHandler extends CommandHandler<SupportCardCommand> {

	@Inject
	private SupportCardRepository supportCardRepository;

	@Inject
	private SupportCardEditRepository supportCardEditRepository;

	@Override
	protected void handle(CommandHandlerContext<SupportCardCommand> context) {
		SupportCardCommand command = context.getCommand();
		Require require = new SupportCardRequireImpl(supportCardEditRepository);

		// カードを編集する(編集前番号): 応援カード番号
		Optional<SupportCardEdit> optEdit = this.supportCardEditRepository.get(command.getCompanyId());
		SupportCardNumber supportCardNumber = optEdit
				.map(data -> data.editTheCard(new SupportCardNumber(command.getSupportCardNumber())))
				.orElse(new SupportCardNumber(command.getSupportCardNumber()));

		// get(応援カード番号): 応援カード
		Optional<SupportCard> supportCard = this.supportCardRepository.getBySupportCardNo(supportCardNumber.v());

		// not 応援カード番号.empty
		if (supportCard.isPresent()) {
			throw new BusinessException("Msg_2137");
		}

		// 応援カード作成する
		SupportCard domain = SupportCard.create(require, command.getCompanyId(),
				new SupportCardNumber(command.getSupportCardNumber()), command.getWorkplaceId());

		// persist
		this.supportCardRepository.insert(Arrays.asList(domain));
	}

}
