/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import javax.ejb.Stateless;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneLateEarlyCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaWorkTimezoneLateEarlySetGetMemento.
 */
public class JpaWorkTimezoneLateEarlySetGetMemento implements WorkTimezoneLateEarlySetGetMemento {

	/** The entity. */
	private KshmtWtCom entity;
	
	/** The Constant TRUE_VALUE. */
	private static final Integer TRUE_VALUE = 1;

	/**
	 * Instantiates a new jpa work timezone late early set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkTimezoneLateEarlySetGetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetGetMemento#getCommonSet()
	 */
	@Override
	public EmTimezoneLateEarlyCommonSet getCommonSet() {
		return new EmTimezoneLateEarlyCommonSet(this.entity.getKshmtWtComLatetimeMng().getIsDeducteFromTime() == TRUE_VALUE);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetGetMemento#getOtherClassSet()
	 */
	@Override
	public List<OtherEmTimezoneLateEarlySet> getOtherClassSet() {
		if (CollectionUtil.isEmpty(this.entity.getKshmtOtherLateEarlies())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtOtherLateEarlies().stream()
				.map(item -> new OtherEmTimezoneLateEarlySet(
						new JpaOtherEmTimezoneLateEarlySetGetMemento(item)))
				.collect(Collectors.toList());
	}

}
