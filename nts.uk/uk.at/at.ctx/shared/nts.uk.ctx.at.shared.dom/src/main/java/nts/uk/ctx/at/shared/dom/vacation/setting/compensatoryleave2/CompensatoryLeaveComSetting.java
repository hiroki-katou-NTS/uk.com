/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompensatoryLeaveComSetting.
 */
@Getter
public class CompensatoryLeaveComSetting extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The is managed. */
	private ManageDistinct isManaged;
	
	/** The normal vacation setting. */
	private CompensatoryAcquisitionUse compensatoryAcquisitionUse;
	
	/** The compensatory digestive time unit. */
	private CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit;
	
	/** The occurrence vacation setting. */
	private List<CompensatoryOccurrenceSetting> compensatoryOccurrenceSetting;
	
	/**
	 * Instantiates a new compensatory leave com setting.
	 *
	 * @param memento the memento
	 */
	public CompensatoryLeaveComSetting(CompensatoryLeaveComGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.isManaged = memento.getIsManaged();
		this.compensatoryAcquisitionUse = memento.getCompensatoryAcquisitionUse();
		this.compensatoryDigestiveTimeUnit = memento.getCompensatoryDigestiveTimeUnit();
		this.compensatoryOccurrenceSetting = memento.getCompensatoryOccurrenceSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompensatoryLeaveComSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setIsManaged(this.isManaged);
		memento.setCompensatoryAcquisitionUse(this.compensatoryAcquisitionUse);
		memento.setCompensatoryDigestiveTimeUnit(this.compensatoryDigestiveTimeUnit);
		memento.setCompensatoryOccurrenceSetting(this.compensatoryOccurrenceSetting);
	}
}
