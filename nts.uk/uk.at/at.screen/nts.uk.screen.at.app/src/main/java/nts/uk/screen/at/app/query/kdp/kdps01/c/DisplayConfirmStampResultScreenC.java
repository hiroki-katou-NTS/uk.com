package nts.uk.screen.at.app.query.kdp.kdps01.c;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultFinder;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.DailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.IGetDailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.StatusActualDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.ConfirmStatusOfDayService;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.??????.KDP_??????.KDPS01_????????????(?????????).C:????????????????????????.???????????????OCD.????????????(?????????)???????????????????????????????????????????????????
 */
@Stateless
public class DisplayConfirmStampResultScreenC {

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;

	@Inject
	private ConfirmStatusActualDayChange confirmStatusActualDayChange;

	@Inject
	private IGetDailyLock iGetDailyLock;

	@Inject
	private DisplayScreenStampingResultFinder stampingFinder;

	@Inject
	private CompanyDailyItemService companyDailyItemService;

	@Inject
	private DailyRecordWorkFinder fullFinder;

	@Inject
	private WorkTypeRepository wkTypeRepo;

	@Inject
	private WorkTimeSettingRepository WorkTimeSettingRepo;

	@Inject
	private DailyAttendanceItemRepository DailyAttendanceItemRepo;

	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private ClosureEmploymentRepository closureEmpRepo;

	@Inject
	private ShareEmploymentAdapter shrEmpAdapter;

	/**
	 * ????????????(?????????)???????????????????????????????????????????????????
	 * 
	 * @param ????????????
	 * @param ???????????????
	 * @param ??????????????????(????????????ID(?????????))
	 * @return ?????????????????????????????????????????????
	 * @return ???????????????????????????
	 * @return ?????????????????????????????????ID?????????????????????PrimitiveValue???
	 * @return ????????????????????????ID?????????????????????
	 * @return ??????????????? ???????????????.?????????
	 * @return ?????????????????? ???????????????????????????.?????????
	 */
	public DisplayConfirmStampResultScreenCDto getStampInfoResult(DatePeriod period, GeneralDate baseDate,
			List<Integer> attendanceItemIds) {

		DisplayConfirmStampResultScreenCDto result = new DisplayConfirmStampResultScreenCDto();

		String companyId = AppContexts.user().companyId();

		String sid = AppContexts.user().employeeId();
		// 1 get*(??????) ??????????????????????????????????????????

		List<DisplayScreenStampingResultDto> stampings = this.stampingFinder.getDisplay(period,
				AppContexts.user().employeeId());

		result.setStampings(stampings);
		result.setWorkplaceCd(stampings.size() > 0 ? stampings.get(0).getWorkplaceCd() : "");
		result.setWorkplaceName(stampings.size() > 0 ? stampings.get(0).getWorkplaceNm() : "");
		
		// 2 require, ??????????????????ID, ??????????????????ID, ?????????
		// ??????????????????????????????????????????

		ConfirmStatusOfDayRequiredImpl required = new ConfirmStatusOfDayRequiredImpl(syWorkplaceAdapter,
				confirmStatusActualDayChange, iGetDailyLock);

		result.setConfirmResult(ConfirmStatusActualResultDto
				.fromDomain(ConfirmStatusOfDayService.get(required, companyId, sid, baseDate)));
		// 3 call ()
		// ???????????????????????????????????????????????????????????????????????????

		List<AttItemName> dailyItems = companyDailyItemService.getDailyItems(companyId,
				Optional.ofNullable(AppContexts.user().roles().forPersonnel()), attendanceItemIds,
				Collections.emptyList());
		// 4 call()
		// ?????????????????????????????????????????????ID????????????????????????????????????????????????

		DailyModifyResult dailyResult = AttendanceItemUtil
				.toItemValues(this.fullFinder.find(Arrays.asList(sid), period),
						dailyItems.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList()))
				.entrySet().stream().map(c -> DailyModifyResult.builder().items(c.getValue())
						.workingDate(c.getKey().workingDate()).employeeId(c.getKey().employeeId()).completed())
				.findFirst().orElse(null);

		List<ItemValueDto> itemValues = dailyResult != null ? dailyResult.getItems().stream()
				.map(x -> ItemValueDto.fromDomain(x, dailyResult.getDate())).collect(Collectors.toList())
				: Collections.emptyList();

		result.setItemValues(itemValues);

		// 5 get ??????ID???????????? ?????????.????????????ID=28

		String workTypeCd = itemValues.stream().filter(x -> x.getItemId() == 28).findFirst().map(x -> x.getValue())
				.orElse("");

		this.wkTypeRepo.findByPK(companyId, workTypeCd)
				.ifPresent(x -> result.setWorkTypeName(x.getName() != null ? x.getName().v() : ""));

		// 6 get ??????ID???????????? ?????????.????????????ID???29

		String workTimeCode = itemValues.stream().filter(x -> x.getItemId() == 29).findFirst().map(x -> x.getValue())
				.orElse("");

		this.WorkTimeSettingRepo.findByCodeAndAbolishCondition(companyId, workTimeCode, AbolishAtr.NOT_ABOLISH)
				.ifPresent(x -> result.setWorkTimeName(
						x.getWorkTimeDisplayName() != null ? x.getWorkTimeDisplayName().getWorkTimeName().v() : null));

		// 7 <call>

		List<ItemDisplayedDto> attendanceItems = this.DailyAttendanceItemRepo.getListById(companyId, attendanceItemIds)
				.stream().sorted(Comparator.comparing(DailyAttendanceItem::getDisplayNumber))
				.map(x -> ItemDisplayedDto.fromDomain(x, dailyItems)).collect(Collectors.toList());

		result.setLstItemDisplayed(attendanceItems);

		result.setEmpInfo(this.sysEmpPub.getPersonInfor(AppContexts.user().employeeId()));

		return result;
	}

	@AllArgsConstructor
	private class ConfirmStatusOfDayRequiredImpl implements ConfirmStatusOfDayService.Require {

		@Inject
		private SyWorkplaceAdapter syWorkplaceAdapter;

		@Inject
		private ConfirmStatusActualDayChange confirmStatusActualDayChange;

		@Inject
		private IGetDailyLock iGetDailyLock;

		@Override
		public Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate) {
			return ClosureService.getClosureDataByEmployee(createImp(), new CacheCarrier(), employeeId, baseDate);
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
			return confirmStatusActualDayChange.processConfirmStatus(companyId, empTarget, employeeIds, periodOpt,
					yearMonthOpt, dailyLockOpt);
		}

	}

	private ClosureService.RequireM3 createImp() {

		return new ClosureService.RequireM3() {

			@Override
			public Optional<Closure> closure(String companyId, int closureId) {
				return closureRepo.findById(companyId, closureId);
			}

			@Override
			public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
				return closureEmpRepo.findByEmploymentCD(companyID, employmentCD);
			}

			@Override
			public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
					String employeeId, GeneralDate baseDate) {
				return shrEmpAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
			}

			@Override
			public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
				return closureEmpRepo.findListEmployment(companyID, employmentCD);
			}

			@Override
			public List<Closure> closureClones(String companyId, List<Integer> closureId) {
				return closureRepo.findByListId(companyId, closureId);
			}

			@Override
			public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId, List<String> employeeId,
					GeneralDate baseDate) {
				return shrEmpAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
			}
		};
	}
}
