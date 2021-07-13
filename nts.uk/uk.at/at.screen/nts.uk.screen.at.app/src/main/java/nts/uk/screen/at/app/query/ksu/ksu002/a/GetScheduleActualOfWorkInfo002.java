package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.GetLegalWorkTimeOfEmployeeService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonAndWeekStatutoryTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.LegalWorkTimeOfEmployeeDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author chungnt 
 * ScreenQuery: 予定・実績を取得する
 */

@Stateless
public class GetScheduleActualOfWorkInfo002 {
	@Inject
	private GetWorkActualOfWorkInfo002 getWorkRecord;

	@Inject
	private GetScheduleOfWorkInfo002 getScheduleOfWorkInfo002;
	
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;
	
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSet;
	
	@Inject
	private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
	
	public List<WorkScheduleWorkInforDto> getDataScheduleAndAactualOfWorkInfo(DisplayInWorkInfoInput param) {
		// lay data Schedule
		List<WorkScheduleWorkInforDto> listDataSchedule = getScheduleOfWorkInfo002.getDataScheduleOfWorkInfo(param);

		if (param.actualData) {
			// lay data Daily
			List<WorkScheduleWorkInforDto> listDataDaily = getWorkRecord.getDataActualOfWorkInfo(param);
			
			for (WorkScheduleWorkInforDto ds : listDataSchedule) {
				listDataDaily.stream().filter(c -> c.employeeId.equals(ds.employeeId) && c.date.equals(ds.date)).findFirst()
					.ifPresent(c -> {
						WorkScheduleWorkInforDto.Achievement arch = WorkScheduleWorkInforDto
								.Achievement
								.builder()
								.workTypeCode(c.getWorkTypeCode())
								.workTypeName(c.getWorkTypeName())
								.workTimeCode(c.getWorkTimeCode())
								.workTimeName(c.getWorkTimeName())
								.startTime(c.getStartTime())
								.endTime(c.getEndTime())
								.build();
						
						ds.setAchievements(arch);
					});
			}
		}

		return listDataSchedule;
	}
	//ScreenQuery 期間に応じる基本情報を取得する  -> 3 取得する(@Require, 社員ID, 期間) Optional<社員の法定労働時間>
	public LegalWorkTimeOfEmployeeDto getlegalworkinghours(DisplayInWorkInfoInput param) {
		String companyId = AppContexts.user().companyId();
		LegalWorkTimeOfEmployeeDto result = new LegalWorkTimeOfEmployeeDto();
		if(param.listSid.isEmpty()) {
			return result;
		}
		result.setValue(GetLegalWorkTimeOfEmployeeService.get(new LegalWorkTimeRequireImpl(companyId), param.listSid.get(0), param.getPeriod()));
		return result;
	}

	@RequiredArgsConstructor
	private class LegalWorkTimeRequireImpl implements GetLegalWorkTimeOfEmployeeService.Require {
		
		private final String companyId;
		
		@Override
		public Optional<WorkingConditionItem> getHistoryItemBySidAndBaseDate(String sid, GeneralDate baseDate) {
			return workingConditionItemRepository.getBySidAndStandardDate(sid, baseDate);
		}

		@Override
		public List<EmploymentPeriodImported> getEmploymentHistories(String sid, DatePeriod datePeriod) {
			List<String> listEmpId = new ArrayList<String>();
			listEmpId.add(sid);
			return employmentHisScheduleAdapter.getEmploymentPeriod(listEmpId, datePeriod);
		}

		@Override
		public MonthlyFlexStatutoryLaborTime flexMonAndWeekStatutoryTime(YearMonth ym, String employmentCd,String employeeId, GeneralDate baseDate) {
			return MonthlyStatutoryWorkingHours.flexMonAndWeekStatutoryTime(new Require1(), new CacheCarrier(), companyId, employmentCd, employeeId, baseDate, ym);
		}

		@Override
		public Optional<MonAndWeekStatutoryTime> monAndWeekStatutoryTime(YearMonth ym, String employmentCd, String employeeId, GeneralDate baseDate, WorkingSystem workingSystem) {
			return MonthlyStatutoryWorkingHours.monAndWeekStatutoryTime(require, new CacheCarrier(), companyId, employmentCd, employeeId, baseDate, ym, workingSystem);
		}

		@Override
		public Optional<GetFlexPredWorkTime> getFlexStatutoryTime() {
			return getFlexPredWorkTimeRepository.find(companyId);
		}
		
	}
	
	@RequiredArgsConstructor
	private class Require1 implements MonthlyStatutoryWorkingHours.RequireM1 {
		
		@Override
		public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
			return usageUnitSettingRepository.findByCompany(companyId);
		}
		@Override
		public List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId,
				GeneralDate baseDate) {
			return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId, baseDate);
		}
		@Override
		public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSet.findEmployee(cid, sid, laborAttr, ym);
		}
		@Override
		public Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSet.findEmployment(cid, empCode, laborAttr, ym);
		}
		@Override
		public Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr,
				YearMonth ym) {
			return monthlyWorkTimeSet.findCompany(cid, laborAttr, ym);
		}
		@Override
		public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSet.findWorkplace(cid, workplaceId, laborAttr, ym);
		}
		
	}
}
