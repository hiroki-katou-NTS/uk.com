/**
 * 
 */
package nts.uk.screen.at.app.mobi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hieult
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WarningConfirmDto {

	private String messWarningSystem;
	
	private String mesWarningCompany;
	
	private boolean messWarning;
}
