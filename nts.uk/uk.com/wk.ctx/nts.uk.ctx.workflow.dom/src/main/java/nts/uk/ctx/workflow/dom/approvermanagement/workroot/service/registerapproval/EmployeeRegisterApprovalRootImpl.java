package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApproverAsAppInfor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.DataSourceApproverList;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpApproverAsApp;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpOrderApproverAsApp;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.service.ApprovalSettingService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput.LevelInforOutput;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput.LevelInforOutput.LevelApproverList;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput.LevelInforOutput.LevelApproverList.LevelApproverInfo;

@Stateless
public class EmployeeRegisterApprovalRootImpl implements EmployeeRegisterApprovalRoot {
	
	public static final Integer EMPLOYMENT = 0;
	public static final Integer HUMAN_RESOURCE = 1;
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Inject
	private ApplicationOfEmployee appEmployee;
	@Inject
	private ApprovalPhaseRepository phaseRepo;
	@Inject
	private WorkplaceApproverAdapter wpAdapter;
	@Inject
	private PersonAdapter psAdapter;
	@Inject
	private CollectApprovalRootService collectApprSv;

	
	@Inject
	private ApprovalSettingService approvalSettingService;
	
	/**
	 * 01.申請者としての承認ルートを取得する
	 */
	@Override
	public DataSourceApproverList lstEmps(String companyID, int sysAtr, GeneralDate baseDate,  List<String> lstEmpIds, List<AppTypes> lstApps) {
		// toan the du lieu co workplace
		Map<String, WpApproverAsAppOutput> appOutput = new HashMap<>();
		List<CompanyApprovalRoot> lstComs = Collections.emptyList();
		List<WorkplaceApprovalRoot> lstWps = Collections.emptyList();
		List<PersonApprovalRoot> lstPss = Collections.emptyList();
		ApproverRegisterSet approverRegsterSet = approvalSettingService.getSettingUseUnit(companyID, sysAtr);
		
		if (approverRegsterSet.getCompanyUnit() == UseClassification.DO_USE) {
			// ドメインモデル「会社別就業承認ルート」を取得する(lấy dữ liệu domain 「会社別就業承認ルート」)
			lstComs = comRootRepository.findByBaseDate(companyID, baseDate, sysAtr);			
		}
		if (approverRegsterSet.getWorkplaceUnit() == UseClassification.DO_USE) {
			// ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
			lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate, sysAtr);			
		}
		if (approverRegsterSet.getEmployeeUnit() == UseClassification.DO_USE) {
			// ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
			lstPss = psRootRepository.findAllByBaseDate(companyID, baseDate, sysAtr);			
		}
		
