package nts.uk.ctx.sys.portal.dom.webmenu;

public enum SelectedAtr {
    /* (0) ツリーメニューから起動 */	
	BootFromTreeMenu(0),
	/* (1) 直接起動 */
	DirectActivation(1);

	public final int value;

	private SelectedAtr(int type) {
		this.value = type;
		
	}
	
}
