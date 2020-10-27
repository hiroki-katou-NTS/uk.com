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
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloWorkTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloOverTsPK;

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
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloOverTs())) {
			this.entity.setLstKshmtWtFloOverTs(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtWtFloPK().getCid();
		this.workTimeCd = this.entity.getKshmtWtFloPK().getWorktimeCd();
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
		KshmtWtFloWorkTs timeZoneEntity = this.entity.getKshmtWtFloWorkTs();
		if (timeZoneEntity == null) {
			KshmtWtFloWorkTsPK pk = new KshmtWtFloWorkTsPK();
			pk.setCid(this.companyId);
			pk.setWorktimeCd(this.workTimeCd);
			
			timeZoneEntity = new KshmtWtFloWorkTs();
			timeZoneEntity.setKshmtWtFloWorkTsPK(pk);
			this.entity.setKshmtWtFloWorkTs(timeZoneEntity);
		}
		this.entity.getKshmtWtFloWorkTs().setUnit(trSet.getRoundingTime().value);
		this.entity.getKshmtWtFloWorkTs().setRounding(trSet.getRounding().value);
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
			this.entity.setLstKshmtWtFloOverTs(new ArrayList<>());
			return;
		}

		List<KshmtWtFloOverTs> lstEntity = this.entity.getLstKshmtWtFloOverTs();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtWtFloOverTsPK, KshmtWtFloOverTs> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtWtFloOverTs::getKshmtWtFloOverTsPK, Function.identity()));

		// set list entity
		this.entity.setLstKshmtWtFloOverTs(lstTzone.stream().map(domain -> {
			// newPk
			KshmtWtFloOverTsPK pk = new KshmtWtFloOverTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setWorktimeNo(domain.getWorktimeNo());

			// find entity if existed, else new entity
			KshmtWtFloOverTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFloOverTs();
				entity.setKshmtWtFloOverTsPK(pk);
			}

			// save to memento
			domain.saveToMemento(new JpaFlowOTTimezoneSetMemento(entity));

			return entity;
		}).collect(Collectors.toList()));
	}
}
