package nts.uk.ctx.pereg.app.find.licence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LicenseUpperLimit {

	private EndStatusLicenseCheck status;
	
	private int registered;
	
	private int canBeRegistered;
	
	private int maxRegistered;
}
