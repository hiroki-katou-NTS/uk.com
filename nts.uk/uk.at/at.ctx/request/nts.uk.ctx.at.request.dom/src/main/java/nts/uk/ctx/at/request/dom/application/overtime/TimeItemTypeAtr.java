package nts.uk.ctx.at.request.dom.application.overtime;

public enum TimeItemTypeAtr {
	
	NORMAL_TYPE(0),

	SPECIAL_TYPE(1);

	public final int value;
	
	TimeItemTypeAtr(int value){
		this.value = value;
	}
}
