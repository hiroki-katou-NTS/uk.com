package nts.uk.ctx.at.request.dom.application.stamp;
/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 外出理由
 *
 */
public enum StampGoOutAtr {
	
	PRIVATE(0,"私用"),
	
	PUBLIC(1,"公用"),
	
	COMPENSATION(2,"有償"),
	
	UNION(3,"組合");
	
	public final int value;
	
	public final String name;
	
	StampGoOutAtr(int value, String name){
		this.value = value;
		this.name = name;
	}
}
