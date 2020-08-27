package nts.uk.screen.at.app.shift.workcycle;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.*;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.HolidayWorkTypeService;
import nts.uk.screen.at.app.ksm003.find.WorkCycleQueryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクル反映ダイアログ
 * @author khai.dh
 */
@Stateless
public class WorkCycleReflectionDialog {
	@Inject private HolidayWorkTypeService holidayWorkTypeService;
	//@Inject private WorkInformation workInformation;
	@Inject private WorkCycleQueryRepository workCycleQueryRepository;

	@Inject private WorkInformationRequire wRequire;
	@Inject private CreateWorkCycleAppImageRequire cRequire;
	@Inject private CreateWorkCycleAppImage createWorkCycleAppImage;
//
//	public WorkCycleReflectionDialog(){
//		wRequire = new WorkInformationRequire();
//		cRequire = new CreateWorkCycleAppImageRequire();
//		createWorkCycleAppImage = new CreateWorkCycleAppImage();
//	}

 	/**
	 * 起動情報を取得する
	 * @param bootMode 起動モード
	 * @param creationPeriod 作成期間
	 * @param workCycleCode 勤務サイクルコード
	 * @param refOrder パターン反映順序
	 * @param numOfSlideDays スライド日数
	 * @return start up info
	 */
	public WorkCycleReflectionDto getStartupInfo(
			BootMode bootMode,
			DatePeriod creationPeriod,
			String workCycleCode,
			List<WorkCreateMethod> refOrder,
			int numOfSlideDays){

		WorkCycleReflectionDto dto = new WorkCycleReflectionDto();

		// 1. 勤務サイクル一覧を取得する [Get a list of work cycles]
		if (bootMode == BootMode.EXEC_MODE){
			String cid = AppContexts.user().companyId();
			List<WorkCycle> workCycleList = workCycleQueryRepository.getByCid(cid);
			workCycleCode = workCycleList.get(0).getCode().v();
			dto.setWorkCycleList(workCycleList);
		}

		// 2. 休日系の勤務種類を取得する [Get holiday type of work]
		List<WorkType> workTypes = holidayWorkTypeService.acquiredHolidayWorkType();

		// 3. 作成する(Require, 期間, 勤務サイクルの反映設定)
		WorkCycleRefSetting config = new WorkCycleRefSetting(
				workCycleCode,
				refOrder,
				numOfSlideDays,
				null,
				null,
				null
		);

		List<RefImageEachDay> refImageEachDayList = createWorkCycleAppImage.create(cRequire, creationPeriod, config);
 		ReflectionImage reflectionImage = new ReflectionImage();
		HashMap<GeneralDate,WorkStyle> workStyles = new HashMap<>();
		refImageEachDayList.stream().forEach(ref -> {
			GeneralDate date = ref.getDate();
			WorkInformation wInfo = ref.getWorkInformation();
			reflectionImage.addInWorkCycle(date, wInfo);
			workStyles.put(ref.getDate(),  wInfo.getWorkStyle(wRequire).get());
		});

		// 4. 出勤・休日系の判定(Require)
		// WorkTimeCode workTimeCode, WorkTypeCode workTypeCode
		// Optional<WorkStyle> optWorkStyle = workInformation.getWorkStyle(wRequire);

		dto.setWorkTypes(workTypes); // List<勤務種類>：休日系の勤務種類
		dto.setReflectionImage(reflectionImage); // 反映イメージ
		dto.setWorkStyles(workStyles); // 出勤休日区分

		return dto;
	}

	/**
	 * 勤務サイクルの適用イメージを取得する
	 * @param creationPeriod 作成期間
	 * @param workCycleRefSetting 設定
	 * @return 反映イメージ
	 */
	public ReflectionImage getWorkCycleAppImage(
			DatePeriod creationPeriod,
			WorkCycleRefSetting workCycleRefSetting){
		List<RefImageEachDay> refImageEachDayList = createWorkCycleAppImage.create(cRequire, creationPeriod, workCycleRefSetting);
		ReflectionImage reflectionImage = new ReflectionImage();
		refImageEachDayList.stream().forEach(ref -> {
			reflectionImage.addInWorkCycle(ref.getDate(), ref.getWorkInformation());
		});

		return reflectionImage;
	}

	@Stateless
	private static class CreateWorkCycleAppImageRequire implements CreateWorkCycleAppImage.Require {
		@Inject private WeeklyWorkDayRepository weeklyWorkDayRepository;
		@Inject private PublicHolidayRepository publicHolidayRepository;
		@Inject private WorkCycleRepository workCycleRepository;

		@Override
		public Optional<WeeklyWorkDayPattern> getWeeklyWorkSetting(String cid){
			return Optional.of(weeklyWorkDayRepository.getWeeklyWorkDayPatternByCompanyId(cid));
		}

		@Override
		public List<PublicHoliday> getpHolidayWhileDate(String companyId, GeneralDate strDate, GeneralDate endDate){
			return publicHolidayRepository.getpHolidayWhileDate(companyId, strDate, endDate);
		}

		@Override
		public Optional<WorkCycle> getWorkCycle(String cid, String code){
			return workCycleRepository.getByCidAndCode(cid, code);
		}

	}

	@Stateless
	private static class WorkInformationRequire implements WorkInformation.Require {
		@Inject private BasicScheduleService basicScheduleService;

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return Optional.empty();
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return Optional.empty();
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return null;
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd, Integer workNo) {
			return null;
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}
}