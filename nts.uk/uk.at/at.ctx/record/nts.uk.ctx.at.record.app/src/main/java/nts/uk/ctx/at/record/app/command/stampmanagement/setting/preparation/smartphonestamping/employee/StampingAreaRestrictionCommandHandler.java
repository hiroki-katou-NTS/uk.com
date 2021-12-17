package nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestrictionDto;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaReposiroty;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;

@Stateless
public class StampingAreaRestrictionCommandHandler extends CommandHandler<StampingAreaRestrictionDto> {
	
	@Inject
	private  StampingAreaReposiroty stampingAreaReposiroty;
	@Override
	protected void handle(CommandHandlerContext<StampingAreaRestrictionDto> context) {
		StampingAreaRestrictionDto dto = context.getCommand();
			Optional<StampingAreaRestriction> result = stampingAreaReposiroty.findByEmployeeId(dto.getEmployeeId());
			if (result.isPresent()) {
				stampingAreaReposiroty.updateStampingArea(dto.getEmployeeId(), dto.toDomain());
			}
		else {	
			stampingAreaReposiroty.insertStampingArea(dto.getEmployeeId(),dto.toDomain());
		}
	
	}

}
