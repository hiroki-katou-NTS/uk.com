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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItemSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetail;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetailPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshmtOutsideAtd;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshmtOutsideAtdPK;

/**
 * The Class JpaOutsideOTBRDItemSetMemento.
 */
public class JpaOutsideOTBRDItemSetMemento implements OutsideOTBRDItemSetMemento {

	/** The entity. */
	private KshmtOutsideDetail entity;
	
	/**
	 * Instantiates a new jpa overtime BRD item set memento.
	 *
	 * @param entity the entity
	 * @param entityAtens the entity atens
	 * @param companyId the company id
	 */
	public JpaOutsideOTBRDItemSetMemento(KshmtOutsideDetail entity, String companyId) {
		if (entity.getKshmtOutsideDetailPK() == null) {
			entity.setKshmtOutsideDetailPK(new KshmtOutsideDetailPK());
		}
		this.entity = entity;
		this.entity.getKshmtOutsideDetailPK().setCid(companyId);
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
		this.entity.getKshmtOutsideDetailPK().setBrdItemNo(breakdownItemNo.value);
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
		this.entity.setLstOutsideOtBrdAten(attendanceItemIds.stream().map(id->{
			KshmtOutsideAtd entityAten = new KshmtOutsideAtd();
			entityAten.setKshmtOutsideAtdPK(
					new KshmtOutsideAtdPK(this.entity.getKshmtOutsideDetailPK().getCid(),
							this.entity.getKshmtOutsideDetailPK().getBrdItemNo(), id));
			return entityAten;
		}).collect(Collectors.toList()));
	}

}
