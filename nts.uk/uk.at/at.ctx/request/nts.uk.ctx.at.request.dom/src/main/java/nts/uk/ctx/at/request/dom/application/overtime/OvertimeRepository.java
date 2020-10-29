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
	public Optional<AppOverTime_Old> getAppOvertime(String companyID, String appID);
	
	/**
	 * ドメインモデル「残業申請」の登録処理を実行する(INSERT)
	 * @param domain : 残業申請
	 */
	public void Add(AppOverTime_Old domain);
	
	public Optional<AppOverTime_Old> getFullAppOvertime(String companyID, String appID);
	
	public void update(AppOverTime_Old appOverTime); 
	
	public void delete(String companyID, String appID);
	
	public Optional<AppOverTime_Old> getAppOvertimeByDate(GeneralDate appDate, String employeeID, OverTimeAtr overTimeAtr);
	/**
	 * get Application Over Time and Frame
	 * @author hoatt 
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppOverTime_Old> getAppOvertimeFrame(String companyID, String appID);
	/**
	 * get list Application Over Time and Frame
	 * @author hoatt 
	 * @param companyID
	 * @param lstAppID
	 * @return map: key - appID, value - AppOverTime
	 */
	public Map<String, AppOverTime_Old> getListAppOvertimeFrame(String companyID, List<String> lstAppID);
}
