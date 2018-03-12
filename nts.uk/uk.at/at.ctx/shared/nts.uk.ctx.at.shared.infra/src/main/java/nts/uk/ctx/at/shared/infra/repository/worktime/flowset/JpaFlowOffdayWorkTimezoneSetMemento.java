/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWtzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRtSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRtSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFworkHolidayTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFworkHolidayTimePK;

/**
 * The Class JpaFlowOffdayWorkTimezoneSetMemento.
 */
public class JpaFlowOffdayWorkTimezoneSetMemento implements FlowOffdayWtzSetMemento {

	/** The entity. */
	private KshmtFlowWorkSet entity;

	/** The company id. */
	private String companyId;

	/** The work time cd. */
	private String workTimeCd;

	/**
	 * Instantiates a new jpa flow offday work timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowOffdayWorkTimezoneSetMemento(KshmtFlowWorkSet entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtFworkHolidayTime())) {
			this.entity.setLstKshmtFworkHolidayTime(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtFlowWorkSetPK().getCid();
		this.workTimeCd = this.entity.getKshmtFlowWorkSetPK().getWorktimeCd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzSetMemento#
	 * setRestTimeZone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimeZone(FlowWorkRestTimezone tzone) {
		KshmtFlowRtSet offDayEntity = this.entity.getFlowOffDayWorkRtSet();
		if (offDayEntity == null) {
			KshmtFlowRtSetPK pk = new KshmtFlowRtSetPK();
			pk.setCid(this.entity.getKshmtFlowWorkSetPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtFlowWorkSetPK().getWorktimeCd());
			pk.setResttimeAtr(ResttimeAtr.OFF_DAY.value);
			
			offDayEntity = new KshmtFlowRtSet();
			offDayEntity.setKshmtFlowRtSetPK(pk);
			this.entity.getLstKshmtFlowRtSet().add(offDayEntity);
		}			
		tzone.saveToMemento(new JpaFlowWorkRestTimezoneSetMemento(this.entity.getFlowOffDayWorkRtSet()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzSetMemento#
	 * setLstWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkTimezone(List<FlowWorkHolidayTimeZone> listHdtz) {
		if (CollectionUtil.isEmpty(listHdtz)) {
			this.entity.setLstKshmtFworkHolidayTime(new ArrayList<>());
			return;
		}

		List<KshmtFworkHolidayTime> lstEntity = this.entity.getLstKshmtFworkHolidayTime();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtFworkHolidayTimePK, KshmtFworkHolidayTime> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtFworkHolidayTime::getKshmtFworkHolidayTimePK, Function.identity()));

		// set list entity
		this.entity.setLstKshmtFworkHolidayTime(listHdtz.stream().map(domain -> {
			// newPk
			KshmtFworkHolidayTimePK pk = new KshmtFworkHolidayTimePK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setWorktimeNo(domain.getWorktimeNo());

			// find entity if existed, else new entity
			KshmtFworkHolidayTime entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtFworkHolidayTime();
				entity.setKshmtFworkHolidayTimePK(pk);
			}

			// save to memento
			domain.saveToMemento(new JpaFlowWorkHolidayTimeZoneSetMemento(entity));

			return entity;
		}).collect(Collectors.toList()));
	}

}
