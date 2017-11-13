package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;

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
	public WorkTypeOvertime getWorkType(String companyID,String employeeID, Optional<PersonalLaborCondition> personalLablorCodition, Optional<RequestAppDetailSetting> requestAppDetailSetting);
	
	/**
	 * 08_就業時間帯取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param requestAppDetailSetting
	 * @return
	 */
	public SiftType getSiftType(String companyID,String employeeID,Optional<PersonalLaborCondition> personalLablorCodition,Optional<RequestAppDetailSetting> requestAppDetailSetting);
	
	
	void CreateOvertime(AppOverTime domain);
}
