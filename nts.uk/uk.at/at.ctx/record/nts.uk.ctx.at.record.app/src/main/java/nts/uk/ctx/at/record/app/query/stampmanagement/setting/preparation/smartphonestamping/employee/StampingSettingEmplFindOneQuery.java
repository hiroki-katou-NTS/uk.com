package nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestrictionCommand;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.WorkLocationReposiroty;

@Stateless
public class StampingSettingEmplFindOneQuery {
	@Inject
	private WorkLocationReposiroty workLocationReposiroty;  
	
	public StampingAreaRestrictionCommand getStatuEmployee(String employId) {
		Optional<StampingAreaRestriction> domain = workLocationReposiroty.findByEmployeeId(employId);
		StampingAreaRestrictionCommand command = domain.map(t -> new StampingAreaRestrictionCommand(employId, t.getStampingAreaLimit().value, t.getUseLocationInformation().value))
														.orElse(null);
		return command;
	}
}
