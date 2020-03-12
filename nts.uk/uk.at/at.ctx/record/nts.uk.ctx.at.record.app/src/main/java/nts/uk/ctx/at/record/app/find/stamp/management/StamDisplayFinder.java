package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StamDisplayFinder {

	@Inject
	private StampSetPerRepository repo;
	
	public StampSettingPersonDto getStampSet(){
		String companyId = AppContexts.user().companyId();
		Optional<StampSettingPersonDto> stampSet = repo.getStampSet(companyId).map(x->StampSettingPersonDto.fromDomain(x));
		if(!stampSet.isPresent())
		return null;
		
	return stampSet.get();
	}
	
	public StampPageLayoutDto getStampPage(int pageNo){
		String companyId = AppContexts.user().companyId();
		Optional<StampPageLayoutDto> stampPage = repo.getStampSetPage(companyId, pageNo).map(mapper->StampPageLayoutDto.fromDomain(mapper));
		if(!stampPage.isPresent())
			return null;
			
	return stampPage.get();
	}
	
	public StampPageLayoutDto getStampPageByCid(){
		String companyId = AppContexts.user().companyId();
		Optional<StampPageLayoutDto> stampPage = repo.getStampSetPageByCid(companyId).map(mapper->StampPageLayoutDto.fromDomain(mapper));
		if(!stampPage.isPresent())
			return null;
			
	return stampPage.get();
	}
	
	public List<StampPageLayoutDto> getAllStampPage(){
		String companyId = AppContexts.user().companyId();
		List<StampPageLayoutDto> stampPage = repo.getAllStampSetPage(companyId).stream().map(mapper->StampPageLayoutDto.fromDomain(mapper)).collect(Collectors.toList());
		return stampPage;
	}
}
