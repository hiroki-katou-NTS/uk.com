package nts.uk.ctx.sys.portal.dom.webmenu;

public enum TitleMenuIndicator {

	
	NotSetTitleMenu(0),

	
	SelectTitleMenu(1);

	public int value;

	private TitleMenuIndicator(int type) {
		this.value = type;
	}
}
