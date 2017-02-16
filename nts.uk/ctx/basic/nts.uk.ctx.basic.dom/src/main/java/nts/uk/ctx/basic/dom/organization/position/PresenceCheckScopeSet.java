package nts.uk.ctx.basic.dom.organization.position;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PresenceCheckScopeSet {

	EVERYONE_CAN_SEE(0),
	
	NOT_VISIBLE_TO_EVERYONE(1),
	
	SET_FOR_EACH_ROLE(2);
	
	public final int value;
}
