package nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.WorkLocationReposiroty;

@Stateless
public class StampingAreaRestrictionCommandHandler extends CommandHandler<StampingAreaRestrictionCommand> {
	
	@Inject
	private  WorkLocationReposiroty workLocationRepository;
	@Override
	protected void handle(CommandHandlerContext<StampingAreaRestrictionCommand> context) {
		StampingAreaRestrictionCommand areaRestrictionCommand = context.getCommand();
			Optional<StampingAreaRestriction> result = workLocationRepository.findByEmployeeId(areaRestrictionCommand.getEmployeeId());
			if (result.isPresent()) {
				workLocationRepository.updateStampingArea(areaRestrictionCommand.getEmployeeId(), areaRestrictionCommand.toDomain());
			}
		else {	
			workLocationRepository.insertStampingArea(areaRestrictionCommand.getEmployeeId(),areaRestrictionCommand.toDomain());
		}
	
	}

}
