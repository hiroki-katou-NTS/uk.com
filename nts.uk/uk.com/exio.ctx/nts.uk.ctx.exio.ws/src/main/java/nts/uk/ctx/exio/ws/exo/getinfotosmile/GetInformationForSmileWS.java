package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.workrecord.actuallock.ActualLockFinder;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.ClosureHistPeriod;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.GetSpecifyPeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresInfoDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileClosureTime;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileEmpClosure;
import nts.uk.ctx.sys.gateway.app.find.tenantlogin.TenanLoginFinder;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Path("exio/exo/getinforsmile")
@Produces("application/json")
public class GetInformationForSmileWS extends WebService{
	@Inject
	private ClosureFinder finder;
	
	@Inject
	private TenanLoginFinder tenanFinder;
	
	@Inject
	private ClosureFinder closureFinder;
	
	@Inject
	private ActualLockFinder lockFinder;
	
	@Inject
	private ApprovalRootStateAdapter approvalFinder;
	
	@Inject
	private GetSpecifyPeriod specify;
	
	
	// 締め情報を取得する
	@POST
	@Path("get-current-closure-by-cid")
	public List<ClosuresInfoDto> getClosureByCid() {
		String Cid = AppContexts.user().companyId();
		List<ClosuresInfoDto> listClosure = finder.findClosureByCid(Cid);
		return listClosure;
	}
	
	@POST
	@Path("check")
	public boolean check() {
		String contract = AppContexts.user().contractCode();
		return this.tenanFinder.checkExist(contract);
	}
	
	// 雇用に紐づく締め情報を取得する
	@POST
	@Path("getemployment-relate-closure")
	public List<SmileEmpClosure> getEmploymentClosure() {
		String cid = AppContexts.user().companyId();
		return this.closureFinder.getEmpCloSmile(cid);
	}
	
	// 締め開始日と締め終了日を算出する
	@POST
	@Path("geclosure-time")
	public SmileClosureTime getEmploymentClosure(ClosureTimeParam param) {
		String Cid = AppContexts.user().companyId();
		return this.closureFinder.getTimeSmile(Cid, param.getClosureId(), param.getStartYM());
	}
	
	// 月次のロック状態を判定する
	@POST
	@Path("get-monthly-lock-status")
	public Integer getDetermineMonthlyLockStatus(LockStatusParam param) {
		String cid = AppContexts.user().companyId();
		return this.lockFinder.findMonthState(cid, param.getClosureId());
	}
	
	// 月別承認状態を取得する
	@POST
	@Path("getmonth-state")
	public ApprovalRootStateDto getEmploymentClosure(ApprovalRootStateParam param) {
		ApprovalRootStateImport_New approState = this.approvalFinder.getAppRootInstanceMonthByEmpPeriod(param.getEmployeeId(), new DatePeriod(GeneralDate.legacyDate(param.getStartDate()), GeneralDate.legacyDate(param.getEndDate())), 
				new YearMonth(param.getYearMonth()), param.getClosureID(), new ClosureDate(param.getClosureDay(), param.getLastDayOfMonth()), GeneralDate.legacyDate(param.getBaseDate()));
		if(approState == null)
			return null;
		ApprovalRootStateDto rootState = new ApprovalRootStateDto(approState.getRootStateID(),
				approState.getListApprovalPhaseState().stream()
										.map(x -> new ApprovalPhaseStateDto(x.getPhaseOrder(), 
																				x.getApprovalAtr().value,
																				x.getApprovalForm().value, 
																				x.getListApprovalFrame().stream()
																					.map(y -> new ApprovalFrameDto(y.getFrameOrder(), 
																							y.getListApprover().stream().map(t -> new ApproverStateDto(t.getApproverID(),
																									t.getApprovalAtr().value, t.getAgentID(), t.getApproverName(), 
																									t.getAgentName(), t.getRepresenterID(), t.getRepresenterName(), 
																									t.getApprovalDate(), t.getApprovalReason(), t.getApproverEmail(), 
																									t.getAgentMail(), t.getRepresenterEmail(), t.getApproverInListOrder()))
																									.collect(Collectors.toList()), 
																							y.getConfirmAtr(), y.getAppDate())).collect(Collectors.toList())))
										.collect(Collectors.toList()), 
				approState.getDate(), 
				approState.getRootType(), 
				approState.getEmployeeID());
		
		return rootState;
	}
	
	// 指定した年月の締め期間を取得する
	@POST
	@Path("getclosing-period")
	public Map<Integer, List<ClosureHistPeriodDto>> getSpecify(ClosingPeriodParam param) {
		List<ClosureHistPeriod> listSpecify = specify.getSpecifyPeriod(new YearMonth(param.getYearmonth()));
		List<ClosureHistPeriodDto> listPeriod = listSpecify.stream().map(x -> new ClosureHistPeriodDto(x)).collect(Collectors.toList());
		
		Map<Integer, List<ClosureHistPeriodDto>> listPeriodHist = new HashMap<>();
		listPeriodHist = listPeriod.stream().collect(Collectors.groupingBy(ClosureHistPeriodDto :: getClosureId));
		return listPeriodHist;
	}
}
