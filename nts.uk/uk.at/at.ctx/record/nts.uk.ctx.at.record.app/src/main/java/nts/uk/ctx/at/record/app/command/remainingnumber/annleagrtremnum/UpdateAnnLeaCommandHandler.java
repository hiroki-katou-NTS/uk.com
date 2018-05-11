package nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;

@Stateless
public class UpdateAnnLeaCommandHandler extends CommandHandler<AnnLeaGrantRemnNumCommand> {

	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<AnnLeaGrantRemnNumCommand> context) {
		AnnLeaGrantRemnNumCommand command = context.getCommand();
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (command.getGrantDate().compareTo(command.getDeadline()) > 0) {
			throw new BusinessException("Msg_1023");
		}

		Optional<AnnualLeaveGrantRemainingData> remainDataOpt = annLeaRepo.findByID(command.getAnnLeavID());
		if (!remainDataOpt.isPresent()) {
			throw new RuntimeException("Can not update item which was deleted!");
		}

		AnnualLeaveGrantRemainingData data = remainDataOpt.get();
		
		data.updateData(command.getGrantDate(), command.getDeadline(), command.getExpirationStatus(),
				GrantRemainRegisterType.MANUAL.value, command.getGrantDays(), command.getGrantMinutes(),
				command.getUsedDays(), command.getUsedMinutes(), null, command.getRemainingDays(),
				command.getRemainingMinutes(), 0d);

		annLeaRepo.update(data);
	}

}
