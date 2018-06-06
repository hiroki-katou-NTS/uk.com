package nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
@Stateless
public class AddAnnLeaCommandPeregHandler extends CommandHandlerWithResult<AddAnnLeaGrantRemnNumPeregCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<AddAnnLeaGrantRemnNumPeregCommand>{
	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00037";
	}

	@Override
	public Class<?> commandClass() {
		return AddAnnLeaGrantRemnNumPeregCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddAnnLeaGrantRemnNumPeregCommand> context) {
		AddAnnLeaGrantRemnNumPeregCommand command = context.getCommand();
		
		String cid = AppContexts.user().companyId();
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (command.getGrantDate().compareTo(command.getDeadline()) > 0){
			throw new BusinessException("Msg_1023");
		}
		String annLeavId = IdentifierUtil.randomUniqueId();
		AnnualLeaveGrantRemainingData data = AnnualLeaveGrantRemainingData.createFromJavaType(annLeavId, cid, command.getEmployeeId(), 
				command.getGrantDate(), command.getDeadline(), 
				command.getExpirationStatus() == null? 1: command.getExpirationStatus().intValue(),
				GrantRemainRegisterType.MANUAL.value,
				command.getGrantDays() == null? 0d: command.getGrantDays() .doubleValue(), 
				command.getGrantMinutes() == null? null: command.getGrantMinutes().intValue(), 
				command.getUsedDays() == null? 0d: command.getUsedDays().doubleValue(), 
				command.getUsedMinutes()== null? null: command.getUsedMinutes().intValue(), 
				null, 
				command.getRemainingDays()== null? 0: command.getRemainingDays().doubleValue(), 
				command.getRemainingMinutes() == null? null: command.getRemainingMinutes().intValue(), 
				0d, null, null, null);
		annLeaRepo.add(data);
		return new PeregAddCommandResult(annLeavId);
	}

}
