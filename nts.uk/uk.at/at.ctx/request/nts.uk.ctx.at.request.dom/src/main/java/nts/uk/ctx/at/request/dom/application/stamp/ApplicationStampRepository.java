package nts.uk.ctx.at.request.dom.application.stamp;

public interface ApplicationStampRepository {
	
	public void addStampGoOutPermit(ApplicationStamp applicationStamp);
	
	public void addStampWork(ApplicationStamp applicationStamp);
	
	public void addStampCancel(ApplicationStamp applicationStamp);
	
	public void addStampOnlineRecord(ApplicationStamp applicationStamp);
	
	public void updateStampGoOutPermit(ApplicationStamp applicationStamp);
	
	public void updateStampWork(ApplicationStamp applicationStamp);
	
	public void updateStampCancel(ApplicationStamp applicationStamp);
	
	public void updateStampOnlineRecord(ApplicationStamp applicationStamp);
	
	public void deleteStampGoOutPermit(ApplicationStamp applicationStamp);
	
	public void deleteStampWork(ApplicationStamp applicationStamp);
	
	public void deleteStampCancel(ApplicationStamp applicationStamp);
	
	public void deleteStampOnlineRecord(ApplicationStamp applicationStamp);
	
}
