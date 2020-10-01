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
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrd;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrdPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOutsideOtBrdAten;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOutsideOtBrdAtenPK;

/**
 * The Class JpaOutsideOTBRDItemSetMemento.
 */
public class JpaOutsideOTBRDItemSetMemento implements OutsideOTBRDItemSetMemento {

	/** The entity. */
	private KshstOutsideOtBrd entity;
	
	/**
	 * Instantiates a new jpa overtime BRD item set memento.
	 *
	 * @param entity the entity
	 * @param entityAtens the entity atens
	 * @param companyId the company id
	 */
	public JpaOutsideOTBRDItemSetMemento(KshstOutsideOtBrd entity, String companyId) {
		if (entity.getKshstOutsideOtBrdPK() == null) {
			entity.setKshstOutsideOtBrdPK(new KshstOutsideOtBrdPK());
		}
		this.entity = entity;
		this.entity.getKshstOutsideOtBrdPK().setCid(companyId);
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
		this.entity.getKshstOutsideOtBrdPK().setBrdItemNo(breakdownItemNo.value);
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
			KshstOutsideOtBrdAten entityAten = new KshstOutsideOtBrdAten();
			entityAten.setKshstOutsideOtBrdAtenPK(
					new KshstOutsideOtBrdAtenPK(this.entity.getKshstOutsideOtBrdPK().getCid(),
							this.entity.getKshstOutsideOtBrdPK().getBrdItemNo(), id));
			return entityAten;
		}).collect(Collectors.toList()));
	}

}
