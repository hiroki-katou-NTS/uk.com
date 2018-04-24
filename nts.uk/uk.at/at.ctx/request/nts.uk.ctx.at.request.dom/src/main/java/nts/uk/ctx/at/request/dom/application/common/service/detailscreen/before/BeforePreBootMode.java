package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
/**
 * 14-2.詳細画面起動前モードの判断
 * @author dudt
 *
 */
public interface BeforePreBootMode {
	
	/**
	 * 詳細画面起動前モードの判断
	 * @param companyID
	 * @param employeeID login
	 * @param appID
	 * @param baseDate
	 * @return
	 */
	public DetailedScreenPreBootModeOutput judgmentDetailScreenMode(String companyID, String employeeID, String appID, GeneralDate baseDate);
	
}