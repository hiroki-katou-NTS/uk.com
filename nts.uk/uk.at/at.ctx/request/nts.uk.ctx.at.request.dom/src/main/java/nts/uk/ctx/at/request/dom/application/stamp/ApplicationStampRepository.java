package nts.uk.ctx.at.request.dom.application.stamp;

public interface ApplicationStampRepository {
	
	public void addStampGoOutPermit();
	
	public void addStampWork();
	
	public void addStampCancel();
	
	public void addStampOnlineRecord();
	
	public void updateStampGoOutPermit();
	
	public void updateStampWork();
	
	public void updateStampCancel();
	
	public void updateStampOnlineRecord();
	
	public void deleteStampGoOutPermit();
	
	public void deleteStampWork();
	
	public void deleteStampCancel();
	
	public void deleteStampOnlineRecord();
	
}
