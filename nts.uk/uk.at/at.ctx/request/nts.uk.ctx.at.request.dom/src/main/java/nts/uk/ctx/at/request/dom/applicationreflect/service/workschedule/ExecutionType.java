package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;
/**
 * 実行種別
 * @author dudt
 *
 */
public enum ExecutionType {
	/**	通常実行 */
	NORMALECECUTION(0),
	/** 再実行*/
	RETURN(1);
	
	public int value;
	
	ExecutionType(int type){
		this.value = type;
	}
}
