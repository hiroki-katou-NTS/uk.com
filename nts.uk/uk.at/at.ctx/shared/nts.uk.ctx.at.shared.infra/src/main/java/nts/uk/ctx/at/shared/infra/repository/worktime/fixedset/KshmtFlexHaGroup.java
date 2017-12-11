/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRestTime;

/**
 * The Class KshmtFlexHaGroup.
 */
@Getter
@Setter
public class KshmtFlexHaGroup {

	/** The entity. */
	private KshmtFlexHaRestTime entity;
	
	/** The entity fixed rests. */
	private List<KshmtFlexHaFixRest> entityFixedRests;

	/**
	 * Instantiates a new kshmt flex ha groub.
	 *
	 * @param entity the entity
	 * @param entityFixedRests the entity fixed rests
	 */
	public KshmtFlexHaGroup(KshmtFlexHaRestTime entity, List<KshmtFlexHaFixRest> entityFixedRests) {
		super();
		this.entity = entity;
		this.entityFixedRests = entityFixedRests;
	}
	
}
