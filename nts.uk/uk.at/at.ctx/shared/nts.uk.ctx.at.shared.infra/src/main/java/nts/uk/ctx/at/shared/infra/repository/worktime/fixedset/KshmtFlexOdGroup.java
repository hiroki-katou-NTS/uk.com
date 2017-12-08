/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestTime;

@Getter
@Setter
public class KshmtFlexOdGroup {
	
	/** The entity worktimezones. */
	private List<KshmtFlexHolSet> entityWorktimezones;
	
	/** The entity. */
	private KshmtFlexOdRestTime entityOffday;
	
	/** The entity fixed rests. */
	private List<KshmtFlexOdFixRest> entityFixedRests;
	
	/** The entity flow rests. */
	private List<KshmtFlexOdRestSet> entityFlowRests;

	/**
	 * Instantiates a new kshmt flex od group.
	 *
	 * @param entityWorktimezones the entity worktimezones
	 * @param entityOffday the entity offday
	 * @param entityFixedRests the entity fixed rests
	 * @param entityFlowRests the entity flow rests
	 */
	public KshmtFlexOdGroup(List<KshmtFlexHolSet> entityWorktimezones, KshmtFlexOdRestTime entityOffday,
			List<KshmtFlexOdFixRest> entityFixedRests, List<KshmtFlexOdRestSet> entityFlowRests) {
		super();
		this.entityWorktimezones = entityWorktimezones;
		this.entityOffday = entityOffday;
		this.entityFixedRests = entityFixedRests;
		this.entityFlowRests = entityFlowRests;
	}
	
	

}
