package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComNormalSettingFinder {
	
	@Inject
	private ComNormalSettingRepository repository;

	public ComNormalSettingDto find (int year) {
		String companyId = AppContexts.user().companyId();
		
		Optional<ComNormalSetting> optComNormalSet = this.repository.find(companyId, year);
		ComNormalSettingDto dto = new ComNormalSettingDto();
		
		if(optComNormalSet.isPresent()) {
			dto = ComNormalSettingDto.fromDomain(optComNormalSet.get());
		}
		return dto;
	}

}
