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
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowFixedRtSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;

/**
 * The Class JpaTimezoneOfFixedRestTimeSetSetMemento.
 */
public class JpaTimezoneOfFixedRestTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento {

	/** The entity. */
	private KshmtWtFloBrFl entity;

	/** The company id. */
	private String companyId;

	/** The work time cd. */
	private String workTimeCd;

	/** The resttime atr. */
	private int resttimeAtr;

	/**
	 * Instantiates a new jpa timezone of fixed rest time set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaTimezoneOfFixedRestTimeSetSetMemento(KshmtWtFloBrFl entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtFlowFixedRtSet())) {
			this.entity.setLstKshmtFlowFixedRtSet(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtFlowRtSetPK().getCid();
		this.workTimeCd = this.entity.getKshmtFlowRtSetPK().getWorktimeCd();
		this.resttimeAtr = this.entity.getKshmtFlowRtSetPK().getResttimeAtr();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> timzones) {
		if (CollectionUtil.isEmpty(timzones)) {
			this.entity.setLstKshmtFlowFixedRtSet(new ArrayList<>());
			return;
		}

		List<KshmtWtFloBrFiAllTs> lstEntity = this.entity.getLstKshmtFlowFixedRtSet();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
     	Map<KshmtFlowFixedRtSetPK, KshmtWtFloBrFiAllTs> mapEntity = lstEntity.stream()
     			.collect(Collectors.toMap(KshmtWtFloBrFiAllTs::getKshmtFlowFixedRtSetPK, Function.identity()));
		
		// set list entity
		List<KshmtWtFloBrFiAllTs> newListEntity = new ArrayList<>();
		int periodNo = 1;
		for (DeductionTime domain : timzones) {
			KshmtFlowFixedRtSetPK pk = new KshmtFlowFixedRtSetPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setResttimeAtr(resttimeAtr);
			pk.setPeriodNo(periodNo);

			// find entity if existed, else new entity
			KshmtWtFloBrFiAllTs entity = mapEntity.get(pk);
 			if (entity == null) {
 				entity = new KshmtWtFloBrFiAllTs();
 				entity.setKshmtFlowFixedRtSetPK(pk);
 			}

			// save to memento
			domain.saveToMemento(new JpaDeductionTimeSetMemento(entity));
			periodNo++;
			newListEntity.add(entity);
		}
		this.entity.setLstKshmtFlowFixedRtSet(newListEntity);
	}

}
