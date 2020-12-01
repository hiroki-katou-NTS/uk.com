/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class SubstVacationSetting.
 */
@Getter
public class SubstVacationSetting extends DomainObject {

	
	private ManageDeadline manageDeadline;

	/** The expiration date. */
	// 休暇使用期限
	private ExpirationTime expirationDate;

	/** The allow prepaid leave. */
	// 先取り許可
	private ApplyPermission allowPrepaidLeave;
	


	/**
	 * Instantiates a new subst vacation setting.
	 *
	 * @param isManage
	 *            the is manage
	 * @param expirationDate
	 *            the expiration date
	 * @param allowPrepaidLeave
	 *            the allow prepaid leave
	 */
	public SubstVacationSetting(ManageDeadline manageDeadline, ExpirationTime expirationDate,
			ApplyPermission allowPrepaidLeave ) {
		super();
		this.manageDeadline = manageDeadline;
		this.expirationDate = expirationDate;
		this.allowPrepaidLeave = allowPrepaidLeave;
	
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new subst vacation setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public SubstVacationSetting(SubstVacationSettingGetMemento memento) {
		
		this.manageDeadline = memento.manageDeadline();
		this.expirationDate = memento.getExpirationDate();
		this.allowPrepaidLeave = memento.getAllowPrepaidLeave();
	
		
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SubstVacationSettingSetMemento memento ) {
		memento.setManageDeadline(this.manageDeadline);
		memento.setExpirationDate(this.expirationDate);
		memento.setAllowPrepaidLeave(this.allowPrepaidLeave);
		
		
	}

}
