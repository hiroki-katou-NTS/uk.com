package nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

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
				command.getGrantDate(), command.getDeadline(), command.getExpirationStatus(), GrantRemainRegisterType.MANUAL.value,
				command.getGrantDays(), command.getGrantMinutes(), command.getUsedDays(), command.getUsedMinutes(), 
				null, command.getRemainingDays(), command.getRemainingMinutes(), 0d, null, null, null);
		annLeaRepo.add(data);
		return new PeregAddCommandResult(annLeavId);
	}

}
