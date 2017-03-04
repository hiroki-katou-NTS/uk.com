package nts.uk.ctx.basic.dom.organization.position;

public enum PresenceCheckScopeSet {
	EveryoneCanSee(0),
	NotVisibleToEveryone(1),
	SetForEachRole(2);

	public final int value;

	private PresenceCheckScopeSet(int value) {
		this.value = value;
	}

	

}
