package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComFlexSettingFinder {

	@Inject
	private ComFlexSettingRepository repository;
	
	public ComFlexSettingDto find (int year) {
		String companyId = AppContexts.user().companyId();
		
		Optional<ComFlexSetting> optComFlexSet = this.repository.find(companyId, year);
		ComFlexSettingDto dto = new ComFlexSettingDto();
		
		if(optComFlexSet.isPresent()) {
			dto = ComFlexSettingDto.fromDomain(optComFlexSet.get());
		}
		return dto;
	}

}
