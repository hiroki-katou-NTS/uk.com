package nts.uk.ctx.at.shared.dom.application.reflect;
/**
 * 実行種別
 * @author thanh_nx
 *
 */
public enum ExecutionTypeShare {
	/**	通常実行 */
	NORMALECECUTION(0),
	/** 再実行*/
	RETURN(1);
	
	public int value;
	
	ExecutionTypeShare(int type){
		this.value = type;
	}
}
