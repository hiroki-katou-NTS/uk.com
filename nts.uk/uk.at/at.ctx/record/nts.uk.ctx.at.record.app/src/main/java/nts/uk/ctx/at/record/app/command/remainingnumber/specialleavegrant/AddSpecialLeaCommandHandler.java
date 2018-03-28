package nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;

@Stateless
public class AddSpecialLeaCommandHandler extends AsyncCommandHandler<SpecialLeaveRemainCommand> {

	@Inject
	private SpecialLeaveGrantRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SpecialLeaveRemainCommand> context) {
		
		SpecialLeaveRemainCommand command = context.getCommand();
		String specialId = IdentifierUtil.randomUniqueId();
		// 付与日＞使用期限の場合はエラー #Msg_1023
		if (command.getGrantDate().compareTo(command.getDeadlineDate()) > 0){
			throw new BusinessException("Msg_1023");
		}
		
		SpecialLeaveGrantRemainingData data = SpecialLeaveGrantRemainingData.createFromJavaType(specialId, command.getSid(), 
				command.getSpecialLeaCode(), command.getGrantDate(), 
				command.getDeadlineDate(), command.getExpStatus(), GrantRemainRegisterType.MANUAL.value,
				command.getNumberDayGrant(), command.getTimeGrant(), 
				command.getNumberDayUse(),command.getTimeUse(), 
				null, 
				command.getNumberDaysOver(),command.getTimeOver(), 
				command.getNumberDayRemain(), command.getTimeRemain());
		
		repo.add(data);
		
	}
		
}
