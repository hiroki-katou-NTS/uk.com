package nts.uk.ctx.at.auth.dom.employmentrole;
/**
 * するしない区分
 * @author tutk
 *
 */
public enum DisabledSegment {
	/**
	 * する(toUse)
	 */
	USE_ATR(0,""),
	/**
	 * しない(notUse)
	 */
	NOT_USE(1,"");
	
	public int value;
	
	public String nameId;
	
	DisabledSegment(int type,String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
