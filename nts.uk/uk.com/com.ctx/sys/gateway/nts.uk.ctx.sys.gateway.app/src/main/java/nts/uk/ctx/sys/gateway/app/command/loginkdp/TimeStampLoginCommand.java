/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampLoginCommand {
	
	private String contractCode;
	private String companyId;
	private String employeeCode;
	private String password;
	private boolean passwordInvalid;
	private boolean isAdminMode; 
	private boolean runtimeEnvironmentCreat;
	private String  screenId; 
	private String  programId; 
	private String  url; 
	
	/** The request. */
	private HttpServletRequest request;
	
	
	
	private String companyCode; // hoi lai
	
	public String getContractCode() {
		return contractCode.trim();
	}
	
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode.trim();
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public String getPassword() {
		if(this.password != null){
			return this.password;
		}else{
			return null;
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPasswordInvalid() {
        return passwordInvalid;
    }

    public void setPasswordInvalid(boolean passwordInvalid) {
        this.passwordInvalid = passwordInvalid;
    }
    
    public boolean isAdminMode() {
        return isAdminMode;
    }

    public void setAdminMode(boolean isAdminMode) {
        this.isAdminMode = isAdminMode;
    }
    
    public boolean isRuntimeEnvironmentCreat() {
        return runtimeEnvironmentCreat;
    }

    public void setRuntimeEnvironmentCreat(boolean runtimeEnvironmentCreat) {
        this.runtimeEnvironmentCreat = runtimeEnvironmentCreat;
    }
    
	public String getScreenID() {
		return screenId;
	}

	public void setScreenID(String screenID) {
		this.screenId = screenID;
	}
	
	public String getProgramID() {
		return programId;
	}

	public void setProgramID(String programID) {
		this.programId = programID;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCompanyCode() {
		return companyCode.trim();
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode.trim();
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
	
	
}

