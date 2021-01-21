package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultFinder;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.DailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.IGetDailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.StatusActualDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.ConfirmStatusOfDayService;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt
 * 打刻結果の確認及び実績の確認画面を取得する
 */
@Stateless
public class StampResultConfirmationQuery {
	
	@Inject
	private DisplayScreenStampingResultFinder displayScreenStamping;
	
	@Inject
	private CompanyDailyItemService companyDailyItemService;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepo;
	
	@Inject
	private DailyRecordWorkFinder fullFinder;
	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private ConfirmStatusActualDayChange confirmStatusActualDayChange;
	
	@Inject
	private IGetDailyLock iGetDailyLock;
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	@Inject
	private GetRoleIDQuery getRoleIDQuery;
	
	public StampResultConfirmDto getStampResultConfirm(StampResultConfirmRequest param) {
		String cid         = AppContexts.user().companyId();
		String sid         = param.getEmployeeId();

		// add more 28 29 31 34 info
		boolean contain28 = param.isContain28();
		boolean contain29 = param.isContain29();
		boolean contain31 = param.isContain31();
		boolean contain34 = param.isContain34();
		param.correctRequest();

		List<Integer> attItemIds = param.getAttendanceItems();
		
		// 1
		List<DisplayScreenStampingResultDto> screenDisplays = displayScreenStamping.getDisplay(param.toStampDatePeriod(), sid);
		
		// 2
		ConfirmStatusOfDayRequiredImpl required = new ConfirmStatusOfDayRequiredImpl();
		ConfirmStatusActualResult confirmStatusAcResults = ConfirmStatusOfDayService.get(required, cid, sid, GeneralDateTime.now().toDate());
		
		// 3 打刻結果を表示するためにロールIDを取得する 2020/05/13  EA3769　追加
		String roleId = getRoleIDQuery.getRoleId(cid, sid);
		
		// 4 アルゴリズム「会社の日次項目を取得する」を実行する
		List<AttItemName> dailyItems = companyDailyItemService.getDailyItems(cid, Optional.ofNullable(roleId) , param.getAttendanceItems(), Collections.emptyList());
		
		// 5 アルゴリズム「指定した勤怠項目IDに対応する項目を返す」を実行する
		List<String> sids = new ArrayList<>();
		sids.add(sid);
		DailyModifyResult dailyResult = AttendanceItemUtil.toItemValues(this.fullFinder.find(sids, param.toStampDatePeriod()), attItemIds)
			.entrySet().stream().map(c -> DailyModifyResult.builder().items(c.getValue())
						.workingDate(c.getKey().workingDate()).employeeId(c.getKey().employeeId()).completed())
				.findFirst().orElse(null);
		List<ItemValue> itemValues = dailyResult != null ? dailyResult.getItems() : Collections.emptyList();
		
		// 6
		
		String itemId = itemValues.stream().filter(i -> i.getItemId() == 28).findFirst().map(x -> x.getValue())
				.orElse("");
		List<WorkType> workTypes = workTypeRepo.getPossibleWorkType(cid, Arrays.asList(itemId));
		
		// 7
		
		String itemId2 = itemValues.stream().filter(i -> i.getItemId() == 29).findFirst().map(x -> x.getValue())
				.orElse("");
		List<WorkTimeSetting> workTimes = workTimeRepo.getListWorkTimeSetByListCode(cid, Arrays.asList(itemId2));
		
		Optional<ItemValue> attendance = itemValues.stream().filter(i -> i.getItemId() == 31).findFirst();
		Optional<ItemValue> leave = itemValues.stream().filter(i -> i.getItemId() == 34).findFirst();
		
		if(!contain28) {
			dailyItems.removeIf(r -> r.getAttendanceItemId() == 28);			
		}
		if(!contain29) {
			dailyItems.removeIf(r -> r.getAttendanceItemId() == 29);			
		}
		if(!contain31) {
			dailyItems.removeIf(r -> r.getAttendanceItemId() == 31);			
		}
		if(!contain34) {
			dailyItems.removeIf(r -> r.getAttendanceItemId() == 34);			
		}
		
		// 8
		dailyItems.sort((a , b) -> Integer.valueOf(a.getAttendanceItemDisplayNumber()).compareTo(Integer.valueOf(b.getAttendanceItemDisplayNumber())));
		
		return new StampResultConfirmDto(screenDisplays, dailyItems, itemValues, workTypes, workTimes, confirmStatusAcResults, attendance, leave);
	}
	
	private class ConfirmStatusOfDayRequiredImpl implements ConfirmStatusOfDayService.Require {
		
		@Override
		public Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate) {
			return ClosureService.getClosureDataByEmployee(
					ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
					new CacheCarrier(), employeeId, baseDate);
		}

		@Override
		public Optional<SWkpHistRcImported> findBySid(String employeeId, GeneralDate baseDate) {
			return syWorkplaceAdapter.findBySid(employeeId, baseDate);
		}

		@Override
		public DailyLock getDailyLock(StatusActualDay satusActual) {
			return iGetDailyLock.getDailyLock(satusActual);
		}

		@Override
		public List<ConfirmStatusActualResult> processConfirmStatus(String companyId, String empTarget,
				List<String> employeeIds, Optional<DatePeriod> periodOpt, Optional<YearMonth> yearMonthOpt,
				Optional<DailyLock> dailyLockOpt) {
			return confirmStatusActualDayChange.processConfirmStatus(companyId, empTarget, employeeIds, periodOpt, yearMonthOpt, dailyLockOpt);
		}
		
	}
	
}
