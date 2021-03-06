package nts.uk.screen.com.app.find.approvermanagement.workroot;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.ctx.sys.auth.pub.role.RoleExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.HistoryCmm053Command;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ManagerSettingDto;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class SettingOfManagerFinder {
	/** The work time hist repo. */
	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private PersonApprovalRootRepository personAppRootRepo;

	@Inject
	private ApprovalPhaseRepository appPhaseRepo;

	@Inject
	private EmployeeAdapter employeeAdapter;

	@Inject
	private ApprovalSettingRepository appSetRepo;

	@Inject
	private RoleExportRepo roleExportRepo;

	/**
	 * Get ???????????????
	 *
	 * @param employeeId
	 * @return
	 */
	public ManagerSettingDto getPsAppRootBySettingOfManager(String employeeId) {
		String companyId             = AppContexts.user().companyId();
		String loginId               = AppContexts.user().employeeId();
		GeneralDate startDate        = null;
		GeneralDate endDate          = null;
		GeneralDate startDateCommon  = null;
		GeneralDate endDateCommon    = null;
		GeneralDate startDateMonthly = null;
		GeneralDate endDateMonthly   = null;
		boolean isNewMode            = false;
		String departmentCode        = null;
		String departmentApproverId  = null;
		String departmentName        = null;
		String dailyApprovalCode     = null;
		String dailyApproverId       = null;
		String dailyApprovalName     = null;
		boolean hasAuthority         = true;
		boolean existCommonAppPhase  = false;
		boolean existMonthlyAppPhase = false;
		GeneralDate closingStartDate = null;
		SettingInfo commonSettingInfo  = null;
		SettingInfo monthlySettingInfo = null;
		boolean displayDailyApprover = false;

		// ????????????????????????????????????????????????????????????????????????
		Optional<PersonApprovalRoot> commonPsApp = this.personAppRootRepo.getNewestCommonPsAppRoot(companyId, employeeId, 0);
		Optional<PersonApprovalRoot> monthlyPsApp = this.personAppRootRepo.getNewestMonthlyPsAppRoot(companyId, employeeId);

		if (commonPsApp.isPresent()) {
			commonSettingInfo = this.getSettingInfo(companyId, commonPsApp.get());
			startDateCommon     = commonSettingInfo.getStartDate();
			endDateCommon       = commonSettingInfo.getEndDate();
			startDate           = commonSettingInfo.getStartDate();
			endDate             = commonSettingInfo.getEndDate();
			dailyApprovalCode   = commonSettingInfo.getApprovalCode();
			dailyApproverId     = commonSettingInfo.getApproverId();
			dailyApprovalName   = commonSettingInfo.getApprovalName();
			existCommonAppPhase = commonSettingInfo.isExistAppPhase();
		}

		if (monthlyPsApp.isPresent()) {
			monthlySettingInfo = this.getSettingInfo(companyId, monthlyPsApp.get());
			startDateMonthly     = monthlySettingInfo.getStartDate();
			endDateMonthly       = monthlySettingInfo.getEndDate();
			startDate            = monthlySettingInfo.getStartDate();
			endDate              = monthlySettingInfo.getEndDate();
			departmentCode       = monthlySettingInfo.getApprovalCode();
			departmentApproverId = monthlySettingInfo.getApproverId();
			departmentName       = monthlySettingInfo.getApprovalName();
			existMonthlyAppPhase = monthlySettingInfo.isExistAppPhase();
		}

		if (commonPsApp.isPresent() && monthlyPsApp.isPresent()) {
			GeneralDate maxDate = GeneralDate.fromString(ConstantUtils.MAX_DATE, ConstantUtils.FORMAT_DATE_YYYYMMDD);

			// ????????????????????????????????????
			if (startDateCommon.after(startDateMonthly)) {
				startDate = startDateCommon;
				endDate   = endDateCommon;
			}
			// ???????????????????????????????????????
			else {
				startDate = startDateMonthly;
				endDate   = endDateMonthly;
			}

			if (maxDate.equals(endDateCommon) && maxDate.equals(endDateMonthly)) {
				dailyApprovalCode    = commonSettingInfo.getApprovalCode();
				dailyApproverId      = commonSettingInfo.getApproverId();
				dailyApprovalName    = commonSettingInfo.getApprovalName();
				departmentCode       = monthlySettingInfo.getApprovalCode();
				departmentApproverId = monthlySettingInfo.getApproverId();
				departmentName       = monthlySettingInfo.getApprovalName();
			} else {
				if (startDateCommon.after(startDateMonthly)) {
					departmentCode       = null;
					departmentApproverId = null;
					departmentName       = null;
				} else {
					dailyApprovalCode = null;
					dailyApproverId   = null;
					dailyApprovalName = null;
				}
			}
		}
		// ??????????????????
		else if (!commonPsApp.isPresent() && !monthlyPsApp.isPresent()) {
			isNewMode = true;
		}

		// ??????????????????????????????????????????1?????????????????????
		if (!existMonthlyAppPhase && !existCommonAppPhase) {
			isNewMode = true;
		}

		// ????????????????????????????????????????????????
		Optional<GeneralDate> closureStartDate = this.getClosureStartDate(companyId, 1);
		if(closureStartDate.isPresent()){
			closingStartDate = closureStartDate.get();
		}

		// ???????????????????????????????????????????????????????????????????????????
		GeneralDate baseDate = isNewMode ? closingStartDate : startDate;

		// ?????????????????????????????????????????????
		hasAuthority         = this.employeeAdapter.canApprovalOnBaseDate(companyId, loginId, baseDate);

		// ????????????????????????
		Optional<RoleExport> opRoleExport = roleExportRepo.findByRoleId(AppContexts.user().roles().forAttendance());
		if(!opRoleExport.isPresent()){
			displayDailyApprover = true;
		} else {
			if(opRoleExport.get().getEmployeeReferenceRange()==3){
				displayDailyApprover = false;
			} else {
				displayDailyApprover = true;
			}
		}

		return new ManagerSettingDto(startDate, endDate, isNewMode, departmentCode, departmentApproverId,
				departmentName, dailyApprovalCode, dailyApproverId, dailyApprovalName, hasAuthority, closingStartDate, displayDailyApprover);
	}

	/**
	 * ??????????????????????????????????????????
	 *
	 * @param cId
	 * @param closureId
	 * @return
	 */
	private Optional<GeneralDate> getClosureStartDate(String companyId, int closureId) {
		Optional<Closure> optClosure = closureRepo.findById(companyId, closureId);

		// Check exist and active
		if (!optClosure.isPresent()
				|| optClosure.get().getUseClassification().equals(UseClassification.UseClass_NotUse)) {
			return Optional.empty();
		}

		Closure closure = optClosure.get();

		// Get Processing Ym ????????????
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();

		DatePeriod closurePeriod = ClosureService.getClosurePeriod(closureId, processingYm, optClosure);

		return Optional.of(closurePeriod.start());
	}

	/**
	 * Get setting information
	 * @param psAppRoot
	 * @return
	 */
	private SettingInfo getSettingInfo(String companyId, PersonApprovalRoot psAppRoot){
		GeneralDate startDate = psAppRoot.getApprRoot().getHistoryItems().get(0).getDatePeriod().start();
		GeneralDate endDate   = psAppRoot.getApprRoot().getHistoryItems().get(0).getDatePeriod().end();
		boolean existAppPhase = false;
		String approvalCode = null;
		String approverId   = null;
		String approvalName = null;
		// ????????????????????????????????????????????????????????????
		Optional<ApprovalPhase> commonPhase = this.appPhaseRepo.getApprovalFirstPhase(psAppRoot.getApprRoot().getHistoryItems().isEmpty()
				? ""
				: psAppRoot.getApprRoot().getHistoryItems().get(0).getApprovalId());
		
		if (commonPhase.isPresent()) {
			commonPhase.get().getApprovers().sort((p1, p2) -> p1.getApproverOrder() - p2.getApproverOrder());
			ApprovalAtr appAtr = commonPhase.get().getApprovalAtr();
			// ??????????????????????????????
			Approver firstApprover = commonPhase.get().getApprovers().get(0);
			// ??????????????????????????????????????????1??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
			if (appAtr.equals(ApprovalAtr.PERSON)) {
				PersonImport person = this.employeeAdapter.getEmployeeInformation(firstApprover.getEmployeeId());
				approvalCode = person.getEmployeeCode();
				approverId   = person.getSID();
				approvalName = person.getEmployeeName();
			}
			existAppPhase = true;
		}
		return new SettingInfo(startDate, endDate, approvalCode, approverId, approvalName, existAppPhase);
	}

	public void checkApprovalSetting(HistoryCmm053Command command) {
		// ????????????????????????????????????????????????????????????????????????????????????
		String companyId = AppContexts.user().companyId();
		Optional<ApprovalSetting> appSetOpt = this.appSetRepo.getApprovalByComId(companyId);
		if (appSetOpt.isPresent()) {
			appSetOpt.ifPresent(appSet -> {
				if (appSet.getPrinFlg().equals(PrincipalApprovalFlg.NOT_PRINCIPAL)) {
					// ??????????????????????????????????????????????????????????????????false(domain ?????????????????????????????????????????? = false)
					String Sid = command.getEmployeeId();
					String selectedID = command.getDepartmentApproverId();
					if (Sid.equals(selectedID)) {
						// ????????????????????????ID???A1_6??? ?????? ????????????????????????ID???A2_7???
						throw new BusinessException("Msg_1487");
					}

				}

			});
		} else {
			throw new BusinessException("Not Found ApprovalSetting(????????????) domain");
		}

	}
}


@Value
class SettingInfo {
	private GeneralDate startDate;
	private GeneralDate endDate;
	private String approvalCode;
	private String approverId;
	private String approvalName;
	private boolean existAppPhase;
}
