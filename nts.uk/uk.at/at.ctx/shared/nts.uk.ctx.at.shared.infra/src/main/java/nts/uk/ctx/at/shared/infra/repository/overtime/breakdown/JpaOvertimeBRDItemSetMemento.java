/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.breakdown;

import java.util.List;

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
	
	/** The entity atens. */
	private List<Integer> entityAtens;

	/**
	 * Instantiates a new jpa overtime BRD item set memento.
	 *
	 * @param entity the entity
	 * @param entityAtens the entity atens
	 * @param companyId the company id
	 */
	public JpaOvertimeBRDItemSetMemento(KshstOverTimeBrd entity,
			List<Integer> entityAtens, String companyId) {
		if (entity.getKshstOverTimeBrdPK() == null) {
			entity.setKshstOverTimeBrdPK(new KshstOverTimeBrdPK());
		}
		this.entityAtens = entityAtens;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setAttendanceItemIds(java.util.List)
	 */
	@Override
	public void setAttendanceItemIds(List<Integer> attendanceItemIds) {
		this.entityAtens = attendanceItemIds;
	}

}
