/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtg;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgTs;

/**
 * The Class JpaSingleDayScheduleSetMemento.
 */
public class JpaSDayScheWorkCatSetMemento implements SingleDayScheduleSetMemento {

	/** The entity. */
	private KshmtWorkcondCtg entity;
	
	/** The map kshmt work cat time zone. */
	private Map<Integer, KshmtWorkcondCtgTs> mapKshmtWorkcondCtgTs;

	/**
	 * Instantiates a new jpa single day schedule set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheWorkCatSetMemento(String historyId, int workCategoryAtr,
			KshmtWorkcondCtg entity) {
		if (entity.getKshmtWorkcondCtgPK() == null) {
			KshmtWorkcondCtgPK kshmtWorkcondCtgPK = new KshmtWorkcondCtgPK();
			kshmtWorkcondCtgPK.setHistoryId(historyId);
			kshmtWorkcondCtgPK.setPerWorkCatAtr(workCategoryAtr);
			entity.setKshmtWorkcondCtgPK(kshmtWorkcondCtgPK);
		}

		this.entity = entity;
		
		this.mapKshmtWorkcondCtgTs = new HashMap<>();
		if (!CollectionUtil.isEmpty(entity.getKshmtWorkcondCtgTss())) {
			entity.getKshmtWorkcondCtgTss().stream().forEach(c -> {
				c.setSid(entity.getSid());
			});
			
			this.mapKshmtWorkcondCtgTs = entity.getKshmtWorkcondCtgTss().stream()
					.collect(Collectors.toMap(item -> item.getKshmtWorkcondCtgTsPK().getCnt(), Function.identity()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTypeCode(nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(Optional<WorkTypeCode> workTypeCode) {
		if (workTypeCode != null && workTypeCode.isPresent()){
			this.entity.setWorkTypeCode(workTypeCode.get().v());
		}  else {
			this.entity.setWorkTypeCode(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkingHours(java.util.List)
	 */
	@Override
	public void setWorkingHours(List<TimeZone> workingHours) {
		this.entity.setKshmtWorkcondCtgTss(workingHours.stream().map(item -> {
			KshmtWorkcondCtgTs kshmtWorkcondCtgTs = this.mapKshmtWorkcondCtgTs.getOrDefault(Integer.valueOf(item.getCnt()), new KshmtWorkcondCtgTs());
			item.saveToMemento(new JpaTimezoneSetMemento<KshmtWorkcondCtgTs>(
					this.entity.getKshmtWorkcondCtgPK().getHistoryId(),
					this.entity.getKshmtWorkcondCtgPK().getPerWorkCatAtr(), kshmtWorkcondCtgTs));
			return kshmtWorkcondCtgTs;
		}).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTimeCode(java.util.Optional)
	 */
	@Override
	public void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode) {
		if (workTimeCode != null && workTimeCode.isPresent()){
			this.entity.setWorkTimeCode(workTimeCode.get().v());
		}  else {
			this.entity.setWorkTimeCode(null);
		}
	}

}
