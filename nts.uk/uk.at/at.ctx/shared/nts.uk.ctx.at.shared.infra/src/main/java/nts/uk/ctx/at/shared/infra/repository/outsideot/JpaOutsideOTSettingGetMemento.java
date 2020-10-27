/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.infra.entity.outsideot.KshmtOutsideSet;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetail;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetailPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshmtOutside;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshmtOutsidePK;
import nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.JpaOutsideOTBRDItemGetMemento;
import nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.JpaOvertimeGetMemento;

/**
 * The Class JpaOutsideOTSettingGetMemento.
 */
public class JpaOutsideOTSettingGetMemento implements OutsideOTSettingGetMemento{
	
	/** The entity overtimes. */
	private List<KshmtOutside> entityOvertimes;
	
	/** The entity overtime BRD items. */
	private List<KshmtOutsideDetail> entityOvertimeBRDItems;
	
	/** The entity. */
	private KshmtOutsideSet entity;
	

	/**
	 * Instantiates a new jpa overtime setting get memento.
	 *
	 * @param entity the entity
	 * @param entityOvertimes the entity overtimes
	 */
	public JpaOutsideOTSettingGetMemento(KshmtOutsideSet entity,
			List<KshmtOutsideDetail> entityOvertimeBRDItems, List<KshmtOutside> entityOvertimes) {
		entityOvertimeBRDItems.forEach(entityItem -> {
			if (entityItem.getKshmtOutsideDetailPK() == null) {
				entityItem.setKshmtOutsideDetailPK(new KshmtOutsideDetailPK());
			}
		});
		entityOvertimes.forEach(entityItem -> {
			if (entityItem.getKshmtOutsidePK() == null) {
				entityItem.setKshmtOutsidePK(new KshmtOutsidePK());
			}
		});
		this.entityOvertimes = entityOvertimes;
		this.entityOvertimeBRDItems = entityOvertimeBRDItems;
		this.entity = entity;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#getCompanyId(
	 * )
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#getNote()
	 */
	@Override
	public OvertimeNote getNote() {
		return new OvertimeNote(this.entity.getNote());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento#
	 * getBreakdownItems()
	 */
	@Override
	public List<OutsideOTBRDItem> getBreakdownItems() {
		return this.entityOvertimeBRDItems.stream()
				.map(entityBRDItem -> new OutsideOTBRDItem(
						new JpaOutsideOTBRDItemGetMemento(entityBRDItem)))
				.collect(Collectors.toList());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#
	 * getCalculationMethod()
	 */
	@Override
	public OutsideOTCalMed getCalculationMethod() {
		return OutsideOTCalMed.valueOf(this.entity.getCalculationMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#getOvertimes(
	 * )
	 */
	@Override
	public List<Overtime> getOvertimes() {
		return this.entityOvertimes.stream()
				.map(entityOvertime -> new Overtime(new JpaOvertimeGetMemento(entityOvertime)))
				.collect(Collectors.toList());
	}


}
