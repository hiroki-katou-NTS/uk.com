package nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;

@Stateless
public class HdAppSetFinder {
	@Inject
	private HdAppSetRepository hdRep;
	
	public HdAppSetDto findByApp(){
		Optional<HdAppSet> hdAppSet = hdRep.getAll();
		if(hdAppSet.isPresent()){
			return HdAppSetDto.convertToDto(hdAppSet.get());
		}
		return null;
	}
}
