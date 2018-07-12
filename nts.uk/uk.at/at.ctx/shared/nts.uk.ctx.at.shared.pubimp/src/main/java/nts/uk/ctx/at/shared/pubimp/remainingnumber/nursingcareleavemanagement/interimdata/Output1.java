package nts.uk.ctx.at.shared.pubimp.remainingnumber.nursingcareleavemanagement.interimdata;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class Output1 {
	
	private GeneralDate useStartDateBefore;
	
	private GeneralDate useEndDateBefore;
	
	private boolean periodGrantFlag;
	
	private Optional<GeneralDate> useStartDateAfter;
	
	private Optional<GeneralDate> useEndDateAfter;
	
	public Output1() {
		this.periodGrantFlag = false;
		useStartDateAfter = Optional.empty();
		useEndDateAfter = Optional.empty();
	}

}
