/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneLateEarlyCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeCommonSet;

/**
 * The Class JpaWorkTimezoneCommonSetGetMemento.
 */
public class JpaWorkTimezoneLateEarlySetGetMemento implements WorkTimezoneLateEarlySetGetMemento {

	/** The entity. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa work timezone common set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneLateEarlySetGetMemento(KshmtWorktimeCommonSet entity) {
		super();
		this.entity = entity;
	}

	@Override
	public EmTimezoneLateEarlyCommonSet getCommonSet() {
		return null;
		// TODO: DienTX pls check again.
		// return new EmTimezoneLateEarlyCommonSet(this.entity.get);
	}

	@Override
	public List<OtherEmTimezoneLateEarlySet> getOtherClassSet() {
		return this.entity.getKshmtOtherLateEarlies().stream()
				.map(item -> new OtherEmTimezoneLateEarlySet(
						new JpaOtherEmTimezoneLateEarlySetGetMemento(item)))
				.collect(Collectors.toList());
	}

}
