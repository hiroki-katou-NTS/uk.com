package nts.uk.ctx.pereg.app.find.licence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LicensenCheckDto {

	private boolean display;

	private int registered;

	private int canBeRegistered;
	
	private int maxRegistered;
	
	private String message;

	private String licenseKey;
}
