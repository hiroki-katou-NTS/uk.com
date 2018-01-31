package nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;

@Stateless
public class HdAppSetFinder {
	@Inject
	private HdAppSetRepository hdRep;
	
	public List<HdAppSetDto> findByCom(){
		return hdRep.getAll().stream().map(c -> HdAppSetDto.convertToDto(c))
				.collect(Collectors.toList());
	}
	
	public HdAppSetDto findByApp(int hdApp){
		Optional<HdAppSet> hdAppSet = hdRep.getHdAppSet(hdApp);
		if(hdAppSet.isPresent()){
			return HdAppSetDto.convertToDto(hdAppSet.get());
		}
		return null;
	}
}
