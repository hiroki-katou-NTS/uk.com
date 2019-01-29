/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompensatoryLeaveEmSetting.
 */
@Getter
public class CompensatoryLeaveEmSetting extends AggregateRoot {

	// 会社ID
	/** The company id. */
	private String companyId;
	
	// 雇用区分コード
	// 雇用区分コード
	/** The employment code. */
	private EmploymentCode employmentCode;
	
	// 管理区分
	/** The is managed. */
	private ManageDistinct isManaged;
	
	// 設定
	/** The compensatory acquisition use. */
	private CompensatoryAcquisitionUse compensatoryAcquisitionUse;
	
	// 時間代休の消化単位
	/** The compensatory digestive time unit. */
	private CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit;

	/**
	 * Instantiates a new compensatory leave em setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompensatoryLeaveEmSetting(CompensatoryLeaveEmSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.isManaged = memento.getIsManaged();
		this.compensatoryAcquisitionUse = memento.getCompensatoryAcquisitionUse();
		this.compensatoryDigestiveTimeUnit = memento.getCompensatoryDigestiveTimeUnit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompensatoryLeaveEmSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setIsManaged(this.isManaged);
		memento.setCompensatoryAcquisitionUse(this.compensatoryAcquisitionUse);
		memento.setCompensatoryDigestiveTimeUnit(this.compensatoryDigestiveTimeUnit);
	}
}
