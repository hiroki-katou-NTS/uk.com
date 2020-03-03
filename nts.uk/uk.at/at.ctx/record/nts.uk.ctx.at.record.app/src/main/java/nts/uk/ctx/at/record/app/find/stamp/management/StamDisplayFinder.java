package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StamDisplayFinder {

	@Inject
	private StampSetPerRepository repo;
	
	public Optional<StampSettingPersonDto> getStampSet(){
		String companyId = AppContexts.user().companyId();
		Optional<StampSettingPersonDto> stampSet = repo.getStampSet(companyId).map(x->StampSettingPersonDto.fromDomain(x));
		return stampSet;
	}
}
