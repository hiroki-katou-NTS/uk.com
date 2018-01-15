package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

public interface OvertimeService {
	/**
	 * 02_残業区分チェック 
	 * @param url
	 * @return
	 */
	public int checkOvertime(String url);
	
	/**
	 * 07_勤務種類取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param requestAppDetailSetting
	 * @return
	 */
	public List<WorkTypeOvertime> getWorkType(String companyID,String employeeID,RequestAppDetailSetting requestAppDetailSetting,List<AppEmploymentSetting> appEmploymentSettings);
	
	/**
	 * 08_就業時間帯取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param requestAppDetailSetting
	 * @return
	 */
	public List<SiftType> getSiftType(String companyID,String employeeID,RequestAppDetailSetting requestAppDetailSetting);
	
	/**
	 * 09_勤務種類就業時間帯の初期選択をセットする
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @param workTypes
	 * @param siftTypes
	 * @return
	 */
	public WorkTypeAndSiftType getWorkTypeAndSiftTypeByPersonCon(String companyID,String employeeID,GeneralDate baseDate,List<WorkTypeOvertime> workTypes, List<SiftType> siftTypes);
	
	
	void CreateOvertime(AppOverTime domain, Application_New newApp);
}
