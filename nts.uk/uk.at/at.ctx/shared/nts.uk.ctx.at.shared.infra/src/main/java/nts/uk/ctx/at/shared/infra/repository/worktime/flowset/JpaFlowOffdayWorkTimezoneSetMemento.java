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
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloHolTsPK;

/**
 * The Class JpaFlowOffdayWorkTimezoneSetMemento.
 */
public class JpaFlowOffdayWorkTimezoneSetMemento implements FlowOffdayWtzSetMemento {

	/** The entity. */
	private KshmtWtFlo entity;

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
	public JpaFlowOffdayWorkTimezoneSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloHolTs())) {
			this.entity.setLstKshmtWtFloHolTs(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtWtFloPK().getCid();
		this.workTimeCd = this.entity.getKshmtWtFloPK().getWorktimeCd();
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
		KshmtWtFloBrFl offDayEntity = this.entity.getFlowOffDayWorkRtSet();
		if (offDayEntity == null) {
			KshmtWtFloBrFlPK pk = new KshmtWtFloBrFlPK();
			pk.setCid(this.entity.getKshmtWtFloPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtWtFloPK().getWorktimeCd());
			pk.setResttimeAtr(ResttimeAtr.OFF_DAY.value);
			
			offDayEntity = new KshmtWtFloBrFl();
			offDayEntity.setKshmtWtFloBrFlPK(pk);
			this.entity.getLstKshmtWtFloBrFl().add(offDayEntity);
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
			this.entity.setLstKshmtWtFloHolTs(new ArrayList<>());
			return;
		}

		List<KshmtWtFloHolTs> lstEntity = this.entity.getLstKshmtWtFloHolTs();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtWtFloHolTsPK, KshmtWtFloHolTs> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtWtFloHolTs::getKshmtWtFloHolTsPK, Function.identity()));

		// set list entity
		this.entity.setLstKshmtWtFloHolTs(listHdtz.stream().map(domain -> {
			// newPk
			KshmtWtFloHolTsPK pk = new KshmtWtFloHolTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setWorktimeNo(domain.getWorktimeNo());

			// find entity if existed, else new entity
			KshmtWtFloHolTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFloHolTs();
				entity.setKshmtWtFloHolTsPK(pk);
			}

			// save to memento
			domain.saveToMemento(new JpaFlowWorkHolidayTimeZoneSetMemento(entity));

			return entity;
		}).collect(Collectors.toList()));
	}

}
