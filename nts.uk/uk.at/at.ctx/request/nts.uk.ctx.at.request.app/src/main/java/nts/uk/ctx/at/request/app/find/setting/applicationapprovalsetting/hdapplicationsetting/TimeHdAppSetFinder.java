package nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetRepository;

@Stateless
public class TimeHdAppSetFinder {
	@Inject 
	private TimeHdAppSetRepository timeRep;
	public TimeHdAppSetDto findByCid(){
		Optional<TimeHdAppSet> time = timeRep.getByCid();
		if(time.isPresent()){
			return TimeHdAppSetDto.convertToDto(time.get());
		}
		return null;
	}
}
