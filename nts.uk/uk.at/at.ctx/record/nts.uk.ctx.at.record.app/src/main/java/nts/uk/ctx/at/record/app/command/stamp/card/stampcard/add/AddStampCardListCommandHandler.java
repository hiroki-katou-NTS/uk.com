package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.add;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddStampCardListCommandHandler extends CommandHandlerWithResult<List<AddStampCardCommand>, List<PeregAddCommandResult>>
implements PeregAddListCommandHandler<AddStampCardCommand>{
	@Inject
	private StampCardRepository stampCardRepo;
	@Override
	public String targetCategoryCd() {
		return "CS00069";
	}

	@Override
	public Class<?> commandClass() {
		return AddStampCardCommand.class;
	}

	@Override
	protected List<PeregAddCommandResult> handle(CommandHandlerContext<List<AddStampCardCommand>> context) {
		List<AddStampCardCommand> cmd = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		List<StampCard> insertLst = new ArrayList<>();
		cmd.stream().forEach(c ->{
			// create new domain and add
			if (c.getStampNumber() != null) {
				String stampCardId = IdentifierUtil.randomUniqueId();
				StampCard stampCard = StampCard.createFromJavaType(stampCardId, c.getEmployeeId(),
						c.getStampNumber(), GeneralDate.today(), contractCode);
				insertLst.add(stampCard);
			}

		});
		if(!insertLst.isEmpty()) {
			stampCardRepo.addAll(insertLst);
		}
		return null;
	}

}
