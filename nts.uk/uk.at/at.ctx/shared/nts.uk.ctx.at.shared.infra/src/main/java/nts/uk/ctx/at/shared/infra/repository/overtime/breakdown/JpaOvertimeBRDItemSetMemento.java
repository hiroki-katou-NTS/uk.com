/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.breakdown;

import nts.uk.ctx.at.shared.dom.overtime.ProductNumber;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrd;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrdPK;

/**
 * The Class JpaOvertimeBRDItemSetMemento.
 */
public class JpaOvertimeBRDItemSetMemento implements OvertimeBRDItemSetMemento {

	/** The entity. */
	private KshstOverTimeBrd entity;

	/**
	 * Instantiates a new jpa overtime BRD item set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeBRDItemSetMemento(KshstOverTimeBrd entity, String companyId) {
		if (entity.getKshstOverTimeBrdPK() == null) {
			entity.setKshstOverTimeBrdPK(new KshstOverTimeBrdPK());
		}
		this.entity = entity;
		this.entity.getKshstOverTimeBrdPK().setCid(companyId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setUseClassification(nts.uk.ctx.at.shared.dom.overtime.UseClassification)
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.entity.setUseAtr(useClassification.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setBreakdownItemNo(nts.uk.ctx.at.shared.dom.overtime.breakdown.
	 * BreakdownItemNo)
	 */
	@Override
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo) {
		this.entity.getKshstOverTimeBrdPK().setBrdItemNo(breakdownItemNo.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setName(nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName)
	 */
	@Override
	public void setName(BreakdownItemName name) {
		this.entity.setName(name.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setProductNumber(nts.uk.ctx.at.shared.dom.overtime.ProductNumber)
	 */
	@Override
	public void setProductNumber(ProductNumber productNumber) {
		this.entity.setProductNumber(productNumber.value);
	}

}
