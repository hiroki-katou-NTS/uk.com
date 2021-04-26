package nts.uk.screen.at.ws.kdw.kdw013;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.kdw013.a.EmployeeDisplayInfo;
import nts.uk.screen.at.app.kdw013.a.GetEmployeeDisplayInfo;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.日付を変更する
 * @author tutt
 *
 */
@Stateless
public class ChangeDate {
	
	@Inject
	private GetEmployeeDisplayInfo empDisplayInfo;
	
	public ChangeDateDto changeDate(String sid, GeneralDate refDate, DateRange period) {
		EmployeeDisplayInfo employeeDisplayInfo = empDisplayInfo.getInfo(sid, refDate, period);
		
		return null;
	}

}
