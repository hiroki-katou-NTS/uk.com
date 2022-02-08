package nts.uk.ctx.sys.gateway.app.command.login.url;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.LoginRecordInput;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.shr.com.context.DeviceInfo;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.com.url.UrlExecInfo;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class UrlResult implements LoginCommandHandlerBase.AuthenticationResultBase {
	
	public String programID;
	
	public String screenID;
	
	public String embeddedId;

	public GeneralDateTime expiredDate;
    
	public GeneralDateTime issueDate;

	public Map<String, String> urlTaskValueList;
	
//	public String successMsg; da day vao trong obj changePw
	public String webAppID;
	public CheckChangePassDto changePw;

	@Setter
	public String messageId;
	
	public boolean smpDevice;

	private boolean success;

	private IdentifiedEmployeeInfo identified;

	public UrlResult(boolean success){
		this.success = success;
	}
    public static UrlResult failed() {
		return new UrlResult(false);
    }

	public static UrlResult create(UrlExecInfo urlExecInfo, Optional<DeviceInfo> device) {
		Map<String, String> taskValue = urlExecInfo.getTaskIncre().stream()
				.collect(Collectors.toMap(t -> t.getTaskIncreKey(), t->t.getTaskIncreValue()));

		String webAppID = ProgramsManager.findById(urlExecInfo.getProgramId() + urlExecInfo.getScreenId())
				.map(x -> x.getAppId().toString().toLowerCase()).orElse("");
		if(webAppID == null || webAppID.isEmpty()){
			webAppID = ProgramsManager.findById(urlExecInfo.getProgramId()+"A")
					.map(x -> x.getAppId().toString().toLowerCase()).orElse("");
		}
		return new UrlResult(
				urlExecInfo.getProgramId().toLowerCase(),
				urlExecInfo.getScreenId().toLowerCase(),
				urlExecInfo.getEmbeddedId(),
				urlExecInfo.getExpiredDate(),
				urlExecInfo.getIssueDate(),
				taskValue,
				webAppID,
				null,
				"",
				device.map(d -> d.isSmartPhone()).orElse(null),
				true,
				null);
	}
	public void setFailed(String messageId) {
		this.success = false;
		this.messageId = messageId;
	}
	public void setIdentified(IdentifiedEmployeeInfo identified) {
		this.identified = identified;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	@Override
	public IdentifiedEmployeeInfo getIdentified() {
		return identified;
	}

	public String getEmployeeId() {
		return (identified == null || identified.getEmployeeId() == null || identified.getEmployeeId().isEmpty())
				? ""
				: identified.getEmployeeId();
	}
	public String getLoginId() {
		return (identified == null || identified.getUser() == null || identified.getUser().getLoginID() == null || identified.getUser().getLoginID().v().isEmpty())
				? ""
				: identified.getEmployeeId();
	}

	public String getCid() {
		return (identified == null || identified.getCompanyId() == null)
				? ""
				: identified.getCompanyId();
	}

	public LoginRecordInput createRecord(int status, int method, String remark){
		return new LoginRecordInput(
			this.programID,
			this.screenID,
			"",
				status,
				method,
			"",
			remark,
			this.identified.getEmployeeId());
	}
}
