/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexStampReflect;

@Getter
@Setter
public class KshmtFlexArrayGroup {
	
	/** The entity worktimezones. */
	private List<KshmtFlexHolSet> entityWorktimezones;
	
	/** The entity fixed rests. */
	private List<KshmtFlexOdFixRest> entityFixedRests;
	
	/** The entity flow rests. */
	private List<KshmtFlexOdRestSet> entityFlowRests;
	
	/** The entity stamps. */
	private List<KshmtFlexStampReflect> entityStamps;

	public KshmtFlexArrayGroup(List<KshmtFlexHolSet> entityWorktimezones, List<KshmtFlexOdFixRest> entityFixedRests,
			List<KshmtFlexOdRestSet> entityFlowRests, List<KshmtFlexStampReflect> entityStamps) {
		super();
		this.entityWorktimezones = entityWorktimezones;
		this.entityFixedRests = entityFixedRests;
		this.entityFlowRests = entityFlowRests;
		this.entityStamps = entityStamps;
	}

}
