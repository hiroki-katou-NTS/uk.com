package nts.uk.ctx.cloud.operate.ws.command;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.experimental.var;
import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.cloud.operate.app.command.*;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyCategoryDto;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyDataCommand;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyDataCommandHanlder;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyDataExecutionRespone;
import nts.uk.ctx.sys.assist.app.find.mastercopy.MasterCopyCategoryFindDto;
import nts.uk.ctx.sys.assist.app.find.mastercopy.MasterCopyCategoryFinder;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.shr.com.company.CompanyId;
import nts.uk.shr.infra.data.TenantLocatorService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("ctx/cld/operate/tenant")
@Produces("application/json")
public class CreateTenantWebService extends WebService{

	@Inject
	private CreateTenantOnCloudCommandHandler handler;

	@Inject
	private GeneratePasswordCommandHandler generatePasswordHandler;

	@Inject
	private MasterCopyCommandHandler masterCopyHandler;


	@POST
	@Path("regist")
	public void registTenant(CreateTenantOnCloudCommand command) {
		TenantLocatorService.connect(command.getTenanteCode());
		this.handler.handle(command);
	}

	@POST
	@Path("generatePassword")
	public void generatePassword(GeneratePasswordCommand command) {
		this.generatePasswordHandler.handle(command);
	}

	@POST
	@Path("mastercopy/execute")
	public void executeMasterCopy(MasterCopyCommand command){
		TenantLocatorService.connect(command.getTenantCode());
		this.masterCopyHandler.handle(command);
	}
}
