/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.gateway.app.command.login.BasicLoginCommand;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampInputLoginCommand extends BasicLoginCommand {

	private String companyId;
	private String sid;
	private String passWord;
	private Boolean passwordInvalid;
	private Boolean isAdminMode; 
	private Boolean runtimeEnvironmentCreat;
	private String  screenID; 
	private String  programID;
	

	
	
}

