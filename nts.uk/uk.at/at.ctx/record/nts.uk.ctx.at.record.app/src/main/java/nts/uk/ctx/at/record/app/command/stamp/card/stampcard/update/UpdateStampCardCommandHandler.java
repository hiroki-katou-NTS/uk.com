package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.update;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateStampCardCommandHandler extends CommandHandler<UpdateStampCardCommand>
		implements PeregUpdateCommandHandler<UpdateStampCardCommand> {

	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00069";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateStampCardCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateStampCardCommand> context) {
		UpdateStampCardCommand command = context.getCommand();

		// check duplicate cardNo trong cùng 1 contractCode

		if (command.getStampNumber() != null) {

			Optional<StampCard> origin = this.stampCardRepo.getByStampCardId(command.getStampNumberId());

			String contractCode = AppContexts.user().contractCode();
			Optional<StampCard> duplicate = this.stampCardRepo.getByCardNoAndContractCode(command.getStampNumber(),
					contractCode);

			if (duplicate.isPresent() && origin.isPresent() && origin.get().getStampNumber().toString() != duplicate.get().getStampNumber().toString()) {
				throw new BusinessException("Msg_346" , command.getStampNumber());
			}
			
			// update domain
			StampCard stampCard = StampCard.createFromJavaType(command.getStampNumberId(), command.getEmployeeId(),
					command.getStampNumber(), GeneralDate.today(), AppContexts.user().contractCode());

			stampCardRepo.update(stampCard);

		}

		

	}

}
