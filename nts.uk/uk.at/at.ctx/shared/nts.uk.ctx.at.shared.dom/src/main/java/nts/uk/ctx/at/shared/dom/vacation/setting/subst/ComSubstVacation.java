/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompanyCompensatoryLeave.
 */
// 振休管理設定
@Getter
public class ComSubstVacation extends DomainObject {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The setting. */
	// 振休取得・使用方法
	private SubstVacationSetting setting;
	//管理区分 
	private ManageDistinct manageDistinct;
	
	//紐付け管理区分
	private ManageDistinct  linkingManagementATR;
	
	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.setting.getIsManage().equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new company compensatory leave.
	 *
	 * @param companyId
	 *            the company id
	 * @param setting
	 *            the setting
	 */
	public ComSubstVacation(String companyId, SubstVacationSetting setting ,ManageDistinct manageDistinct ,ManageDistinct linkingManagementATR ) {
		super();
		this.companyId = companyId;
		this.setting = setting;
		this.manageDistinct = manageDistinct;
		this.linkingManagementATR = linkingManagementATR;
		
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComSubstVacation(ComSubstVacationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.setting = memento.getSetting();
		this.manageDistinct = memento.getManageDistinct();
		this.linkingManagementATR = memento.getLinkingManagementATR();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComSubstVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSetting(this.setting);
		memento.setManageDistinct(this.manageDistinct);
		memento.setLinkingManagementATR(this.linkingManagementATR);
	}

}
