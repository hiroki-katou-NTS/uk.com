package nts.uk.ctx.at.shared.dom.workrule.overtime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.BaseAutoCalSetting;

/**
 * 自動計算設定
 * @author nampt
 *
 */
public interface AutoCalculationSetService {

	public BaseAutoCalSetting getAutoCalculationSetting(String companyID, String employeeID, GeneralDate processingDate, String workPlaceId, String jobTitleId);
	
}
