package nts.uk.ctx.at.schedule.pub.budget.premium;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface PremiumItemPub {

	public List<PremiumItemDto> findByCompanyID(String companyID);
	
	public List<PremiumItemDto> findByCompanyIDAndDisplayNumber(String companyID, List<Integer> displayNumbers);
	
	/**
	 * RequestList70
	 * 人件費設定を取得する
	 * @param companyID
	 * @param date
	 * @return
	 */
	public List<PersonCostSettingExport> getPersonCostSetting(String companyID,GeneralDate date);
	
	public List<PersonCostSettingExport> getPersonCostSetting(String companyID, DatePeriod period);
	
}
