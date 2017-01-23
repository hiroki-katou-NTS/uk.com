package nts.uk.ctx.basic.app.command.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author lanlt
 *
 */
@Getter
public class CreateCompanyCommand {
	/** company Code	 */
	private String companyCode;
	/** company Name */
	private String companyName;
	public CreateCompanyCommand(){
		 
	 }
}
