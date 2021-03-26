package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;

/**
 * 
 * @author thanhnx
 *         UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請
 *         休暇申請
 * 
 */

@Getter
@AllArgsConstructor
public class ApplyForLeaveShare extends ApplicationShare implements DomainAggregate {

	// 休暇申請反映情報
	private ReflectFreeTimeAppShare reflectFreeTimeApp;

	// 休暇申請画面描画情報
	private VacationRequestInfoShare vacationInfo;

	public ApplyForLeaveShare(ApplicationShare application, ReflectFreeTimeAppShare reflectFreeTimeApp,
			VacationRequestInfoShare vacationInfo) {
		super(application);
		this.reflectFreeTimeApp = reflectFreeTimeApp;
		this.vacationInfo = vacationInfo;
	}

	public void setApplication(ApplicationShare application) {
		this.setVersion(application.getVersion());
		this.setAppID(application.getAppID());
		this.setPrePostAtr(application.getPrePostAtr());
		this.setEmployeeID(application.getEmployeeID());
		this.setAppType(application.getAppType());
		this.setAppDate(application.getAppDate());
		this.setEnteredPersonID(application.getEnteredPersonID());
		this.setInputDate(application.getInputDate());
		this.setReflectionStatus(application.getReflectionStatus());
		this.setOpStampRequestMode(application.getOpStampRequestMode());
		this.setOpReversionReason(application.getOpReversionReason());
		this.setOpAppStartDate(application.getOpAppStartDate());
		this.setOpAppEndDate(application.getOpAppEndDate());
		this.setOpAppReason(application.getOpAppReason());
		this.setOpAppStandardReasonCD(application.getOpAppStandardReasonCD());
	}
}
