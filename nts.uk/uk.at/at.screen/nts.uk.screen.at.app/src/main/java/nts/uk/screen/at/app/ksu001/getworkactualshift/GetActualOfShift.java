/**
 *
 */
package nts.uk.screen.at.app.ksu001.getworkactualshift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.layer.app.cache.NestedMapCache;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.DailyResultAccordScheduleStatusService;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftResult;
import nts.uk.screen.at.app.ksu001.processcommon.CreateWorkScheduleShiftBase;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleShiftBaseResult;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * 勤務実績（シフト）を取得する
 */
@Stateless
public class GetActualOfShift {

	@Inject
	private EmpComHisAdapter empComHisAdapter;
	@Inject
	private WorkingConditionRepository workCondRepo;
	@Inject
	private EmpLeaveHistoryAdapter empLeaveHisAdapter;
	@Inject
	private EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter;
	@Inject
	private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
	@Inject
	private DailyRecordWorkFinder dailyRecordWorkFinder;
	@Inject
	private CreateWorkScheduleShiftBase createWorkScheduleShiftBase;

	public ScheduleOfShiftResult getActualOfShift(ScheduleOfShiftParam param) {

		// step 1
		// call 予定管理状態に応じて日別実績を取得する
		long start = System.nanoTime();
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		RequireDailyImpl requireDailyImpl = new RequireDailyImpl(param.listSid, period, dailyRecordWorkFinder , empComHisAdapter, workCondRepo, empLeaveHisAdapter,empLeaveWorkHisAdapter, employmentHisScheduleAdapter);
		Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> mapDataDaily = DailyResultAccordScheduleStatusService.get(requireDailyImpl, param.listSid, period);
		long end = System.nanoTime();
		long duration = (end - start) / 1000000; // ms;
		System.out.println("thoi gian get data Daily cua "+ param.listSid.size() + " employee: " + duration + "ms");

		// step 2
		// call 勤務実績で勤務予定（シフト）dtoを作成する
		WorkScheduleShiftBaseResult rs = createWorkScheduleShiftBase.getWorkScheduleShiftBase(mapDataDaily, param.listShiftMasterNotNeedGetNew);
		Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle = rs.mapShiftMasterWithWorkStyle;
		List<ShiftMasterMapWithWorkStyle> listShiftMaster = new ArrayList<>();
		mapShiftMasterWithWorkStyle.forEach((k,v)-> {
			ShiftMasterMapWithWorkStyle shift = new ShiftMasterMapWithWorkStyle(k, v.isPresent() ? v.get().value + "" : null);
			listShiftMaster.add(shift);
			
		});
		return new ScheduleOfShiftResult(rs.listWorkScheduleShift, listShiftMaster);
	}

	@AllArgsConstructor
	private static class RequireDailyImpl implements DailyResultAccordScheduleStatusService.Require {

		private NestedMapCache<String, GeneralDate, DailyRecordDto> workScheduleCache;
		private KeyDateHistoryCache<String, EmpEnrollPeriodImport> affCompanyHistByEmployeeCache;
		private KeyDateHistoryCache<String, EmploymentPeriodImported> employmentPeriodCache;
		private KeyDateHistoryCache<String, EmployeeLeaveJobPeriodImport> empLeaveJobPeriodCache;
		private KeyDateHistoryCache<String, EmpLeaveWorkPeriodImport> empLeaveWorkPeriodCache;
		private KeyDateHistoryCache<String, WorkingConditionItemWithPeriod> workCondItemWithPeriodCache;

