package nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateAnnLeaCommandHandler extends CommandHandler<AnnLeaGrantRemnNumCommand> {

	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<AnnLeaGrantRemnNumCommand> context) {
		AnnLeaGrantRemnNumCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (command.getGrantDate().compareTo(command.getDeadline()) > 0){
			throw new BusinessException("Msg_1023");
		}
		
		AnnualLeaveGrantRemainingData data = AnnualLeaveGrantRemainingData.createFromJavaType(command.getAnnLeavID(),cid, command.getEmployeeId(), 
				command.getGrantDate(), command.getDeadline(), command.getExpirationStatus(), GrantRemainRegisterType.MANUAL.value,
				command.getGrantDays(), command.getGrantMinutes(), command.getUsedDays(), command.getUsedMinutes(), 
				null, command.getRemainingDays(), command.getRemainingMinutes(), 0d, null, null, null);
		annLeaRepo.update(data);
	}

}
