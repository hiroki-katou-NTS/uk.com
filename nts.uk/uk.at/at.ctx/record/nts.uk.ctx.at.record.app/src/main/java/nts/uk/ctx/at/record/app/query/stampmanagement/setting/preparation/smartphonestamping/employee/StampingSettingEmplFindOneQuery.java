package nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaReposiroty;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;

@Stateless
public class StampingSettingEmplFindOneQuery {
	@Inject
	private StampingAreaReposiroty stampingAreaReposiroty;  
	
	public StampingAreaRestrictionDto getStatuEmployee(String employId) {
		Optional<StampingAreaRestriction> domain = stampingAreaReposiroty.findByEmployeeId(employId);
		StampingAreaRestrictionDto dto = domain.map(t -> new StampingAreaRestrictionDto(employId, t.getStampingAreaLimit().value, t.getUseLocationInformation().value))
														.orElse(null);
		return dto;
	}
}
