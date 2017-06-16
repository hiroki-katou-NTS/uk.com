package nts.uk.ctx.sys.portal.dom.webmenu;

public enum SelectedAtr {
    	
	BootFromTreeMenu(0),
	
	DirectActivation(1);

	public int value;

	private SelectedAtr(int type) {
		this.value = type;
	}
	
}
