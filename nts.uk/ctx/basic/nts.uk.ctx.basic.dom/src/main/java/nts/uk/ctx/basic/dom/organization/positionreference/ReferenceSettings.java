package nts.uk.ctx.basic.dom.organization.positionreference;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReferenceSettings {
	CAN_NOT_REFER(0),
	
	CAN_REFER(1);
	
	public final int value;
}
