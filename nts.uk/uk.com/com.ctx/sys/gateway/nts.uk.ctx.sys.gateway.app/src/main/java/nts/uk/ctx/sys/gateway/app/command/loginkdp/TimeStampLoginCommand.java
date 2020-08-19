/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laitv
 * [Input]
	・Contract code
	・Company ID
	・Employee code
	・Password (Optional)
	・Password invalid
	・Administrator
	・Create runtime environment
	・Screen ID
	・Program ID
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampLoginCommand {
	private String contractCode;
	
	private String companyId;
	private String companyCode; // hoi lai
	
	private String employeeId;
	private String employeeCode;
	
	private String password;
	private boolean passwordInvalid;
	private boolean isAdminMode;
	private boolean runtimeEnvironmentCreate;
	
	private String screenId;
	private String programId;
	private String url;

	/** The request. */
	private HttpServletRequest request;
}
