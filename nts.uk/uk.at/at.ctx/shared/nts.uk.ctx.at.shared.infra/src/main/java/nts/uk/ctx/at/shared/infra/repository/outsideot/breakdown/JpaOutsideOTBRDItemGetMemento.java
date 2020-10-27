/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItemGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetail;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetailPK;

/**
 * The Class JpaOutsideOTBRDItemGetMemento.
 */
public class JpaOutsideOTBRDItemGetMemento implements OutsideOTBRDItemGetMemento {
	
	/** The entity. */
	private KshmtOutsideDetail entity;
	
	/**
	 * Instantiates a new jpa overtime BRD item get memento.
	 *
	 * @param entity the entity
	 * @param entityAtens the entity atens
	 */
	public JpaOutsideOTBRDItemGetMemento(KshmtOutsideDetail entity) {
		if (entity.getKshmtOutsideDetailPK() == null) {
			entity.setKshmtOutsideDetailPK(new KshmtOutsideDetailPK());
		}
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
		return BreakdownItemNo.valueOf(this.entity.getKshmtOutsideDetailPK().getBrdItemNo());
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
		return this.entity.getLstOutsideOtBrdAten().stream()
				.map(entity -> entity.getKshmtOutsideAtdPK().getAttendanceItemId())
				.collect(Collectors.toList());
	}

}
