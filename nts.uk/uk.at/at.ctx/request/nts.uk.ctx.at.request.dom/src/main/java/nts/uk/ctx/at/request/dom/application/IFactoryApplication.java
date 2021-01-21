package nts.uk.ctx.at.request.dom.application;

import nts.arc.time.GeneralDate;

public interface IFactoryApplication {
	// 申請
	Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
					String applicationReason,ApplicationType appType,GeneralDate startDate,GeneralDate endDate,String employeeID);
}
