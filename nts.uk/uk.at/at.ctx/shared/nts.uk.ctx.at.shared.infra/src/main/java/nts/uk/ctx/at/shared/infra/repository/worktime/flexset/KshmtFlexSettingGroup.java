/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtFlexSettingGroup.
 */
@Getter
@Setter
public class KshmtFlexSettingGroup {

	/** The entity groups. */
	private List<KshmtFlexHaGroup> entityGroups;

	public KshmtFlexSettingGroup(List<KshmtFlexHaGroup> entityGroups) {
		super();
		this.entityGroups = entityGroups;
	}
	
	
}
