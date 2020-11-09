package nts.uk.ctx.at.request.dom.application.overtime;

public interface AppOverTimeRepository {
	
	public AppOverTime find(String companyId, String appId);
	
	public void add();
	
}
