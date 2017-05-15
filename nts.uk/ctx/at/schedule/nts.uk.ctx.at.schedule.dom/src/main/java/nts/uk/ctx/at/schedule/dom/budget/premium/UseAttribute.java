package nts.uk.ctx.at.schedule.dom.budget.premium;

public enum UseAttribute {
	NotUse(0),
	
	Use(1);
	
	public final int value;
	
	UseAttribute(int value){
		this.value = value;
	}
}
