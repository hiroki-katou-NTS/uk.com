package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApplicationInsertCmd {
	
	private int prePostAtr;
	
	private List<String> employeeIDLst;
	
	private int appType;
	
	private String appDate;
	
	private String opAppReason;
	
	private Integer opAppStandardReasonCD;
	
	private String opAppStartDate;
	
	private String opAppEndDate;
	
	private Integer opStampRequestMode;
	
	public Application toDomain() {
		String loginID = AppContexts.user().employeeId();
		return Application.createFromNew(
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
				CollectionUtil.isEmpty(employeeIDLst) ? loginID : employeeIDLst.get(0), 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				new ApplicationDate(GeneralDate.fromString(appDate, "yyyy/MM/dd")), 
				loginID, 
				opStampRequestMode == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opStampRequestMode, StampRequestMode.class)), 
				Optional.empty(), 
				Strings.isBlank(opAppStartDate) ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(opAppStartDate, "yyyy/MM/dd"))), 
				Strings.isBlank(opAppEndDate) ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(opAppEndDate, "yyyy/MM/dd"))), 
				Strings.isBlank(opAppReason) ? Optional.empty() : Optional.of(new AppReason(opAppReason)), 
				opAppStandardReasonCD == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(opAppStandardReasonCD)));
	}
	   
}
