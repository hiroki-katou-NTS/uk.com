/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.infra.entity.outsideot.KshmtOutsideSet;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetail;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshmtOutside;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshmtOutsidePK;
import nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.JpaOutsideOTBRDItemSetMemento;
import nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.JpaOvertimeSetMemento;

/**
 * The Class JpaOutsideOTSettingSetMemento.
 */
@Getter
public class JpaOutsideOTSettingSetMemento implements OutsideOTSettingSetMemento{
	
	/** The entity overtimes. */
	private List<KshmtOutside> entityOvertimes;
	
	/** The entity overtime BRD items. */
	private List<KshmtOutsideDetail> entityOvertimeBRDItems;
	
	/** The entity. */
	private KshmtOutsideSet entity;
	
	/**
	 * Instantiates a new jpa overtime setting set memento.
	 *
	 * @param entityOvertimes the entity overtimes
	 * @param entity the entity
	 */
	public JpaOutsideOTSettingSetMemento(List<KshmtOutside> entityOvertimes,
			List<KshmtOutsideDetail> entityOvertimeBRDItems, KshmtOutsideSet entity) {
		entityOvertimes.forEach(entityItem -> {
			if (entityItem.getKshmtOutsidePK() == null) {
				entityItem.setKshmtOutsidePK(new KshmtOutsidePK());
			}
		});
		this.entityOvertimes = entityOvertimes;
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#setCompanyId(
	 * nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#setNote(nts.
	 * uk.ctx.at.shared.dom.overtime.OvertimeNote)
	 */
	@Override
	public void setNote(OvertimeNote note) {
		this.entity.setNote(note.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingSetMemento#
	 * setBreakdownItems(java.util.List)
	 */
	@Override
	public void setBreakdownItems(List<OutsideOTBRDItem> breakdownItems) {
		this.entityOvertimeBRDItems = breakdownItems.stream().map(overtimeBRDItem -> {
			KshmtOutsideDetail entityOvertimeBRDItem = new KshmtOutsideDetail();
			overtimeBRDItem.saveToMemento(
					new JpaOutsideOTBRDItemSetMemento(entityOvertimeBRDItem, this.entity.getCid()));
			return entityOvertimeBRDItem;
		}).collect(Collectors.toList());

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#
	 * setCalculationMethod(nts.uk.ctx.at.shared.dom.overtime.
	 * OvertimeCalculationMethod)
	 */
	@Override
	public void setCalculationMethod(OutsideOTCalMed calculationMethod) {
		this.entity.setCalculationMethod(calculationMethod.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#setOvertimes(
	 * java.util.List)
	 */
	@Override
	public void setOvertimes(List<Overtime> overtimes) {
		this.entityOvertimes = overtimes.stream().map(overtime -> {
			KshmtOutside entityOvertime = new KshmtOutside();
			overtime.saveToMemento(new JpaOvertimeSetMemento(entityOvertime, this.entity.getCid()));
			return entityOvertime;
		}).collect(Collectors.toList());
	}

	

}
