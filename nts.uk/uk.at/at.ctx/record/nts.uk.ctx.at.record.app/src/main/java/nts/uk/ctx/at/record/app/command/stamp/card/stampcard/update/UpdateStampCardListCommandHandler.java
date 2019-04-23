package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateStampCardListCommandHandler extends CommandHandler<List<UpdateStampCardCommand>>
implements PeregUpdateListCommandHandler<UpdateStampCardCommand>{
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
	protected void handle(CommandHandlerContext<List<UpdateStampCardCommand>> context) {
		List<UpdateStampCardCommand> cmd = context.getCommand();
		List<UpdateStampCardCommand> stampCardNotNull = cmd.parallelStream().filter(c -> c.getStampNumber()!= null).collect(Collectors.toList());
		Map<String, String> cardQuery = stampCardNotNull.parallelStream().collect(Collectors.toMap(UpdateStampCardCommand::getStampNumber, UpdateStampCardCommand::getEmployeeId));
		List<String> empErrors = new ArrayList<>();
		// check duplicate cardNo trong cùng 1 contractCode
		if(!stampCardNotNull.isEmpty()) {
			List<StampCard> updateLst = new ArrayList<>();
			String contractCode = AppContexts.user().contractCode();
			Map<String, StampCard> stampCardByCardNoMaps = this.stampCardRepo.getByCardNoAndContractCode(cardQuery, contractCode);
			Map<String, List<StampCard>> stampCardByIdMaps = this.stampCardRepo.getByStampCardId(contractCode,
					stampCardNotNull.parallelStream().map(c -> c.getStampNumberId()).collect(Collectors.toList()));
			stampCardNotNull.parallelStream().forEach(c ->{
				StampCard stampCardByCardNo = stampCardByCardNoMaps.get(c.getEmployeeId());
				List<StampCard> stampCardById = stampCardByIdMaps.get(c.getEmployeeId());
				if (stampCardByCardNo != null && stampCardById!= null && stampCardById.get(0).getStampNumber() != stampCardByCardNo.getStampNumber()) {
					empErrors.add(c.getEmployeeId());
				}
				// update domain
				StampCard stampCard = StampCard.createFromJavaType(c.getStampNumberId(), c.getEmployeeId(),
						c.getStampNumber(), GeneralDate.today(), AppContexts.user().contractCode());
				updateLst.add(stampCard);
			});
			if(!updateLst.isEmpty()) {
				stampCardRepo.updateAll(updateLst);
			}
		}
		
	}

}
