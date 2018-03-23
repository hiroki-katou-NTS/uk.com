package nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;

@Stateless
public class AddSpecialLeaCommandHandler extends CommandHandler<AddSpecialLeaCommand> {

	@Inject
	private SpecialLeaveGrantRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AddSpecialLeaCommand> context) {
		
		AddSpecialLeaCommand command = context.getCommand();
		
		if(command == null) {
			// exception
		}
		
		String specialLeaID = IdentifierUtil.randomUniqueId();
		
		Optional<SpecialLeaveGrantRemainingData> opt = repo.getBySpecialId(specialLeaID);
		if(opt.isPresent()) {
			
			// exception
		}
		
		SpecialLeaveGrantRemainingData domain = SpecialLeaveGrantRemainingData.createFromJavaType(command.getSpecialLeaId(),command.getSid(), command.getSpecialLeaCode(), 
				command.getGrantDate(), command.getDeadlineDate(), command.getExpStatus(),
				command.getRegisterType(), command.getNumberDayGrant(), command.getTimeGrant(), 
				command.getNumberDayUse(), command.getTimeUse(), command.getUseSavingDays(), 
				command.getNumberDaysOver(), command.getTimeOver(), command.getNumberDayRemain(), command.getTimeRemain());
		
		repo.add(domain);
			
		
		
		
	}

}
