package nts.uk.ctx.at.request.dom.application.lateorleaveearly;
/**
 * 
 * @author hieult
 *
 */
public enum Select {

	/** 0: 選択されていない */
	NOTSLECTED(0),
	/** 1: 選択された */
	SELECTED(1);
	
	public int value ; 
	
	Select(int type) {
		this.value = type;
	}

}
