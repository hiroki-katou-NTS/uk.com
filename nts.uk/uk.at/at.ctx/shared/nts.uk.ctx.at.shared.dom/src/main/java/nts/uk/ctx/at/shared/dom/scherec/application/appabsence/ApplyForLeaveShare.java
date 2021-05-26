package nts.uk.ctx.at.shared.dom.scherec.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;

/**
 * 
 * @author thanhnx
 * 
 *         休暇申請(反映用)
 * 
 */

@Getter
@AllArgsConstructor
public class ApplyForLeaveShare extends ApplicationShare implements DomainAggregate {

	// 休暇申請反映情報
	private ReflectFreeTimeAppShare reflectFreeTimeApp;

	public ApplyForLeaveShare(ApplicationShare application, ReflectFreeTimeAppShare reflectFreeTimeApp) {
		super(application);
		this.reflectFreeTimeApp = reflectFreeTimeApp;
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
		this.setOpStampRequestMode(application.getOpStampRequestMode());
		this.setOpAppEndDate(application.getOpAppEndDate());
	}
}
