/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;

/**
 * The Class AcquisitionRule.
 */
//休暇の取得ルール
@Getter
@EqualsAndHashCode(callSuper = false, of = { "companyId" })
public class AcquisitionRule extends DomainObject {

	/** The company id. */
	private String companyId;

	/** The setting classification. --- 取得する順番をチェックする */
	@Setter
	private SettingDistinct category;
	
	/**年休より優先する休暇*/
	@Setter
	private AnnualHoliday annualHoliday;
	
	

	/**
	 * Instantiates a new vacation acquisition rule.
	 *
	 * @param memento
	 *            the memento
	 */
	public AcquisitionRule(AcquisitionRuleGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.category = memento.getCategory();
		this.annualHoliday = memento.getAnnualHoliday();
	

	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AcquisitionRuleSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setCategory(this.category);
		memento.setAnnualHoliday(this.annualHoliday);
	
	}
}
