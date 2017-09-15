/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.setting;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingGetMemento;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.infra.entity.outsideot.KshstOverTimeSet;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOverTimeBrd;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOverTimeBrdPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK;
import nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.JpaOvertimeBRDItemGetMemento;
import nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.JpaOvertimeGetMemento;

/**
 * The Class JpaOvertimeSettingGetMemento.
 */
public class JpaOvertimeSettingGetMemento implements OutsideOTSettingGetMemento{
	
	/** The entity overtimes. */
	private List<KshstOverTime> entityOvertimes;
	
	/** The entity overtime BRD items. */
	private List<KshstOverTimeBrd> entityOvertimeBRDItems;
	
	/** The entity. */
	private KshstOverTimeSet entity;
	

	/**
	 * Instantiates a new jpa overtime setting get memento.
	 *
	 * @param entity the entity
	 * @param entityOvertimes the entity overtimes
	 */
	public JpaOvertimeSettingGetMemento(KshstOverTimeSet entity,
			List<KshstOverTimeBrd> entityOvertimeBRDItems, List<KshstOverTime> entityOvertimes) {
		entityOvertimeBRDItems.forEach(entityItem -> {
			if (entityItem.getKshstOverTimeBrdPK() == null) {
				entityItem.setKshstOverTimeBrdPK(new KshstOverTimeBrdPK());
			}
		});
		entityOvertimes.forEach(entityItem -> {
			if (entityItem.getKshstOverTimePK() == null) {
				entityItem.setKshstOverTimePK(new KshstOverTimePK());
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
				.map(entityBRDItem -> new OutsideOTBRDItem(new JpaOvertimeBRDItemGetMemento(
						entityBRDItem, entityBRDItem.getEntityAtens())))
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
