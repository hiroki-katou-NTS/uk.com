package nts.uk.ctx.basic.dom.organization.position;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PresenceCheckScopeSet {
	EveryoneCanSee(0),
	NotVisibleToEveryone(1),
	SetForEachRole(2);

	public final int value;


}
