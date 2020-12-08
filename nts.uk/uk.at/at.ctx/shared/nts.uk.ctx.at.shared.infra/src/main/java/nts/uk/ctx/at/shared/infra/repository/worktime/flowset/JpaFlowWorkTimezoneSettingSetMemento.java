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
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowTimeZonePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtOtTimeZonePK;

/**
 * The Class JpaFlowWorkTimezoneSettingSetMemento.
 */
public class JpaFlowWorkTimezoneSettingSetMemento implements FlWtzSettingSetMemento {

	/** The entity. */
	private KshmtWtFlo entity;

	/** The company id. */
	private String companyId;

	/** The work time cd. */
	private String workTimeCd;

	/**
	 * Instantiates a new jpa flow work timezone setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkTimezoneSettingSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtOtTimeZone())) {
			this.entity.setLstKshmtOtTimeZone(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtFlowWorkSetPK().getCid();
		this.workTimeCd = this.entity.getKshmtFlowWorkSetPK().getWorktimeCd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingSetMemento#
	 * setWorkTimeRounding(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setWorkTimeRounding(TimeRoundingSetting trSet) {
		KshmtWtFloWorkTs timeZoneEntity = this.entity.getKshmtFlowTimeZone();
		if (timeZoneEntity == null) {
			KshmtFlowTimeZonePK pk = new KshmtFlowTimeZonePK();
			pk.setCid(this.companyId);
			pk.setWorktimeCd(this.workTimeCd);
			
			timeZoneEntity = new KshmtWtFloWorkTs();
			timeZoneEntity.setKshmtFlowTimeZonePK(pk);
			this.entity.setKshmtFlowTimeZone(timeZoneEntity);
		}
		this.entity.getKshmtFlowTimeZone().setUnit(trSet.getRoundingTime().value);
		this.entity.getKshmtFlowTimeZone().setRounding(trSet.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingSetMemento#
	 * setLstOTTimezone(java.util.List)
	 */
	@Override
	public void setLstOTTimezone(List<FlowOTTimezone> lstTzone) {
		if (CollectionUtil.isEmpty(lstTzone)) {
			this.entity.setLstKshmtOtTimeZone(new ArrayList<>());
			return;
		}

		List<KshmtWtFloOverTs> lstEntity = this.entity.getLstKshmtOtTimeZone();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtOtTimeZonePK, KshmtWtFloOverTs> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtWtFloOverTs::getKshmtOtTimeZonePK, Function.identity()));

		// set list entity
		this.entity.setLstKshmtOtTimeZone(lstTzone.stream().map(domain -> {
			// newPk
			KshmtOtTimeZonePK pk = new KshmtOtTimeZonePK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setWorktimeNo(domain.getWorktimeNo());

			// find entity if existed, else new entity
			KshmtWtFloOverTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFloOverTs();
				entity.setKshmtOtTimeZonePK(pk);
			}

			// save to memento
			domain.saveToMemento(new JpaFlowOTTimezoneSetMemento(entity));

			return entity;
		}).collect(Collectors.toList()));
	}
}
