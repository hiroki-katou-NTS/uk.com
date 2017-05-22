/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.acquisitionrule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.Money;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class VacationAcquisitionRule extends DomainObject {
	
	
	/** The company id. */
	private String companyId;
	
	/** The setting classification. */
	@Setter
	private Settingclassification settingClassification;
	
	/** The acquisition order. */
	@Setter
	private List<VacationAcquisitionOrder> acquisitionOrder;
	
	
	/**
	 * Instantiates a new vacation acquisition rule.
	 *
	 * @param memento the memento
	 */
	public VacationAcquisitionRule(VaAcRuleGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.settingClassification = memento.getSettingclassification();
		this.acquisitionOrder = memento.getAcquisitionOrder();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(VaAcRuleSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSettingclassification(this.settingClassification);
		memento.setAcquisitionOrder(this.acquisitionOrder);
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VacationAcquisitionRule other = (VacationAcquisitionRule) obj;		
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}


}
