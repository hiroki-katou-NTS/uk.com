package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FurikyuMngDataExtractionService {
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	@Inject
	private SysWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private EmpSubstVacationRepository empSubstVacationRepository;
	
	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;
	
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	public FurikyuMngDataExtractionData getFurikyuMngDataExtraction(String sid, GeneralDate startDate, GeneralDate endDate, boolean isPeriod) {
		List<PayoutManagementData> payoutManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout = new ArrayList<PayoutSubofHDManagement>();
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToSub = new ArrayList<PayoutSubofHDManagement>();
		Double numberOfDayLeft;
		int expirationDate;
		Integer closureId;
		String cid = AppContexts.user().companyId();
		String empCD = null;
		boolean haveEmploymentCode = false;
		
		// select 全ての状況
		if(isPeriod) {
			payoutManagementData = payoutManagementDataRepository.getAllData();
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getAllData();
		// select 現在の残数状況
		} else {
			payoutManagementData = payoutManagementDataRepository.getBySidStateAndInSub(sid);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBySidRemainDayAndInPayout(sid);
		}
		
		if (!payoutManagementData.isEmpty()){
			List<String> listPayoutID = payoutManagementData.stream().map(x ->{
				return x.getPayoutId();
			}).collect(Collectors.toList());
			
			payoutSubofHDManagementLinkToPayout = payoutSubofHDManaRepository.getByListPayoutID(listPayoutID);
		}
		
		if (!substitutionOfHDManagementData.isEmpty()){
			List<String> listSubID = substitutionOfHDManagementData.stream().map(x ->{
				return x.getSubOfHDID();
			}).collect(Collectors.toList());
			
			payoutSubofHDManagementLinkToSub = payoutSubofHDManaRepository.getByListSubID(listSubID);
		}
		
		if (sysEmploymentHisAdapter.findSEmpHistBySid(cid, sid, GeneralDate.legacyDate(new Date())).isPresent()) {
			empCD = sysEmploymentHisAdapter.findSEmpHistBySid(cid, sid, GeneralDate.legacyDate(new Date())).get().getEmploymentCode();
		}
		
		if(empCD != null) {
			haveEmploymentCode = true;
		}
		
		numberOfDayLeft = getNumberOfDayLeft(sid);
		expirationDate = getExpirationDate(sid, empCD);
		closureId = getClosureId(sid, empCD);
		
		SWkpHistImport sWkpHistImport = null;
		if(syWorkplaceAdapter.findBySid(sid, GeneralDate.today()).isPresent()) {
			sWkpHistImport = syWorkplaceAdapter.findBySid(sid, GeneralDate.today()).get();
		}
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(sid);
		List<PersonEmpBasicInfoImport> employeeBasicInfo = empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
		PersonEmpBasicInfoImport personEmpBasicInfoImport = null;
		if (!employeeBasicInfo.isEmpty()){
			personEmpBasicInfoImport = employeeBasicInfo.get(0);
		}
		
		return new FurikyuMngDataExtractionData(payoutManagementData, substitutionOfHDManagementData, payoutSubofHDManagementLinkToPayout, payoutSubofHDManagementLinkToSub, expirationDate, numberOfDayLeft, closureId, haveEmploymentCode, sWkpHistImport, personEmpBasicInfoImport);
	}
	
	public void displayRemainingNumberDataInformation(String compId, String empId, boolean isPeriod, String messageDisplay) {
		List<PayoutManagementData> payoutManagementData;
		EmploymentManageDistinctDto emplManage = getEmploymentManageDistinct(compId, empId);
		if (emplManage.getIsManage() == ManageDistinct.NO) {
			throw new BusinessException("Msg_1731");
		} else {
			if (isPeriod) {
				
			}
		}
	}
	
	// Step 振休管理データを管理するかチェック
	public EmploymentManageDistinctDto getEmploymentManageDistinct(String compId, String empId) {
		// Step 管理区分 ＝ 管理しない
		EmploymentManageDistinctDto emplManage = new EmploymentManageDistinctDto();
		emplManage.setIsManage(ManageDistinct.NO);
		// Step 社員IDから全ての雇用履歴を取得
		List<EmploymentHistShareImport> empHistShrImp = this.shareEmploymentAdapter.findByEmployeeIdOrderByStartDate(empId);
		// Step 取得した社員の雇用履歴をチェック
		if (empHistShrImp.isEmpty()) {
			// Step エラーメッセージ(Msg_1306)を表示する
			throw new BusinessException("Msg_1306");
		} else {
			// Step 取得した社員の雇用履歴をループする
			for (EmploymentHistShareImport empHist : empHistShrImp) {
				// Step 管理区分設定を取得する
				ComSubstVacation comSubstVaca = getClassifiedManagementSetup(compId, empHist.getEmploymentCode());
				// Step 取得した「振休管理設定」．管理区分をチェック
				if (comSubstVaca.isManaged()) {
					// Step 管理区分 ＝ 管理する
					emplManage.setIsManage(ManageDistinct.YES);					
				}
				// Step 雇用コード ＝ 取得した社員の雇用履歴．期間．開始日 ＜＝ システム日付 AND 取得した社員の雇用履歴．期間．終了日 ＞＝システム日付
				GeneralDate now = GeneralDate.today();
				if (empHist.getPeriod().start().beforeOrEquals(now) && empHist.getPeriod().end().afterOrEquals(now)) {
					emplManage.setEmploymentCode(empHist.getEmploymentCode());
				}
			}
		}
		// Step 管理区分、雇用コードを返す
		return emplManage;
	}
	
	// Step 管理区分設定を取得する
	public ComSubstVacation getClassifiedManagementSetup(String compId, String empCode) {
		// Step ドメインモデル「雇用振休管理設定」を取得
		Optional<ComSubstVacation> optComSubData = comSubstVacationRepository.findById(compId);
		// Step 取得した「振休管理設定」をチェック
		if (!optComSubData.isPresent()) {
			// Step ドメインモデル「振休管理設定」を取得
			Optional<EmpSubstVacation> optEmpSubData = empSubstVacationRepository.findById(compId, empCode);
			// Step ドメインモデル「雇用振休管理設定」を返す
			return new ComSubstVacation(optEmpSubData.get().getCompanyId(), optEmpSubData.get().getSetting());
		} else {
			// Step 取得したドメインモデル「振休管理設定」を返す
			return optComSubData.get();
		}
	}
	
	public Double getNumberOfDayLeft(String sID) {
		String cid = AppContexts.user().companyId();
		Double totalUnUseDay = 0.0;
		Double  totalUndeliveredDay = 0.0;
		
		List<PayoutManagementData> payoutManagementData = payoutManagementDataRepository.getSidWithCod(cid, sID, 0);
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBysiDRemCod(cid, sID);
		
		for (PayoutManagementData item : payoutManagementData) {
			totalUnUseDay += item.getUnUsedDays().v();
		}
		
		for (SubstitutionOfHDManagementData item : substitutionOfHDManagementData) {
			totalUndeliveredDay += item.getRemainDays().v();
		}
		
		return totalUnUseDay - totalUndeliveredDay;
	}
	
	public int getExpirationDate(String sid, String empCD) {
		String cid = AppContexts.user().companyId();
		EmpSubstVacation empSubstVacation;
		ComSubstVacation comSubstVacation;
		int expirationDate = 0;
		
		// get scd
		if (empCD != null) {
			if(empSubstVacationRepository.findById(cid, empCD).isPresent()) {
				empSubstVacation = empSubstVacationRepository.findById(cid, empCD).get();
				expirationDate = empSubstVacation.getSetting().getExpirationDate().value;
			} else if (comSubstVacationRepository.findById(cid).isPresent()){
				comSubstVacation = comSubstVacationRepository.findById(cid).get();
				expirationDate = comSubstVacation.getSetting().getExpirationDate().value;
			}
		} else {
			if (comSubstVacationRepository.findById(cid).isPresent()){
				comSubstVacation = comSubstVacationRepository.findById(cid).get();
				expirationDate = comSubstVacation.getSetting().getExpirationDate().value;
			}
		}
		
		return expirationDate;
	}
	
	public Integer getClosureId(String sid, String empCD) {
		String cid = AppContexts.user().companyId();
		Integer closureId = null;
		
		if (empCD != null) {
			if(closureEmploymentRepository.findByEmploymentCD(cid, empCD).isPresent()) {
				closureId = closureEmploymentRepository.findByEmploymentCD(cid, empCD).get().getClosureId();
			}
		}
		
		return closureId;
	}
}
