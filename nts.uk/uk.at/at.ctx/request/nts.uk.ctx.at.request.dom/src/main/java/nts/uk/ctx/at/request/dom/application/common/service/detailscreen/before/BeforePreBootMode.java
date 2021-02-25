package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.14-2.詳細画面起動前モードの判断()
 * @author Doan Duy Hung
 *
 */
public interface BeforePreBootMode {
	
	/**
	 * 14-2.詳細画面起動前モードの判断
	 * @param companyID
	 * @param employeeID login
	 * @param application
	 * @param baseDate
	 * @return
	 */
	public DetailedScreenPreBootModeOutput judgmentDetailScreenMode(String companyID, String employeeID, Application application, GeneralDate baseDate);
	
}