package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;

public enum ApplySetting {
	APPLY(0),
	NOT_APPLY(1);
	
	public final int value;
	
	ApplySetting(int value) {
		this.value = value;
	}
}
