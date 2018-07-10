package nts.uk.ctx.at.shared.pubimp.remainingnumber.nursingcareleavemanagement.interimdata;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class Output2 {
	
	private double grantedNumberThisTime = 0;
	private double grantedNumberNextTime = 0;
	private double useNumberPersonInfo = 0;
	private boolean useClassification = false;
	private GeneralDate startDateOverlapBeforeGrant, endDateOverlapBeforeGrant, startDateOverlapAfterGrant,
	endDateOverlapAfterGrant;
	
	public Output2() {
		this.grantedNumberThisTime = 0;
		this.grantedNumberNextTime = 0;
		this.useNumberPersonInfo = 0;
		this.useClassification = false;
	}

}
