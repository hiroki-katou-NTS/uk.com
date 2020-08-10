package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
//遅刻早退取消申請
public class ArrivedLateLeaveEarly_Old {
//	取消
	private List<LateCancelation> lateCancelation;
//	時刻報告
	private List<TimeReport> lateOrLeaveEarlies;
	
	public ArrivedLateLeaveEarly_Old(Long version, String companyID, String appID, PrePostAtr prePostAtr,
			GeneralDateTime inputDate, String enteredPersonID, AppReason reversionReason, GeneralDate appDate,
			AppReason appReason, ApplicationType appType, String employeeID, Optional<GeneralDate> startDate,
			Optional<GeneralDate> endDate, ReflectionInformation_New reflectionInformation) {
//		super(version, companyID, appID, 
//				EnumAdaptor.valueOf(prePostAtr.value, PrePostAtr.class), 
//				inputDate, enteredPersonID, reversionReason, appDate, appReason, 
//				EnumAdaptor.valueOf(appType.value, ApplicationType.class),
//				employeeID, startDate, endDate, reflectionInformation);
		
	}
	/*
	 * 申請内容
	 * */
	public String contentApplication() {
		return null;
	}

}
