package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.add;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddStampCardListCommandHandler extends CommandHandlerWithResult<List<AddStampCardCommand>, List<MyCustomizeException>>
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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddStampCardCommand>> context) {
		List<AddStampCardCommand> cmd = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		List<StampCard> insertLst = new ArrayList<>();
		List<String> sids= new ArrayList<>();
		List<MyCustomizeException> result = new ArrayList<>();
		cmd.stream().forEach(c ->{
			// create new domain and add
			if (c.getStampNumber() != null) {
				String stampCardId = IdentifierUtil.randomUniqueId();
				Optional<StampCard> stampCardOpt = this.stampCardRepo.getByCardNoAndContractCode(c.getStampNumber(), contractCode);
				if (stampCardOpt.isPresent()) {
					sids.add(c.getEmployeeId());
				} else {
					StampCard stampCard = StampCard.createFromJavaType(stampCardId, c.getEmployeeId(),
							c.getStampNumber(), GeneralDate.today(), contractCode);
					insertLst.add(stampCard);
				}

			}

		});
		
		if(!sids.isEmpty()) {
			result.add(new MyCustomizeException("Msg_346", sids));
		}
		
		if(!insertLst.isEmpty()) {
			stampCardRepo.addAll(insertLst);
		}
		
		return result;
	}

}
