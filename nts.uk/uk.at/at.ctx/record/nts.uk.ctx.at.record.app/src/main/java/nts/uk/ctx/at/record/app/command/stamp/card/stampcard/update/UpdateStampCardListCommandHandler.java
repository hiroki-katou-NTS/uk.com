package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateStampCardListCommandHandler extends CommandHandlerWithResult<List<UpdateStampCardCommand>, List<MyCustomizeException>>
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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateStampCardCommand>> context) {
		List<UpdateStampCardCommand> cmd = context.getCommand();
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		List<StampCard> updateLst = new ArrayList<>();
		// check duplicate cardNo trong cùng 1 contractCode
		cmd.stream().forEach(command ->{
			if (command.getStampNumber() != null) {

				Optional<StampCard> origin = this.stampCardRepo.getByStampCardId(command.getStampNumberId());

				String contractCode = AppContexts.user().contractCode();
				Optional<StampCard> duplicate = this.stampCardRepo.getByCardNoAndContractCode(command.getStampNumber(),
						contractCode);

				if (duplicate.isPresent() && origin.isPresent() && origin.get().getStampNumber().toString() != duplicate.get().getStampNumber().toString()) {
					errorExceptionLst.add(new MyCustomizeException("Msg_1106", Arrays.asList(command.getEmployeeId())));
				}else {
					// update domain
					StampCard stampCard = new StampCard(new ContractCode(AppContexts.user().contractCode()),
							new StampNumber(command.getStampNumber()), command.getEmployeeId(), GeneralDate.today(),
							command.getStampNumberId());

					updateLst.add(stampCard);
				}
			}

		});
		
		if(!updateLst.isEmpty()) {
			stampCardRepo.updateAll(updateLst);
		}
		return errorExceptionLst;
	}

}
