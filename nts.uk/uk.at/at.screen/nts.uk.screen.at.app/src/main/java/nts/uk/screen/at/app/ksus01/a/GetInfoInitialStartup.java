package nts.uk.screen.at.app.ksus01.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetShiftTableRuleForOrganizationService;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTableRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRule;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRule;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRuleDateSetting;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSUS01_スケジュール参照（スマホ）.スケジュール参照.A：スケジュール参照.メニュー別OCD.初期起動の情報取得
 * @author dungbn
 *
 */
@Stateless
public class GetInfoInitialStartup {
	
	@Inject
	private EmpAffiliationInforAdapter empAffiliationInforAdapter;
	
	@Inject
	private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrganizationRepo;
	
	@Inject
	private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;
	
	@Inject
	private PublicManagementShiftTableRepository publicManagementShiftTableRepository;

	
	public InitInformationDto handle() {
		
		String empId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		GeneralDate systemDate = GeneralDate.today();
		
		TargetIdentInforSerRequireImpl targetIdentInforSerRequire = new TargetIdentInforSerRequireImpl(empAffiliationInforAdapter);
		ShiftTableRuleForOrSerRequireImpl shiftTableRuleForOrSerRequire = new ShiftTableRuleForOrSerRequireImpl(shiftTableRuleForOrganizationRepo, shiftTableRuleForCompanyRepo, companyId);
		
		// 1: 取得する(Require, 年月日, 社員ID): 対象組織識別情報
		TargetOrgIdenInfor targetOrgIdenInfor = GetTargetIdentifiInforService.get(targetIdentInforSerRequire, systemDate, empId);
		
		// 2: 取得する(Require, 対象組織識別情報): Optional<シフト表のルール>  
		Optional<ShiftTableRule> shiftTableRule = GetShiftTableRuleForOrganizationService.get(shiftTableRuleForOrSerRequire, targetOrgIdenInfor);
		
		// 3: [not シフト表のルール.isPresent]
		if (!shiftTableRule.isPresent()) {
			throw new BusinessException("Msg_2049");
		}
		
		// 4: 取得する(対象組織識別情報): シフト表の公開管理
		Optional<PublicManagementShiftTable> publicManagementShiftTable = publicManagementShiftTableRepository.get(targetOrgIdenInfor);
		
		// ※シフト表のルール.勤務希望運用区分 ==しない場合： 期間＝システム日を含む月（１日～月末）
		if (shiftTableRule.get().getUseWorkAvailabilityAtr().value == 0) {
			GeneralDate start = GeneralDate.ymd(systemDate.year(), systemDate.month(), 1);
			GeneralDate end = start.addMonths(1).addDays(-1);
			DatePeriod datePeriod = new DatePeriod(start, end);
			
			return new InitInformationDto(
					shiftTableRule.get().getUsePublicAtr().value == 1 ? true : false,
					shiftTableRule.get().getUseWorkAvailabilityAtr().value == 1 ? true : false,
					publicManagementShiftTable.isPresent() ? publicManagementShiftTable.get().getEndDatePublicationPeriod().toString() : "", 
							 datePeriod.start().toString(), datePeriod.end().toString());
		}
		
		// 5: 希望日を含める期間を取得する(年月日): 期間
		DatePeriod datePeriod = shiftTableRule.get().getShiftTableSetting().get().getPeriodWhichIncludeAvailabilityDate(systemDate);
		
		return new InitInformationDto(
				shiftTableRule.get().getUsePublicAtr().value == 1 ? true : false,
				shiftTableRule.get().getUseWorkAvailabilityAtr().value == 1 ? true : false,
				publicManagementShiftTable.isPresent() ? publicManagementShiftTable.get().getEndDatePublicationPeriod().toString() : "",
						 datePeriod.start().toString(), datePeriod.end().toString());
	}
	
	@AllArgsConstructor
	private static class TargetIdentInforSerRequireImpl implements GetTargetIdentifiInforService.Require {
		
		private EmpAffiliationInforAdapter empAffiliationInforAdapter;

		@Override
		public List<EmpOrganizationImport> getEmpOrganization(GeneralDate baseDate, List<String> lstEmpId) {
			return empAffiliationInforAdapter.getEmpOrganization(baseDate, lstEmpId);
		}
	}
	
	@AllArgsConstructor
	private static class ShiftTableRuleForOrSerRequireImpl implements GetShiftTableRuleForOrganizationService.Require {
		
		private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrganizationRepo;
		
		private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;
		
		private String companyId;
		
		@Override
		public Optional<ShiftTableRuleForOrganization> getOrganizationShiftTable(TargetOrgIdenInfor targetOrg) {
			return shiftTableRuleForOrganizationRepo.get(companyId, targetOrg);
		}

		@Override
		public Optional<ShiftTableRuleForCompany> getCompanyShiftTable() {
			return shiftTableRuleForCompanyRepo.get(companyId);
		}
	}
}
