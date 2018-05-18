package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeleteSubstitutionOfHDManaDataCommand {

	String subOfHDID;
	
	String sID;
	
	GeneralDate dayOff;
	
	GeneralDate expirationDate;
}
