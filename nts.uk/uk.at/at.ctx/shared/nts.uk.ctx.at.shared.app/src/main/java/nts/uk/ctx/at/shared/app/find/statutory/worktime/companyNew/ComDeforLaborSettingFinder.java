package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComDeforLaborSettingFinder {
	
	@Inject
	private ComDeforLaborSettingRepository repository;
	
	public ComDeforLaborSettingDto find(int year) {

		String companyId = AppContexts.user().companyId();

		Optional<ComDeforLaborSetting> optComDeforLaborSet = this.repository.find(companyId, year);
		ComDeforLaborSettingDto dto = new ComDeforLaborSettingDto();
		
		if(optComDeforLaborSet.isPresent()) {
			dto = ComDeforLaborSettingDto.fromDomain(optComDeforLaborSet.get());
		}
		return dto;
		
	}

}
