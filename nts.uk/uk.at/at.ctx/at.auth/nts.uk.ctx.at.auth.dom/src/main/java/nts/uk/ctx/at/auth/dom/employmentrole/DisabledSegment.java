package nts.uk.ctx.at.auth.dom.employmentrole;

public enum DisabledSegment {
	/**
	 * する(toUse)
	 */
	TO_USE(0,""),
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
