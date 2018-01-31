package nts.uk.ctx.at.request.app.find.setting.company.displayname;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;

@Stateless
public class AppDispNameFinder {
	@Inject
	private AppDispNameRepository dispRep;
	
	public List<AppDispNameDto> findByCom(){
		return dispRep.getAll().stream().map(item -> AppDispNameDto.convertToDto(item)).collect(Collectors.toList());
	}
	
	public AppDispNameDto findByApp(int appType){
		Optional<AppDispName> app = dispRep.getDisplay(appType);
		if(app.isPresent()){
			return AppDispNameDto.convertToDto(app.get());
		}
		return null;
	}
}