		// 選択する対象社員リストを先頭から最後までループする(loop list nhan vien da chon tu dau den cuoi)
		for (String empId : lstEmpIds) {
			List<ApprovalRootCommonOutput> appOfEmployee = new ArrayList<>();
			for (AppTypes app : lstApps) {
				if(app.getEmpRoot() == 0){// ループ中の承認ルート対象が共通ルート が false の場合(loại đơn xin đang xử lý loop : 共通ルート = false)
					//03.社員の共通の承認ルートを取得する
					appOfEmployee = appEmployee.commonOfEmployee(lstComs, lstWps, lstPss, companyID, empId,
							baseDate, sysAtr);
					
				}else {
					//02.社員の対象申請の承認ルートを取得する
					appOfEmployee = appEmployee.appOfEmployee(lstComs, lstWps, lstPss, companyID, empId, app,
							baseDate, sysAtr);
					
				}
				Optional<ApprovalRootCommonOutput> appE = appOfEmployee.isEmpty() ? Optional.empty() : Optional.of(appOfEmployee.get(0));
				this.getData(sysAtr, appE, companyID, empId, baseDate, appOutput, app);
			}
		}
		List<WkpDepInfo> lstWpInfor = new ArrayList<>();
		for (Map.Entry<String, WpApproverAsAppOutput> m : appOutput.entrySet()) {
			WpApproverAsAppOutput wp = (WpApproverAsAppOutput) m.getValue();
			lstWpInfor.add(wp.getWpInfor());
		}
		Collections.sort(lstWpInfor, Comparator.comparing(WkpDepInfo:: getCode));
		return new DataSourceApproverList(appOutput, lstWpInfor);
	}

	//check cho 1 don
	private void getData(int sysAtr, Optional<ApprovalRootCommonOutput> approvalRootOp,
			String companyID, String empId, GeneralDate baseDate, Map<String, WpApproverAsAppOutput> appOutput,
			AppTypes apptype) {
		// list phase cua employee
		List<ApproverAsAppInfor> phaseInfors = new ArrayList<>();
		// du lieu cua phase trong employee
		Map<AppTypes, List<ApproverAsAppInfor>> mapAppType = new HashMap<>();
		Map<String, EmpApproverAsApp> mapEmpRootInfo = new HashMap<>();
		ErrorFlag err = null;
		// 終了状態が「承認ルートあり」の場合(trang thai ket thuc「có approval route」)
		if (approvalRootOp.isPresent()) {
			List<ApprovalPhase> phases = phaseRepo.getAllApprovalPhasebyCode(approvalRootOp.get().getApprovalId());
			//2.承認ルートを整理する（二次開発）
			LevelOutput p = collectApprSv.organizeApprovalRoute(companyID, empId, baseDate, phases,
					EnumAdaptor.valueOf(sysAtr, SystemAtr.class), apptype.getLowerApprove() == null ? Optional.empty() : Optional.of(apptype.getLowerApprove()));
			//7.承認ルートの異常チェック
			err = collectApprSv.checkApprovalRoot(p);

			for(LevelInforOutput level : p.getLevelInforLst()) {
				int phaseNumber = level.getLevelNo();
				String approvalForm =  EnumAdaptor.valueOf(level.getApprovalForm(), ApprovalForm.class).name;
				List<EmpOrderApproverAsApp> employIn = this.convet(level.getApproverLst());
				ApproverAsAppInfor appAsAppInfor = new ApproverAsAppInfor(
						phaseNumber, approvalForm, employIn);
				phaseInfors.add(appAsAppInfor);
			}
		} else {
			// 「マスタなし」を表示し、出力対象として追加する(hiển thị là 「マスタなし」 ,và thêm vào dữ liệu output)
			ApproverAsAppInfor phase = new ApproverAsAppInfor(0, null, null);
			phaseInfors.add(phase);
			err = null;
		}
		PersonImport ps = psAdapter.getPersonInfo(empId);
		EmployeeApproverOutput empInfor = new EmployeeApproverOutput(ps.getSID(), ps.getEmployeeCode(), ps.getEmployeeName());
		List<AppTypes> lstAppTypes = new ArrayList<>();	
		EmpApproverAsApp infor = new EmpApproverAsApp(empInfor, mapAppType, lstAppTypes);
		AppTypes ap = new AppTypes(apptype.getCode(), apptype.getId(), apptype.getEmpRoot(), err, apptype.getName(), null);
		lstAppTypes.add(ap);
		infor.setLstAppTypes(this.sortByAppTypeConfirm(lstAppTypes));
		infor.getMapAppType().put(ap, phaseInfors);
		//wkpDep info
		String id = Strings.EMPTY;
		Optional<WkpDepInfo> wkpDepO = Optional.empty();
		if(sysAtr == 0){
			id = wpAdapter.getWorkplaceIDByEmpDate(empId, GeneralDate.today());
			wkpDepO = wpAdapter.findByWkpIdNEW(companyID, id, baseDate);
		}else{
			id = wpAdapter.getDepartmentIDByEmpDate(empId, GeneralDate.today());
			wkpDepO = wpAdapter.findByDepIdNEW(companyID, id, baseDate);
		}
		if(!wkpDepO.isPresent()) return;
		WkpDepInfo wkpDep = wkpDepO.get();
		if (!appOutput.isEmpty() && appOutput.containsKey(wkpDep.getId())) {
			//TH da ton tai wpk 
			WpApproverAsAppOutput wpRoot = appOutput.get(wkpDep.getId());
			Map<String, EmpApproverAsApp> mapEmAp = wpRoot.getMapEmpRootInfo();
			if(!mapEmAp.isEmpty() && mapEmAp.containsKey(empId)) {//TH da ton tai nhan vien
				EmpApproverAsApp employ = mapEmAp.get(empId);
				Map<AppTypes, List<ApproverAsAppInfor>> mapAppTypeAsApprover = employ.getMapAppType();
				mapAppTypeAsApprover.put(ap, phaseInfors);
				lstAppTypes = new ArrayList<AppTypes>(employ.getLstAppTypes());
				lstAppTypes.add(ap);
				employ.setLstAppTypes(this.sortByAppTypeConfirm(lstAppTypes));
			}else {//TH chua ton tai nv
				mapEmAp.put(empId, infor);
				//#101014
				List<EmployeeApproverOutput> lstEmp = new ArrayList<EmployeeApproverOutput>(wpRoot.getLstEmployeeInfo());
				lstEmp.add(empInfor);
				Collections.sort(lstEmp, Comparator.comparing(EmployeeApproverOutput:: getEmpCD));
				wpRoot.setLstEmployeeInfo(lstEmp);
			}
		} else {//TH chua ton tai wpk
			mapEmpRootInfo.put(empId, infor);
			WpApproverAsAppOutput output = new WpApproverAsAppOutput(wkpDep, mapEmpRootInfo, Arrays.asList(empInfor));
			appOutput.put(wkpDep.getId(), output);
		}
	}
	private List<EmpOrderApproverAsApp> convet(List<LevelApproverList> approverLst) {
		List<EmpOrderApproverAsApp> lstResult = new ArrayList<>();
		for(LevelApproverList appr : approverLst) {
			int approverOrder = appr.getOrder();
			boolean confirmPerson = appr.isComfirmAtr();
			for(LevelApproverInfo c : appr.getApproverInfoLst()) {
				lstResult.add(new EmpOrderApproverAsApp(approverOrder, 
						psAdapter.getPersonInfo(c.getApproverID()).getEmployeeName(), confirmPerson));
			}
		}
		return lstResult;
	}
	/**
	 * ソート順： 
	 * 就業：　共通、申請種類（昇順）、確認ルート種類（昇順）
	 * 人事：　共通、届出、各業務エベント
	 * @param wpRootInfor
	 * @return
	 */
	private List<AppTypes> sortByAppTypeConfirm(List<AppTypes> lstAppType){
		List<AppTypes>  lstSort = new ArrayList<>();
		List<AppTypes> lstCommon = lstAppType.stream()
					.filter(c -> c.getEmpRoot() == 0).collect(Collectors.toList());
		List<AppTypes> lstApp = lstAppType.stream()
				.filter(c -> c.getEmpRoot() == 1).collect(Collectors.toList());
		List<AppTypes> lstConfirm = lstAppType.stream()
				.filter(c -> c.getEmpRoot() == 2).collect(Collectors.toList());
		List<AppTypes> lstNotice = lstAppType.stream()
				.filter(c -> c.getEmpRoot() == 4).collect(Collectors.toList());
		List<AppTypes> lstEvent = lstAppType.stream()
				.filter(c -> c.getEmpRoot() == 5).collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(lstApp)) {
			Collections.sort(lstApp, Comparator.comparing(AppTypes:: getCode));
		}
		if(!CollectionUtil.isEmpty(lstConfirm)) {
			Collections.sort(lstConfirm, Comparator.comparing(AppTypes:: getCode));
		}
		if(!CollectionUtil.isEmpty(lstNotice)) {
			Collections.sort(lstNotice, Comparator.comparing(AppTypes:: getCode));
		}
		if(!CollectionUtil.isEmpty(lstEvent)) {
			Collections.sort(lstEvent, Comparator.comparing(AppTypes:: getCode));
		}
		lstSort.addAll(lstCommon);
		lstSort.addAll(lstApp);
		lstSort.addAll(lstConfirm);
		lstSort.addAll(lstNotice);
		lstSort.addAll(lstEvent);
		return lstSort;
	}
}
