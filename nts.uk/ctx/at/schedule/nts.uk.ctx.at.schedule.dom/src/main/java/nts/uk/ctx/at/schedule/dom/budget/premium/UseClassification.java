package nts.uk.ctx.at.schedule.dom.budget.premium;

public enum UseClassification {
	NotUse(0),
	
	Use(1);
	
	public final int value;
	
	UseClassification(int value){
		this.value = value;
	}
}
