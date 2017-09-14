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
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrd;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrdPK;

/**
 * The Class JpaOvertimeBRDItemGetMemento.
 */
public class JpaOvertimeBRDItemGetMemento implements OvertimeBRDItemGetMemento {
	
	/** The entity. */
	private KshstOverTimeBrd entity;
	
	/** The entity atens. */
	private List<Integer> entityAtens;
	
	/**
	 * Instantiates a new jpa overtime BRD item get memento.
	 *
	 * @param entity the entity
	 * @param entityAtens the entity atens
	 */
	public JpaOvertimeBRDItemGetMemento(KshstOverTimeBrd entity,
			List<Integer> entityAtens) {
		if (entity.getKshstOverTimeBrdPK() == null) {
			entity.setKshstOverTimeBrdPK(new KshstOverTimeBrdPK());
		}
		this.entityAtens = entityAtens;
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getUseClassification()
	 */
	@Override
	public UseClassification getUseClassification() {
		return UseClassification.valueOf(this.entity.getUseAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getBreakdownItemNo()
	 */
	@Override
	public BreakdownItemNo getBreakdownItemNo() {
		return BreakdownItemNo.valueOf(this.entity.getKshstOverTimeBrdPK().getBrdItemNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getName()
	 */
	@Override
	public BreakdownItemName getName() {
		return new BreakdownItemName(this.entity.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getProductNumber()
	 */
	@Override
	public ProductNumber getProductNumber() {
		return ProductNumber.valueOf(this.entity.getProductNumber());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getAttendanceItemIds()
	 */
	@Override
	public List<Integer> getAttendanceItemIds() {
		return this.entityAtens;
	}

}
