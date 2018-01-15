package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;
/**
 * 
 * @author hieult
 *
 */
public enum OutputMode {
	
	DISPLAYMODE(0),
	EDITMODE(1);

	public int value;

	OutputMode(int type) {
		this.value = type;
	}

}
