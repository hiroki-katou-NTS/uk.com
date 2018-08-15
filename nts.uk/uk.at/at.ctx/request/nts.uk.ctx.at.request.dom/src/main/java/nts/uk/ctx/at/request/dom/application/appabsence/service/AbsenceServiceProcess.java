package nts.uk.ctx.at.request.dom.application.appabsence.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

public interface AbsenceServiceProcess {
	/**
	 * @param workTypeCode
	 * @return
	 */
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode);
	
	void CreateAbsence(AppAbsence domain, Application_New newApp);
	/**
	 * @author hoatt
	 * 13.計画年休上限チェック
	 * @param cID: 会社ID
	 * @param sID: 社員ID
	 * @param workTypeCD: 勤務種類コード
	 * @param sDate: 申請開始日
	 * @param eDate: 申請終了日
	 * @param hdAppType: 休暇種類
	 */
	void checkLimitAbsencePlan(String cID, String sID, String workTypeCD, 
			GeneralDate sDate, GeneralDate eDate, HolidayAppType hdAppType);
	/**
	 * @author hoatt
	 * 14.休暇種類表示チェック
	 * @param companyID
	 * @param sID
	 * @param baseDate
	 * @return
	 */
	CheckDispHolidayType checkDisplayAppHdType(String companyID, String sID, GeneralDate baseDate);
}
