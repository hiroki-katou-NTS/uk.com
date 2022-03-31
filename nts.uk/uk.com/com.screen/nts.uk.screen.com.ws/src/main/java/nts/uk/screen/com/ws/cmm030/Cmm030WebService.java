package nts.uk.screen.com.ws.cmm030;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.com.app.find.cmm030.a.DisplayEmployeeApproversFinder;
import nts.uk.screen.com.app.find.cmm030.a.GetSetEmployeeListFinder;
import nts.uk.screen.com.app.find.cmm030.a.InitScreenAFinder;
import nts.uk.screen.com.app.find.cmm030.a.dto.DisplayEmployeeApproversDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.GetSetEmployeeListDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.InitScreenADto;
import nts.uk.screen.com.app.find.cmm030.a.param.DisplayEmployeeApproversParam;
import nts.uk.screen.com.app.find.cmm030.a.param.GetSetEmployeeListParam;
import nts.uk.screen.com.app.find.cmm030.a.param.InitScreenAParam;
import nts.uk.screen.com.app.find.cmm030.b.GetApprovalAuthorityHoldersFinder;
import nts.uk.screen.com.app.find.cmm030.b.GetWorkplaceInfoFinder;
import nts.uk.screen.com.app.find.cmm030.b.dto.GetApprovalAuthorityDto;
import nts.uk.screen.com.app.find.cmm030.b.dto.GetWorkplaceInfoDto;
import nts.uk.screen.com.app.find.cmm030.b.param.GetApprovalAuthorityHoldersParam;
import nts.uk.screen.com.app.find.cmm030.b.param.GetWorkplaceInfoParam;
import nts.uk.screen.com.app.find.cmm030.c.GetApprovalRootLastStartDateFinder;
import nts.uk.screen.com.app.find.cmm030.c.GetClosureStartDateFinder;
import nts.uk.screen.com.app.find.cmm030.f.GetApproverHistoryFinder;
import nts.uk.screen.com.app.find.cmm030.f.GetSelfApproverSettingFinder;
import nts.uk.screen.com.app.find.cmm030.f.dto.SelfApproverSettingDto;
import nts.uk.screen.com.app.find.cmm030.f.dto.SummarizePeriodDto;
import nts.uk.screen.com.app.find.cmm030.f.param.GetSelfApproverSettingParam;

@Path("screen/com/cmm030")
@Produces("application/json")
public class Cmm030WebService extends WebService {

	@Inject
	private DisplayEmployeeApproversFinder displayEmployeeApproversFinder;

	@Inject
	private GetSetEmployeeListFinder getSetEmployeeListFinder;

	@Inject
	private InitScreenAFinder initScreenAFinder;

	@Inject
	private GetApprovalAuthorityHoldersFinder getApprovalAuthorityHoldersFinder;

	@Inject
	private GetWorkplaceInfoFinder getWorkplaceInfoFinder;

	@Inject
	private GetApprovalRootLastStartDateFinder getApprovalRootLastStartDateFinder;
	
	@Inject
	private GetClosureStartDateFinder getClosureStartDateFinder;

	@Inject
	private GetApproverHistoryFinder getApproverHistoryFinder;

	@Inject
	private GetSelfApproverSettingFinder getSelfApproverSettingFinder;

	@POST
	@Path("initScreenA")
	public InitScreenADto initScreenA(InitScreenAParam param) {
		return this.initScreenAFinder.findData(param);
	}

	@POST
	@Path("displayEmployeeApprovers")
	public DisplayEmployeeApproversDto displayEmployeeApprovers(DisplayEmployeeApproversParam param) {
		return this.displayEmployeeApproversFinder.findData(param);
	}

	@POST
	@Path("getSetEmployeeList")
	public GetSetEmployeeListDto getSetEmployeeList(GetSetEmployeeListParam param) {
		return this.getSetEmployeeListFinder.findData(param);
	}

	@POST
	@Path("getApprovalAuthorityHolders")
	public GetApprovalAuthorityDto getApprovalAuthorityHolders(GetApprovalAuthorityHoldersParam param) {
		return this.getApprovalAuthorityHoldersFinder.findData(param);
	}
	
	@POST
	@Path("getWorkplaceInfo")
	public GetWorkplaceInfoDto getWorkplaceInfo(GetWorkplaceInfoParam param) {
		return this.getWorkplaceInfoFinder.findData(param);
	}
	
	@POST
	@Path("getApprovalRootLastStartDate/{sid}")
	public GeneralDate getApprovalRootLastStartDate(@PathParam("sid") String sid) {
		return this.getApprovalRootLastStartDateFinder.findData(sid);
	}
	
	@POST
	@Path("getClosureStartDate/{sid}")
	public GeneralDate getClosureStartDate(@PathParam("sid") String sid) {
		return this.getClosureStartDateFinder.findData(sid);
	}
	
	@POST
	@Path("getApproverHistory/{sid}")
	public List<SummarizePeriodDto> getApproverHistory(@PathParam("sid") String sid) {
		return this.getApproverHistoryFinder.findData(sid);
	}
	
	@POST
	@Path("getSelfApproverSetting")
	public SelfApproverSettingDto getSelfApproverSetting(GetSelfApproverSettingParam param) {
		return this.getSelfApproverSettingFinder.findData(param);
	}
}
