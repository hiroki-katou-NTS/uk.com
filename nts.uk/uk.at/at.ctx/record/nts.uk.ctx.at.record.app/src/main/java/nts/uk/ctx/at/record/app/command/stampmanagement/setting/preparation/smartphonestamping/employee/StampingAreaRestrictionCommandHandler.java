package nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaReposiroty;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;

@Stateless
public class StampingAreaRestrictionCommandHandler extends CommandHandler<StampingAreaCmd> {
	
	@Inject
	private  StampingAreaReposiroty stampingAreaReposiroty;
	@Override
	protected void handle(CommandHandlerContext<StampingAreaCmd> context) {
		StampingAreaCmd cmd = context.getCommand();
			Optional<StampingAreaRestriction> result = stampingAreaReposiroty.findByEmployeeId(cmd.getEmployeeId());
			if (result.isPresent()) {
				stampingAreaReposiroty.updateStampingArea(cmd.getEmployeeId(), cmd.toDomain());
			}
		else {	
			stampingAreaReposiroty.insertStampingArea(cmd.getEmployeeId(),cmd.toDomain());
		}
	
	}

}
