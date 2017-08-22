package nts.uk.ctx.at.request.dom.application.lateorleaveearly;
/**
 * 
 * @author hieult
 *
 */
public enum Early {

	/** 0: 選択されていない */
	notSlected(0),
	/** 1: 選択された */
	Select(1);
	
	public int value ; 
	
	Early(int type) {
		this.value = type;
	}

}
