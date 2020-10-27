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
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ReflectReferenceTwoWorkTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloStmpRefTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloStmpRef2Ts;

/**
 * The Class JpaFlowStampReflectTimezoneSetMemento.
 */
public class JpaFlowStampReflectTimezoneSetMemento implements FlowStampReflectTzSetMemento {

	/** The entity. */
	private KshmtWtFloStmpRef2Ts entity;

	/** The company id. */
	private String companyId;

	/** The work time cd. */
	private String workTimeCd;

	/**
	 * Instantiates a new jpa flow stamp reflect timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowStampReflectTimezoneSetMemento(KshmtWtFloStmpRef2Ts entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloStmpRefTs())) {
			this.entity.setLstKshmtWtFloStmpRefTs(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtWtFloStmpRef2TsPK().getCid();
		this.workTimeCd = this.entity.getKshmtWtFloStmpRef2TsPK().getWorktimeCd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzSetMemento#
	 * setTwoTimesWorkReflectBasicTime(nts.uk.ctx.at.shared.dom.worktime.flowset
	 * .ReflectReferenceTwoWorkTime)
	 */
	@Override
	public void setTwoTimesWorkReflectBasicTime(ReflectReferenceTwoWorkTime rtwt) {
		this.entity.setTwoReflectBasicTime(rtwt.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzSetMemento#
	 * setStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setStampReflectTimezone(List<StampReflectTimezone> lstRtz) {
		if (CollectionUtil.isEmpty(lstRtz)) {
			this.entity.setLstKshmtWtFloStmpRefTs(new ArrayList<>());
			return;
		}

		List<KshmtWtFloStmpRefTs> lstEntity = this.entity.getLstKshmtWtFloStmpRefTs();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtWtFloStmpRefTsPK, KshmtWtFloStmpRefTs> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtWtFloStmpRefTs::getKshmtWtFloStmpRefTsPK, Function.identity()));

		// set list entity
		this.entity.setLstKshmtWtFloStmpRefTs(lstRtz.stream().map(domain -> {
			// newPk
			KshmtWtFloStmpRefTsPK pk = new KshmtWtFloStmpRefTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setWorkNo(domain.getWorkNo().v());
			pk.setAttendAtr(domain.getClassification().value);

			// find entity if existed, else new entity
			KshmtWtFloStmpRefTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFloStmpRefTs();
				entity.setKshmtWtFloStmpRefTsPK(pk);
			}

			// save to memento
			domain.saveToMemento(new JpaStampReflectTimezoneSetMemento(entity));

			return entity;
		}).collect(Collectors.toList()));
	}

}
