package nts.uk.ctx.at.request.app.find.application.triprequestsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSetRepository;

@Stateless
public class TripRequestSetFinder {
	@Inject 
	private TripRequestSetRepository tripRep;
	
	public TripRequestSetDto findByCid(){
		Optional<TripRequestSet> trip = tripRep.findByCid();
		if(trip.isPresent()){
			return TripRequestSetDto.convertToDto(trip.get());
		}
		return null;
	}
}
