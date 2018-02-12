package nts.uk.ctx.pereg.dom.common;

public interface WorkTimeSettingRepo {

	boolean isFlowWork(String workTimeCode);
	
	String getWorkTimeSettingName(String companyId, String worktimeCode);
	
}
