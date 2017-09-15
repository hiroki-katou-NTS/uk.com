package nts.uk.ctx.at.request.app.find.application.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStamp;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampRepository;

@Stateless
public class AppStampFinder {
	
	@Inject
	private ApplicationStampRepository applicationStampRepository;
	
	public void findByAppID(){
		ApplicationStamp applicationStamp = this.applicationStampRepository.findByAppID("000000000000-0001", "9c5a9894-faf8-4f54-87b8-0836b0fe3e47");
		System.out.println(applicationStamp);
	}
	
	public AppStampDto findStampGoOutPermit(){
		return null;
	}
	
	public AppStampDto findStampWork(){
		return null;
	}
	
	public AppStampDto findStampCancel(){
		return null;
	}
	
	public AppStampDto findStampOnlineRecord(){
		return null;
	}
	
}
