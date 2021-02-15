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
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowStampReflectPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFstampReflectTime;

/**
 * The Class JpaFlowStampReflectTimezoneSetMemento.
 */
public class JpaFlowStampReflectTimezoneSetMemento implements FlowStampReflectTzSetMemento {

	/** The entity. */
	private KshmtFstampReflectTime entity;

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
	public JpaFlowStampReflectTimezoneSetMemento(KshmtFstampReflectTime entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtFlowStampReflect())) {
			this.entity.setLstKshmtFlowStampReflect(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtFstampReflectTimePK().getCid();
		this.workTimeCd = this.entity.getKshmtFstampReflectTimePK().getWorktimeCd();
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
			this.entity.setLstKshmtFlowStampReflect(new ArrayList<>());
			return;
		}

		List<KshmtFlowStampReflect> lstEntity = this.entity.getLstKshmtFlowStampReflect();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtFlowStampReflectPK, KshmtFlowStampReflect> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtFlowStampReflect::getKshmtFlowStampReflectPK, Function.identity()));

		// set list entity
		this.entity.setLstKshmtFlowStampReflect(lstRtz.stream().map(domain -> {
			// newPk
			KshmtFlowStampReflectPK pk = new KshmtFlowStampReflectPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setWorkNo(domain.getWorkNo().v());
			pk.setAttendAtr(domain.getClassification().value);

			// find entity if existed, else new entity
			KshmtFlowStampReflect entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtFlowStampReflect();
				entity.setKshmtFlowStampReflectPK(pk);
			}

			// save to memento
			domain.saveToMemento(new JpaStampReflectTimezoneSetMemento(entity));

			return entity;
		}).collect(Collectors.toList()));
	}

}
