package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject;

import lombok.Getter;

@Getter
public class AnnLeaRemNumValueObject {

	private double days;
	
	private int minutes;
	
	public AnnLeaRemNumValueObject(double days, int minutes) {
		super();
		this.days = days;
		this.minutes = minutes;
	}

}
