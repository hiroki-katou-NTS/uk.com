package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FactoryApplicationImpl implements IFactoryApplication{

	@Override
	public Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr,
			String appReasonID, String applicationReason, ApplicationType appType,GeneralDate startDate,GeneralDate endDate,String employeeID) {
//		// 会社ID
//		String companyId = AppContexts.user().companyId();
//		// 申請者
//		String applicantSID = AppContexts.user().employeeId();
//
//		Application_New app = new Application_New(0L, companyId, appID,
//				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), GeneralDateTime.now(), applicantSID,
//				new AppReason(Strings.EMPTY), applicationDate, new AppReason(applicationReason),
//				appType, employeeID, Optional.of(startDate),
//				Optional.ofNullable(endDate), ReflectionInformation_New.firstCreate());
//		return app;
		return null;
	}
	
}
