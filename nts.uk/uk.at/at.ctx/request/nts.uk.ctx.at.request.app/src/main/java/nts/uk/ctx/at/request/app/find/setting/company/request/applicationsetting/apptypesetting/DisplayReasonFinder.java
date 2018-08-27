package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DisplayReasonFinder {
	
	@Inject
	private DisplayReasonRepository displayRep;
	
	public List<DisplayReasonDto> findByCom(){
		String companyId = AppContexts.user().companyId();
		List<DisplayReason> displayGet = displayRep.findDisplayReason(companyId);
		if(displayGet.isEmpty()){
			return new ArrayList<>();
		}
		return displayGet.stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
	} 
}
