package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddSpecialLeaCommandHandler extends CommandHandler<SpecialLeaveRemainCommand> {

	@Inject
	private SpecialLeaveGrantRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SpecialLeaveRemainCommand> context) {
		
		SpecialLeaveRemainCommand command = context.getCommand();
		String specialId = IdentifierUtil.randomUniqueId();
		String cid = AppContexts.user().companyId();
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (command.getGrantDate().compareTo(command.getDeadlineDate()) > 0){
			throw new BusinessException("Msg_1023");
		}
		
		SpecialLeaveGrantRemainingData data = SpecialLeaveGrantRemainingData.createFromJavaType(
				specialId,cid, command.getSid(), command.getSpecialLeaCode(), 
				GeneralDate.fromString(command.getGrantDate(), "yyyy/MM/dd"),
				GeneralDate.fromString(command.getDeadlineDate(), "yyyy/MM/dd"),
				command.getExpStatus(), GrantRemainRegisterType.MANUAL.value,
				command.getNumberDayGrant(), command.getTimeGrant(), 
				command.getNumberDayUse(),command.getTimeUse(), 
				null, 
				command.getNumberDaysOver(),command.getTimeOver(), 
				command.getNumberDayRemain(), command.getTimeRemain(),
				command.grantDateItemName, command.deadlineDateItemName);
		
		repo.add(data);
		
	}
		
}
