package nts.uk.shr.com.operation;

public interface SystemOperationSettingAdapter {

	public SystemOperationSetting getSetting();
	
	/**
	 * システム停止中の確認
	 * @return
	 * if stopping - return Optional with value
	 * else return empty optional
	 */
	public SystemSuspendOut stopUseConfirm(String contractCD, String companyCD, int loginMethod, String programID, String screenID);
	
}
