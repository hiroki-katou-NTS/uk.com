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
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddAnnLeaCommandHandler extends CommandHandlerWithResult<AnnLeaGrantRemnNumCommand,PeregAddCommandResult>{
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AnnLeaGrantRemnNumCommand> context) {
		AnnLeaGrantRemnNumCommand command = context.getCommand();
		
		String cid = AppContexts.user().companyId();
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (command.getGrantDate().compareTo(command.getDeadline()) > 0){
			throw new BusinessException("Msg_1023");
			
		}
		/**
		 * update tài liệu 
		 * #設計修正　2018/10/17　渡邉
		 * ユニーク制約を追加
         * EA修正履歴NO.2840
		 */
		if (!annLeaRepo.checkConditionUniqueForAdd(command.getEmployeeId(), command.getGrantDate()).isEmpty()) {
			throw new BusinessException("Msg_1456");
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