		public RequireDailyImpl(List<String> empIdList, DatePeriod period, DailyRecordWorkFinder dailyRecordWorkFinder,
				EmpComHisAdapter empComHisAdapter, WorkingConditionRepository workCondRepo,
				EmpLeaveHistoryAdapter empLeaveHisAdapter, EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter,
				EmploymentHisScheduleAdapter employmentHisScheduleAdapter) {

			long start1 = System.nanoTime();
			List<DailyRecordDto> sDailyRecordDtos = dailyRecordWorkFinder.find(empIdList, period);
			workScheduleCache = NestedMapCache.preloadedAll(sDailyRecordDtos.stream(),
					workSchedule -> workSchedule.getEmployeeId(),
					workSchedule -> workSchedule.getDate());
			System.out.println("thoi gian get data Daily " + ((System.nanoTime() - start1 )/1000000) + "ms");

			long start2 = System.nanoTime();
			List<EmpEnrollPeriodImport> affCompanyHists =  empComHisAdapter.getEnrollmentPeriod(empIdList, period);
			affCompanyHistByEmployeeCache = KeyDateHistoryCache.loaded(affCompanyHists.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data affCompanyHistByEmp " + ((System.nanoTime() - start2 )/1000000) + "ms");

			long start3 = System.nanoTime();
			List<EmploymentPeriodImported> listEmploymentPeriodImported = employmentHisScheduleAdapter.getEmploymentPeriod(empIdList, period);
			employmentPeriodCache = KeyDateHistoryCache.loaded(listEmploymentPeriodImported.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data EmploymentPeriod " + ((System.nanoTime() - start3 )/1000000) + "ms");

			long start4 = System.nanoTime();
			List<EmployeeLeaveJobPeriodImport> empLeaveJobPeriods = empLeaveHisAdapter.getLeaveBySpecifyingPeriod(empIdList, period);
			empLeaveJobPeriodCache = KeyDateHistoryCache.loaded(empLeaveJobPeriods.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data EmployeeLeaveJob " + ((System.nanoTime() - start4 )/1000000) + "ms");

			long start5 = System.nanoTime();
			List<EmpLeaveWorkPeriodImport> empLeaveWorkPeriods =  empLeaveWorkHisAdapter.getHolidayPeriod(empIdList, period);
			empLeaveWorkPeriodCache = KeyDateHistoryCache.loaded(empLeaveWorkPeriods.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data EmpLeaveWork " + ((System.nanoTime() - start5 )/1000000) + "ms");

			long start6 = System.nanoTime();
			List<WorkingConditionItemWithPeriod> listData = workCondRepo.getWorkingConditionItemWithPeriod(AppContexts.user().companyId(),empIdList, period);
			workCondItemWithPeriodCache = KeyDateHistoryCache.loaded(listData.stream()
					.collect(Collectors.toMap( h -> h.getWorkingConditionItem().getEmployeeId(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data WorkingConditionItem " + ((System.nanoTime() - start6 )/1000000) + "ms");

			System.out.println("thoi gian get data để lưu vào Cache" + ((System.nanoTime() - start1 )/1000000) + "ms");
		}

		@Override
		public Optional<IntegrationOfDaily> getDailyResults(String empId, GeneralDate date) {
			Optional<DailyRecordDto> dailyRecordDto = workScheduleCache.get(empId, date);
			if (dailyRecordDto.isPresent()) {
				IntegrationOfDaily data = dailyRecordDto.get().toDomain(empId, date);
				return Optional.of(data);
			}
			return Optional.empty();
		}

		@Override
		public Optional<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(String sid, GeneralDate startDate) {
			Optional<EmpEnrollPeriodImport> data = affCompanyHistByEmployeeCache.get(sid, startDate);
			return data;
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
			Optional<WorkingConditionItemWithPeriod> data = workCondItemWithPeriodCache.get(employeeId, baseDate);
			return data.isPresent() ? Optional.of(data.get().getWorkingConditionItem()) : Optional.empty();
		}

		@Override
		public Optional<EmployeeLeaveJobPeriodImport> getByDatePeriod(String sid, GeneralDate startDate) {
			Optional<EmployeeLeaveJobPeriodImport> data =  empLeaveJobPeriodCache.get(sid, startDate);
			return data;
		}

		@Override
		public Optional<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(String sid, GeneralDate startDate) {
			Optional<EmpLeaveWorkPeriodImport> data = empLeaveWorkPeriodCache.get(sid, startDate);
			return data;
		}

		@Override
		public Optional<EmploymentPeriodImported> getEmploymentHistory(String sid, GeneralDate startDate) {
			Optional<EmploymentPeriodImported> data = employmentPeriodCache.get(sid, startDate);
			return data;
		}
	}
}
