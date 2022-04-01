package nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository;

@Stateless
public class StampingSettingEmplFindOneQuery {
	@Inject
	private StampingAreaRepository stampingAreaReposiroty;

	public StampingAreaRestrictionDto getStatuEmployee(String employId) {
		Optional<EmployeeStampingAreaRestrictionSetting> domain = stampingAreaReposiroty.findByEmployeeId(employId);
		StampingAreaRestrictionDto dto = domain
				.map(t -> StampingAreaRestrictionDto.toDto(t))
				.orElse(null);
		return dto;
	}
}
