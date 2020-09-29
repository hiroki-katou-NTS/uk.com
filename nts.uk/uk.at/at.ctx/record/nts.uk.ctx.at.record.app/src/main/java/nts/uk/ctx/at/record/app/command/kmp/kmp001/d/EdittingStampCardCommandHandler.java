package nts.uk.ctx.at.record.app.command.kmp.kmp001.d;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardDigitNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditMethod;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EdittingStampCardCommandHandler extends CommandHandler<EdittingStampCardCommand> {

	@Inject
	private StampCardEditingRepo stampCardEditingRepo;

	@Override
	protected void handle(CommandHandlerContext<EdittingStampCardCommand> context) {
		EdittingStampCardCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		StampCardEditing cardEditing = stampCardEditingRepo.get(companyId);

		if (cardEditing != null) {
			cardEditing.setDigitsNumber(new StampCardDigitNumber(Integer.parseInt(command.getDigitsNumber())));
			switch (command.getStampMethod()) {
			case "1":
				cardEditing.setStampMethod(StampCardEditMethod.PreviousZero);
				break;
			case "2":
				cardEditing.setStampMethod(StampCardEditMethod.AfterZero);
				break;
			case "3":
				cardEditing.setStampMethod(StampCardEditMethod.PreviousSpace);
				break;
			case "4":
				cardEditing.setStampMethod(StampCardEditMethod.AfterSpace);
				break;
			}
		}
		
		stampCardEditingRepo.update(cardEditing);
	}
}
