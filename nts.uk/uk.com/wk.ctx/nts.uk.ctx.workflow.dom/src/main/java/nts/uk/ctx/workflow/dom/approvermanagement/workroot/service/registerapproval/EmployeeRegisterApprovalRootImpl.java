package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.ApprovalRootService;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalPhaseOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApproverAsApplicationInforOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverAsApplicationOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeOrderApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeRegisterApprovalRootImpl implements EmployeeRegisterApprovalRoot {
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Inject
	private ApplicationOfEmployee appEmployee;
	@Inject
	private ApprovalRootService approvalService;
	@Inject
	private ApprovalPhaseRepository phaseRepo;
	@Inject
	private WorkplaceApproverAdapter wpAdapter;
	@Inject
	private PersonAdapter psAdapter;
	@Inject
	private SyJobTitleAdapter jobTitle;
	@Inject
	private CollectApprovalRootService collectApprRootService;
	
	/**
	 * 01.申請者としての承認ルートを取得する
	 */
	@Override
	public Map<String, WpApproverAsAppOutput> lstEmps(String companyID, GeneralDate baseDate, List<String> lstEmpIds, List<AppTypes> lstApps) {
		// toan the du lieu co workplace
		Map<String, WpApproverAsAppOutput> appOutput = new HashMap<>();

		// ドメインモデル「会社別就業承認ルート」を取得する(lấy dữ liệu domain 「会社別就業承認ルート」)
		List<CompanyApprovalRoot> lstComs = comRootRepository.findByBaseDate(companyID, baseDate);
		// ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
		List<WorkplaceApprovalRoot> lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate);
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
		List<PersonApprovalRoot> lstPss = psRootRepository.findAllByBaseDate(companyID, baseDate);
		// 選択する対象社員リストを先頭から最後までループする(loop list nhan vien da chon tu dau den cuoi)
		for (String empId : lstEmpIds) {
			List<ApprovalRootCommonOutput> appOfEmployee = new ArrayList<>();
			// ループ中の承認ルート対象が共通ルート が false の場合(loại đơn xin đang xử lý loop : 共通ルート = false)
			boolean checkComm = this.checkCommon(lstApps);
			if(checkComm){//TH common
				appOfEmployee = appEmployee.commonOfEmployee(lstComs, lstWps, lstPss, companyID, empId, baseDate);
				//get root by common
				this.getData(appOfEmployee, companyID, empId, baseDate, appOutput, new AppTypes(99, 0,null), "共通ルート");
			}
			for (AppTypes app : lstApps) {
				if(app.getCode().equals(99) && app.getEmpRoot() == 0){
					continue;
				}
				String appTypeName = "";
				if(app.getEmpRoot() == EmploymentRootAtr.APPLICATION.value){//TH application
					appTypeName = EnumAdaptor.valueOf(app.getCode(), ApplicationType.class).nameId;
				}else if(app.getEmpRoot() == EmploymentRootAtr.CONFIRMATION.value){//TH confirm
					appTypeName = EnumAdaptor.valueOf(app.getCode(), ConfirmationRootType.class).nameId;
				}
				//get root by application/confirm
				appOfEmployee = appEmployee.appOfEmployee(lstComs, lstWps, lstPss, companyID, empId, app,
						baseDate);
				this.getData(appOfEmployee, companyID, empId, baseDate, appOutput, app, appTypeName);
			}
		}
		return appOutput;
	}

	private boolean checkCommon(List<AppTypes> lstApps){
		for (AppTypes app : lstApps) {
			if(app.getCode().equals(99) && app.getEmpRoot() == EmploymentRootAtr.COMMON.value){
				return true;
			}
		}
		return false;
	}
	//check cho 1 don
	private Map<String, WpApproverAsAppOutput> getData(List<ApprovalRootCommonOutput> lstAppOfEmployee,
			String companyID, String empId, GeneralDate baseDate, Map<String, WpApproverAsAppOutput> appOutput,
			AppTypes apptyle, String appTypeName) {
		// list phase cua employee
		List<ApproverAsApplicationInforOutput> phaseInfors = new ArrayList<>();
		// du lieu cua phase trong employee
		Map<AppTypes, List<ApproverAsApplicationInforOutput>> mapAppType = new HashMap<>();
		Map<String, EmployeeApproverAsApplicationOutput> mapEmpRootInfo = new HashMap<>();
		
		ErrorFlag err = null;
		// 終了状態が「承認ルートあり」の場合(trang thai ket thuc「có approval route」)
		if (!CollectionUtil.isEmpty(lstAppOfEmployee)) {
			//TH: co nguoi approval
			// 2.承認ルートを整理する
			List<ApprovalRootOutput> result = new ArrayList<>();
			List<ApprovalPhase> approvalPhases = new ArrayList<>();
			lstAppOfEmployee.stream().forEach(x -> {
				result.add(new ApprovalRootOutput(companyID, x.getWorkpalceId(), x.getApprovalId(), empId,
						x.getHistoryId(), x.getApplicationType(), x.getStartDate(), x.getEndDate(), x.getBranchId(),
						x.getAnyItemApplicationId(), x.getConfirmationRootType(), x.getEmploymentRootAtr(), null,
						null));

				// tìm ra các phase từ bảng WWFMT_APPROVAL_PHASE
				List<ApprovalPhase> phases = phaseRepo.getAllApprovalPhasebyCode(companyID, x.getBranchId());

				// check nếu có thì add các phase còn ko thì đưa ra thông báo
				if (!CollectionUtil.isEmpty(phases)) {
					phases.stream().forEach(y -> {
						approvalPhases.add(y);
					});
				}
			});

			List<ApprovalRootOutput> adjustmentData = approvalService.adjustmentData(companyID, empId, baseDate,
					result);
			List<ApprovalPhaseOutput> adjustmentPhase = new ArrayList<>();
			adjustmentData.stream().forEach(z -> {
				List<ApprovalPhase> phaseLst = phaseRepo.getAllApprovalPhasebyCode(companyID, z.getBranchId());
				phaseLst.stream().forEach(w -> {
					List<ApproverInfo> approvers = w.getApprovers().stream().map(b -> new ApproverInfo(b.getJobTitleId(),
							b.getEmployeeId(),
							b.getApprovalPhaseId(), 
							b.getConfirmPerson() == ConfirmPerson.CONFIRM ? true : false, 
							b.getOrderNumber(),
							null,
							b.getApprovalAtr())).collect(Collectors.toList());
					adjustmentPhase
							.add(new ApprovalPhaseOutput(w.getCompanyId(), w.getBranchId(), w.getApprovalPhaseId(),
									w.getApprovalForm().value, w.getBrowsingPhase(), w.getOrderNumber(), approvers));
				});
			});
			for (ApprovalPhaseOutput phase : adjustmentPhase) {
				List<EmployeeOrderApproverAsAppOutput> employIn = new ArrayList<>();
				for (ApproverInfo appInfo : phase.getApprovers()) {
					String name = "";
					//ドメインモデル「承認者」．区分をチェックする(check thong tin domain「承認者」．区分)
					if(appInfo.getApprovalAtr() == ApprovalAtr.PERSON) {
						name = psAdapter.getPersonInfo(appInfo.getSid()).getEmployeeName();
						employIn.add(new EmployeeOrderApproverAsAppOutput(appInfo.getOrderNumber(),name, appInfo.getIsConfirmPerson()));
					}else {
						//3.職位から承認者へ変換する
						String employeeID = AppContexts.user().employeeId();
						List<ApproverInfo> lstApEm = collectApprRootService.convertPositionToApprover(companyID, employeeID, baseDate, appInfo.getJobId());
						for (ApproverInfo approver : lstApEm) {
							employIn.add(new EmployeeOrderApproverAsAppOutput(appInfo.getOrderNumber(),approver.getName(),appInfo.getIsConfirmPerson()));
						}
					}
					
				}
				ApproverAsApplicationInforOutput appAsAppInfor = new ApproverAsApplicationInforOutput(
						phase.getOrderNumber(), EnumAdaptor.valueOf(phase.getApprovalForm(), ApprovalForm.class).name,
						employIn);

				phaseInfors.add(appAsAppInfor);
			}
			List<ApprovalPhase> lstadjutst = new ArrayList<>();
			
			for (ApproverAsApplicationInforOutput appr : phaseInfors) {
				if(appr.getLstEmpInfo().size()==0){
					continue;
				}
				List<Approver> lstAppr = new ArrayList<>();
				for (EmployeeOrderApproverAsAppOutput c : appr.getLstEmpInfo()) {
					lstAppr.add(Approver.createSimpleFromJavaType("", "", "", "", "", c.getEmployeeName(), 0, 0, c.isConfirmPerson() ? 1:0));
				}
				lstadjutst.add(ApprovalPhase.createSimpleFromJavaType("", "", "", 1, 1, appr.getPhaseNumber(), lstAppr));
			}
			err = collectApprRootService.checkApprovalRoot(approvalPhases, lstadjutst);
		} else {
			// 「マスタなし」を表示し、出力対象として追加する(hiển thị là 「マスタなし」 ,và thêm vào dữ liệu output)
			ApproverAsApplicationInforOutput phase = new ApproverAsApplicationInforOutput(0, null, null);
			phaseInfors.add(phase);
			err = null;
		}
		PersonImport ps = psAdapter.getPersonInfo(empId);
		EmployeeApproverOutput empInfor = new EmployeeApproverOutput(ps.getSID(), ps.getEmployeeCode(), ps.getEmployeeName());
		EmployeeApproverAsApplicationOutput infor = new EmployeeApproverAsApplicationOutput(empInfor, mapAppType);
		apptyle.setErr(err);
		infor.getMapAppTypeAsApprover().put(apptyle, phaseInfors);

		WorkplaceImport wpInfor = wpAdapter.findBySid(empId, baseDate);
		if (!appOutput.isEmpty() && appOutput.containsKey(wpInfor.getWkpCode())) {
			WpApproverAsAppOutput wpRoot = appOutput.get(wpInfor.getWkpCode());
			Map<String, EmployeeApproverAsApplicationOutput> mapEmAp = wpRoot.getMapEmpRootInfo();
			if(!mapEmAp.isEmpty() && mapEmAp.containsKey(empId)) {
				EmployeeApproverAsApplicationOutput employ = mapEmAp.get(empId);
				Map<AppTypes, List<ApproverAsApplicationInforOutput>> mapAppTypeAsApprover = employ.getMapAppTypeAsApprover();
				mapAppTypeAsApprover.put(apptyle, phaseInfors);
			}else {
				mapEmAp.put(empId, infor);
			}
			
		} else {
			WorkplaceImport wkInfor = wpAdapter.findBySid(empId, baseDate);
			mapEmpRootInfo.put(empId, infor);
			WpApproverAsAppOutput output = new WpApproverAsAppOutput(wpInfor, mapEmpRootInfo);
			appOutput.put(wkInfor.getWkpCode(), output);
		}
		return appOutput;
	}
}
