package nts.uk.ctx.sys.portal.dom.webmenu;

public enum ActivationClassification {
    	
	BootFromTreeMenu(0),
	
	DirectActivation(1);

	public int value;

	private ActivationClassification(int type) {
		this.value = type;
	}
	
}
