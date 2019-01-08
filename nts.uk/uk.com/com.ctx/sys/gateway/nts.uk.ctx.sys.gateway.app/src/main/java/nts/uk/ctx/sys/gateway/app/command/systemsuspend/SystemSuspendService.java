package nts.uk.ctx.sys.gateway.app.command.systemsuspend;

public interface SystemSuspendService {
	
	/**
	 * システム利用停止の確認
	 * @param contractCD
	 * @param companyCD
	 * @param loginMethod
	 * @param programID
	 * @param screenID
	 * @return
	 */
	public SystemSuspendOutput confirmSystemSuspend(String contractCD, String companyCD, int loginMethod, String programID, String screenID);
	
	/**
	 * システム利用停止の確認_ログイン前
	 * @param contractCD
	 * @param companyCD
	 * @param loginMethod
	 * @param programID
	 * @param screenID
	 * @return
	 */
	public SystemSuspendOutput confirmSystemSuspend_BefLog(String contractCD, String companyCD, int loginMethod, String programID, String screenID);
}
