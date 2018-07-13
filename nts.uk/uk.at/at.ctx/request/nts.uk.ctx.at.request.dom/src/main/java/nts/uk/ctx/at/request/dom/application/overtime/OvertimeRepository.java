package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface OvertimeRepository {
	/**
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppOverTime> getAppOvertime(String companyID, String appID);
	
	/**
	 * ドメインモデル「残業申請」の登録処理を実行する(INSERT)
	 * @param domain : 残業申請
	 */
	public void Add(AppOverTime domain);
	
	public Optional<AppOverTime> getFullAppOvertime(String companyID, String appID);
	
	public void update(AppOverTime appOverTime); 
	
	public void delete(String companyID, String appID);
	
	public Optional<AppOverTime> getAppOvertimeByDate(GeneralDate appDate, String employeeID, OverTimeAtr overTimeAtr);
	/**
	 * get Application Over Time and Frame
	 * @author hoatt 
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppOverTime> getAppOvertimeFrame(String companyID, String appID);
	/**
	 * get list Application Over Time and Frame
	 * @author hoatt 
	 * @param companyID
	 * @param lstAppID
	 * @return map: key - appID, value - AppOverTime
	 */
	public Map<String, AppOverTime> getListAppOvertimeFrame(String companyID, List<String> lstAppID);
}
