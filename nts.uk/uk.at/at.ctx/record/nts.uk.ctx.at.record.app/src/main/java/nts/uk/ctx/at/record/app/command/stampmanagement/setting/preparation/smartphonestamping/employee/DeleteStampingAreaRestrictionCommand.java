package nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;

@Stateless
public class DeleteStampingAreaRestrictionCommand extends CommandHandler<StampingAreaCmd> {
	@Inject
	private  StampingAreaRepository stampingAreaReposiroty ;
	@Override
	protected void handle(CommandHandlerContext<StampingAreaCmd> context) {
		
		StampingAreaCmd cmd = context.getCommand();
		
		if (cmd.getEmployeeId() !=null && !cmd.getEmployeeId().isEmpty()) {
			Optional<StampingAreaRestriction> result = stampingAreaReposiroty.findByEmployeeId(cmd.getEmployeeId());
			if (result.isPresent()) {
				stampingAreaReposiroty.deleteStampSetting(cmd.getEmployeeId());
			}
		}
	}
	

}
